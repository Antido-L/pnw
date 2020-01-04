package cn.antido.connection.service.Thread;

import java.util.Date;
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
import cn.antido.connection.service.socket.SocketConn;

@Service
public class ReserveLimitListerer extends Thread {
	
	/**
	 * ReserveLimitListerer线程
	 */
	private Thread selfThread;
	
	/**
	 * 预约超时监听队列<br>
	 * 存放本服务器响应的预约请求,当队列首超时时与redis中的RESERVE_EXPIRE_SET作对比确认超时状态<br>
	 * 该数据结构由本服务器中多线程进行入队操作,仅由本线程做出队操作
	 */
	@Resource
	private LinkedBlockingQueue<ConfirmTarget> confirmReserveList; //监听队列
	
	/**
	 * redis中待确认的用户集合,供多个服务器共同操作
	 */
	@Value("${reserve.redis.setName}")
	private String RESERVE_SET_NAME;
	
	/**
	 * redis中用户token的命名头部
	 */
	@Value("${redis.session.SESSION_NAME}")
	private String SESSION_NAME;
	
	@Value("${redis.session.SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Autowired
	private JedisClient jedisClient;
	
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
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private SpaceMapper spaceMapper;
	
	@Autowired
	private NodeMapper nodeMapper;
	
	@Autowired
	private ParkMapper parkMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private SocketConn sockConn;
	
	/**
	 * 在spring加载该对象时启动监听线程
	 */
	@Override
	@PostConstruct
	public synchronized void start() {
		//super.start();
		//启动线程
		if(selfThread == null) {
			selfThread = new Thread(this, "ReserveLimitListener");
			selfThread.start();
		}
	}
	
	@Override
	public void run() {
		System.out.println("ReserveLimitListener is started!");
		long curr;
		while(true) {
			while(!confirmReserveList.isEmpty()) {
				//查看头部过期时间
				ConfirmTarget peek = confirmReserveList.peek();
				System.out.println(peek.getSpaceId() + "处在被预约超时监听队列中");
				curr = System.currentTimeMillis();
				//等待头部目标超时
				while(peek.getExpire() > curr) {
					try {
						Thread.sleep(peek.getExpire() - curr + 10);
						curr = System.currentTimeMillis();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				//在redis中查找已经过期的元素
				ConfirmTarget target = confirmReserveList.poll();
				Boolean isMember = jedisClient.smember(RESERVE_SET_NAME, target.getUserId());
				if(isMember) { //在redis中存在 证明已经过期
					//处理已经超时的停车确认
					try {
						ReserveTimeoutHandle(target);	
					} catch (Exception e) {
						//处理车位释放异常防止线程挂掉
						ReserveTimeoutException();
					}
					
				}
			}
			
			//休息一下
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	private void ReserveTimeoutException() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 处理已经超时的预约请求
	 * @param target
	 */
	@Transactional
	private void ReserveTimeoutHandle(ConfirmTarget target) {
		System.out.println(target.getSpaceId() + "已经预约超时");
		
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
		jedisClient.srem(RESERVE_SET_NAME, target.getUserId());
		
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
		
		/*
		if(target.getAddress().startsWith("192.168.0.")) { //如果是虚拟停车场 直接上锁
			Node node2 = new Node();
			node2.setId(target.getNode_id());
			node2.setIs_lock(true);
			nodeMapper.updateIsLock(node2);
		}
		
		
		//向车位发送上锁指令
		CallBackResult lockSpace = lockOnSpace(target);
		if(lockSpace.getCode() != CallBackResult.CODE_OK) {
			lockSpace = lockOnSpace(target);
			if(lockSpace.getCode() != CallBackResult.CODE_OK) {
				//TODO:这问题可严重了...
			}
		}
		*/
		
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
	protected CallBackResult lockOnSpace(ConfirmTarget target) {
		//将车位上锁
		String address = target.getAddress();
		Integer port = target.getPort();
		//新建指令对象
		ConnCommand command = new ConnCommand();
		//根据密钥获取 连接校验码
		String connCode = CodeUtils.getConnCode(target.getConn_key())[0];
		
		command.setNodeId(target.getNode_id());
		command.setOrder(ConnCommand.ORDER_LOCK_ON); //关闭锁
		command.setCode(connCode);
		//发送上锁请求
		CallBackResult result = sockConn.client(address,port, command);
		
		return result;
	}
	
}
