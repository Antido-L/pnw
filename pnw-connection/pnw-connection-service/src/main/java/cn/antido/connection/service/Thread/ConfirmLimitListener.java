package cn.antido.connection.service.Thread;

import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.antido.admin.mapper.NodeMapper;
import cn.antido.admin.mapper.OrderMapper;
import cn.antido.admin.mapper.ParkMapper;
import cn.antido.admin.mapper.SpaceMapper;
import cn.antido.admin.mapper.UserMapper;
import cn.antido.admin.pojo.Node;
import cn.antido.admin.pojo.Order;
import cn.antido.admin.pojo.Space;
import cn.antido.admin.pojo.User;
import cn.antido.common.CallBackResult;
import cn.antido.common.pojo.ConnCommand;
import cn.antido.common.redis.JedisClient;
import cn.antido.common.utils.CodeUtils;
import cn.antido.common.utils.JsonUtils;
import cn.antido.connection.pojo.ConfirmTarget;
import cn.antido.connection.service.list.CheckAndRemoveList;
import cn.antido.connection.service.socket.SocketConn;

/**
 * @Description 停车确认超时监听线程<br>
 * 在工程加载时由spring创建并执行start方法<br>
 * 在当前线程中维护并发安全的queue<br> 
 * 由其他线程向queue中添加元素,当前线性负责确认队列头部元素是否已过期,并对过期进行处理<br>
 * @author Antido
 * @date 2018年6月28日 下午7:47:39
 */
@Service
public class ConfirmLimitListener extends Thread {
	
	/**
	 * ConfirmLimitListener线程
	 */
	private Thread selfThread;
	
	/**
	 * CloseCheck线程
	 */
	private Thread closeCheckThread;
	
	/**
	 * 接近状态监听队列<br>
	 * 存放停车已经超时但车位无法关闭的目标<br>
	 * add()操作仅由此线程进行
	 */
	private CheckAndRemoveList<ConfirmTarget> closeCheckList;
	
	/**
	 * 停车超时监听队列<br>
	 * 存放本服务器响应的停车请求,当队列首超时时与redis中的CONFIRM_EXPIRE_SET作对比确认超时状态<br>
	 * 该数据结构由本服务器中多线程进行入队操作,仅由本线程做出队操作
	 */
	@Resource
	private LinkedBlockingQueue<ConfirmTarget> confirmParkQueue; //监听队列
	
	/**
	 * redis中待确认的用户集合,供多个服务器共同操作
	 */
	@Value("${confirm.redis.setName}")
	private String CONFIRM_SET_NAME;
	
	/**
	 * redis中用户token的命名头部
	 */
	@Value("${redis.session.SESSION_NAME}")
	private String SESSION_NAME;
	
	@Value("${redis.session.SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Value("${confirm.closeCheck.interval}")
	private Integer CLOSE_CHECK_INTERVAL;
	
	@Value("${confirm.closeCheck.timesLimit}")
	private Integer CLOSE_TIMES_LIMIT;
	
	
	@Autowired
	private JedisClient jedisClient;
	
	@Autowired
	private SocketConn sockConn;
	
	@Autowired
	private NodeMapper nodeMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private SpaceMapper spaceMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private ParkMapper parkMapper;
	
	/**
	 * activeMQ发送工具类
	 */
	@Autowired
	private JmsTemplate jmsTemplate;
	
	/**
	 * 增加剩余车辆消息
	 */
	@Resource
	private Destination addRemainQueueDestination; 
	
	/**
	 * 监听队列首的元素,当元素过期时间一到,验证确认是否成功<br>
	 * 队列中元素的过期时间长度是一致的 <br>
	 * 当前方法的运行的间隔时间=首元素的过期时间 - 当前时间<br>
	 * 当验证确认失败时 根据元素内的内容执行ConfirmTimeoutHandle
	 */
	@Override
	public void run() {
		System.out.println("ConfirmLimitListener is started!");
		long curr;
		while(true) {
			while(!confirmParkQueue.isEmpty()) {
				//查看头部过期时间
				ConfirmTarget peek = confirmParkQueue.peek();
				System.out.println(peek.getSpaceId() + "处在被停车监听队列中");
				curr = System.currentTimeMillis();
				while(peek.getExpire() > curr) {
					try {
						Thread.sleep(peek.getExpire() - curr + 10);
						curr = System.currentTimeMillis();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				//在redis中查找已经过期的元素
				ConfirmTarget target = confirmParkQueue.poll();
				Boolean isMember = jedisClient.smember(CONFIRM_SET_NAME, target.getUserId());
				if(isMember) { //在redis中存在 证明已经过期
					//处理已经超时的停车确认
					confirmTimeoutHandle(target);
				}
			}
			//这里小憩15s应该问题不大
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} 
			
	}
	
	/**
	 * 在spring加载该对象时启动监听线程
	 */
	@Override
	@PostConstruct
	public synchronized void start() {
		//初始化接近状态监听队列
		if(closeCheckList == null) {
			closeCheckList = new CheckAndRemoveList<ConfirmTarget>();
		}
		
		//启动线程
		if(selfThread == null) {
			selfThread = new Thread(this, "ComfirmLimitListener");
			selfThread.start();
		}
		
		//启动超时接近监听线程
		if(closeCheckThread == null) {
			closeCheckThread = new Thread(new ConfirmCloseCheck(this));
			closeCheckThread.start();
		}
	}
	
	/**
	 * 处理已经超时的停车确认<br>
	 * @param target
	 */
	@Transactional
	protected void confirmTimeoutHandle(ConfirmTarget target) {
		System.out.println(target.getSpaceId() + "已经停车超时");
		
		//确认当前车位是否有车
		Node node = nodeMapper.selectByPrimaryKey(target.getNode_id());
		if(node.getIs_close()) { //确认已经超时 但车位仍然检测到接近
			//TODO:向用户提交警告
			//TODO:降低用户信用值
			//TODO:向管理员发送消息
			
			//进行重复确认接近状态,接近监听列表中加入元素
			closeCheckList.add(target);
			
		} else { //超时后车位上没有车
			//TODO:向用户发送车位上锁警告
			
			if(target.getAddress().startsWith("192.168.0.")) { //如果是虚拟停车场 直接上锁
				Node node2 = new Node();
				node2.setId(node.getId());
				node2.setIs_lock(true);
				nodeMapper.updateIsLock(node2);
			}
			
			//向车位发送上锁指令
			CallBackResult lockSpace = lockSpace(target);
			if(lockSpace.getCode() != CallBackResult.CODE_OK) {
				lockSpace = lockSpace(target);
				if(lockSpace.getCode() != CallBackResult.CODE_OK) {
					//TODO:这问题可严重了...
				}
			}
			
			//同步处理车位离开后的数据
			changeAllData(target);
		}
	}
	
	/**
	 * 处理车位上锁后的数据同步问题
	 * @param target
	 */
	protected void changeAllData(ConfirmTarget target) {
		//撤销此订单生成的所有数据
		//清除用户车位车场关联状态
		User user = new User();
		user.setId(target.getUserId());
		user.setState(User.STATE_NORMAL); //上锁之后用户变为正常状态
		userMapper.updateByUser(user);
		userMapper.updateState(user);
		
		//更改注册中心中的user关联状态
		String userStr = jedisClient.get(SESSION_NAME + target.getToken());
		user = JsonUtils.json2Obj(userStr, User.class);
		
		//清除CONFIRM_SET中对应的成员
		jedisClient.srem(CONFIRM_SET_NAME, target.getUserId());
		
		user.setSpace(null);
		user.setPark_id(null);
		user.setState(User.STATE_NORMAL); //上锁之后用户变为正常状态
		userStr = JsonUtils.obj2Json(user);
		jedisClient.set(SESSION_NAME + target.getToken(), userStr);
		jedisClient.expire(SESSION_NAME + target.getToken(), SESSION_EXPIRE);
		
		//解除车位使用状态
		Space space = new Space();
		space.setId(target.getSpaceId());
		//更新车位数据using_state,parked_time
		space.setUsing_state(Space.USING_STATE_FREE); //自由状态
		space.setUpdated(new Date());
		space.setUser_id(null); //将车位与用户取消关联
		space.setOrder_id(null); //将车位与订单取消关联
		spaceMapper.updateBySpace(space);
		spaceMapper.removeTimeItem(space.getId()); //将车位各项使用时间写NULL
		
		//将订单改为超时订单
		Order order = new Order();
		order.setId(target.getOrderId());
		order.setPay_state(Order.PAY_STATE_TIME_OUT);
		orderMapper.updateByOrder(order);
		
		//将停车场使用数减一
		final Long parkId = target.getParkId();
		parkMapper.subUsingCount(target.getParkId());
		
		
		//将索引库中剩余车位数加一
		jmsTemplate.send(addRemainQueueDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				//发送要修改的停车场id
				TextMessage message = session.createTextMessage(parkId + "");
				return message;
			}
		});
	}

	/**
	 * 向目标车位发送上锁指令
	 * @param target
	 * @return
	 */
	protected CallBackResult lockSpace(ConfirmTarget target) {
		//将车位上锁
		String address = target.getAddress();
		Integer port = target.getPort();
		//新建指令对象
		ConnCommand command = new ConnCommand();
		//根据密钥获取 连接校验码
		String connCode = CodeUtils.getConnCode(target.getConn_key())[0];
		
		command.setNodeId(target.getNode_id());
		command.setOrder(ConnCommand.ORDER_LOCK_OFF); //关闭锁
		command.setCode(connCode);
		//发送上锁请求
		CallBackResult result = sockConn.client(address,port, command);
		
		return result;
	}
	
	/**
	 * /当检测次数达到上限时处理此接近异常
	 */
	protected void closeCheckExceptHandle(ConfirmTarget target) {
		System.out.println("********closeCheckExceptHandle*********");
		System.out.println(target.getSpaceId() + "停车车位占用异常");
		
	}

	/**
	 * @return the cLOSE_CHECK_INTERVAL
	 */
	public Integer getCLOSE_CHECK_INTERVAL() {
		return CLOSE_CHECK_INTERVAL;
	}

	/**
	 * @param cLOSE_CHECK_INTERVAL the cLOSE_CHECK_INTERVAL to set
	 */
	public void setCLOSE_CHECK_INTERVAL(Integer cLOSE_CHECK_INTERVAL) {
		CLOSE_CHECK_INTERVAL = cLOSE_CHECK_INTERVAL;
	}

	/**
	 * @return the nodeMapper
	 */
	public NodeMapper getNodeMapper() {
		return nodeMapper;
	}

	/**
	 * @param nodeMapper the nodeMapper to set
	 */
	public void setNodeMapper(NodeMapper nodeMapper) {
		this.nodeMapper = nodeMapper;
	}

	/**
	 * @return the cLOSE_TIMES_LIMIT
	 */
	public Integer getCLOSE_TIMES_LIMIT() {
		return CLOSE_TIMES_LIMIT;
	}

	/**
	 * @param cLOSE_TIMES_LIMIT the cLOSE_TIMES_LIMIT to set
	 */
	public void setCLOSE_TIMES_LIMIT(Integer cLOSE_TIMES_LIMIT) {
		CLOSE_TIMES_LIMIT = cLOSE_TIMES_LIMIT;
	}

	/**
	 * @return the closeCheckList
	 */
	public CheckAndRemoveList<ConfirmTarget> getCloseCheckList() {
		return closeCheckList;
	}

	/**
	 * @param closeCheckList the closeCheckList to set
	 */
	public void setCloseCheckList(CheckAndRemoveList<ConfirmTarget> closeCheckList) {
		this.closeCheckList = closeCheckList;
	}

	
}
