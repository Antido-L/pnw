package cn.antido.connection.service.impl;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

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

import cn.antido.admin.mapper.NodeAddressMapper;
import cn.antido.admin.mapper.NodeMapper;
import cn.antido.admin.mapper.OrderMapper;
import cn.antido.admin.mapper.ParkMapper;
import cn.antido.admin.mapper.SpaceMapper;
import cn.antido.admin.mapper.UserMapper;
import cn.antido.admin.pojo.Node;
import cn.antido.admin.pojo.NodeAddress;
import cn.antido.admin.pojo.Order;
import cn.antido.admin.pojo.Park;
import cn.antido.admin.pojo.Space;
import cn.antido.admin.pojo.User;
import cn.antido.common.CallBackResult;
import cn.antido.common.pojo.ConnCommand;
import cn.antido.common.redis.JedisClient;
import cn.antido.common.utils.CodeUtils;
import cn.antido.common.utils.JsonUtils;
import cn.antido.common.utils.PriceUtils;
import cn.antido.common.utils.UUIDUtils;
import cn.antido.connection.pojo.ConfirmTarget;
import cn.antido.connection.pojo.OrderInfoDTO;
import cn.antido.connection.service.CommandService;
import cn.antido.connection.service.list.CheckAndRemoveList;
import cn.antido.connection.service.socket.SocketConn;

/**
 * @Description 节点指令操作实现类
 * @author Antido
 * @date 2018年5月25日 下午2:36:39
 */
@Service
@Transactional
public class CommandServiceImpl implements CommandService {
	
	public final static byte ORDER_LOCK_ON = 1;
	public final static byte ORDER_LOCK_OFF = 0;
	
	/**
	 * redis中用户token的命名头部
	 */
	@Value("${redis.session.SESSION_NAME}")
	private String SESSION_NAME;
	
	@Value("${redis.session.SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;

	/**
	 * redis中待确认的用户集合,供多个服务器共同操作
	 */
	@Value("${confirm.redis.setName}")
	private String CONFIRM_SET_NAME;
	
	/**
	 * redis中已经预约的用户,供多个服务器共同操作
	 */
	@Value("${reserve.redis.setName}")
	private String RESERVE_SET_NAME;
	
	/**
	 * confirmTarget中维护的超时时间<br>
	 * 即停车确认的超时时间
	 */
	@Value("${confirm.target.expire}")
	private Integer CONFIRM_EXPIRE;
	
	/**
	 * confirmTarget中维护的超时时间<br>
	 * 即预约的超时时间
	 */
	@Value("${reserve.target.expire}")
	private Integer RESERVE_EXPIRE;
	
	@Autowired
	private NodeAddressMapper nodeAddressMapper;
	
	@Autowired
	private ParkMapper parkMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private SpaceMapper spaceMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private NodeMapper nodeMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Autowired
	private SocketConn sockConn;
	
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
	 * 减少剩余车辆消息
	 */
	@Resource
	private Destination subRemainQueueDestination; 
	
	/**
	 * 与ConfirmLimitListener共同维护的停车超时监听对象队列
	 */
	@Resource
	private LinkedBlockingQueue<ConfirmTarget> confirmParkQueue;
	
	/**
	 * 目标离开监听队列
	 */
	@Resource
	private CheckAndRemoveList<ConfirmTarget> confirmLeaveList;
	
	/**
	 * 目标预约超时监听队列
	 */
	@Resource
	private LinkedBlockingQueue<ConfirmTarget> confirmReserveList;
	
	/**
	 * 使用一个车位<br>
	 * 
	 * 根据控制器ID查询数据库获取 目标通讯地址与端口号<br>
	 * 更新使用车位后的需要发生改变的各项数据,在数据库操作中发生异常将不会向目标控制器发送指令<br>
	 * 新建socket client 发送消息<br>
	 * 数据更新完毕后向目标控制器发送指定,如果指令操作失败 将抛出异常 该异常在函数体内,会回滚之前的数据库操作<br>
	 * 操作成功后更新solr索引库<br>
	 * 返回结果封装对象 
	 */
	@Override
	public CallBackResult useSpace(OrderInfoDTO info) {
		//判断是否是有效的连接对象
		if(info == null) 
			return CallBackResult.error("OrderInfoDTO is null");
		
		if(info.getControllerId() == null) 
			return CallBackResult.error("ControllerId is null");
		
		NodeAddress nodeAddress = nodeAddressMapper.selectById(info.getControllerId());
		if(nodeAddress == null) {
			return CallBackResult.error("未找到匹配的通讯地址");
		}
		
		if(nodeAddress.getState() != 1) {
			return CallBackResult.error("目标控制器 处于非正常状态");
		}
		
		//验证操作码
		String opCode = userMapper.getOpCode(info.getUserId());
		if(!opCode.equals(info.getOpCode())) {
			return CallBackResult.exception("操作码错误");
		}
		
		//验证支付能力
		Integer wallet = userMapper.getWallet(info.getUserId());
		if(wallet <= 0) {
			return CallBackResult.exception("余额不足");
		}
		
		//从注册中心的缓存中获取User
		String userStr = jedisClient.get(SESSION_NAME + info.getToken());
		User user = JsonUtils.json2Obj(userStr, User.class);
		if(user.getSpace() != null && user.getSpace().getId() != null) {
			return CallBackResult.exception("用户有未结算订单");
		}
		
		boolean isVirtual = false;
		if(nodeAddress.getAddress().startsWith("192.168.0." )) {
			isVirtual = true;
		}
		
		
		//获取需要变化的Park对象和Space对象
		Park park = parkMapper.selectByPrimaryKey(info.getParkId());
		final Long parkId = park.getId();
		Space space = spaceMapper.selectByPrimaryKey(info.getSpaceId());
		
		
		if(space.getUsing_state() == 1) {
			return CallBackResult.error("车位已经被使用了");
		}
		
		Order order = null;
		//该车位被预约过
		if(space.getReserve_time() != null) {
			if(user.getState() != User.STATE_RESERVING && !user.getSpace().getId().equals(space.getId())) {
				return CallBackResult.exception("该车位已经被预约,现在无法使用");
			} else {
				
				//更新停车场数据状态 using_count+1
				//parkMapper.addUsingCount(info.getParkId());
				
				//更改被预约的订单 新增停车时间
				order = orderMapper.selectByPrimaryKey(space.getOrder_id());
				order.setUsing_time(new Date());
				
				orderMapper.updateByOrder(order);
				
				//更新车位数据using_state,parked_time
				space.setUsing_state(Space.USING_STATE_USED);
				space.setParked_time(new Date());
				space.setUpdated(new Date());
				space.setUser_id(user.getId()); //将车位与用户关联
				//将车位与当前订单关联
				space.setOrder_id(order.getId());
				//设置预计离开时间
				Integer leavingTime = info.getLeavingTime();
				if(leavingTime != null && leavingTime > 0) {
					space.setLeaving_time(new Date(new Date().getTime() + leavingTime * 1000));
				}
				
				
				spaceMapper.updateBySpace(space);
				
				//TODO:更新二级缓存
			}
		} else { //该车位没有被预约,直接使用
			//更新节点 锁状态
			
			//更新停车场数据状态 using_count+1
			parkMapper.addUsingCount(info.getParkId());
			
			//生成订单表order
			order = new Order();
			order.setId(UUIDUtils.getUUID32());	
			order.setPay_state(Order.PAY_STATE_NOT_PAY); //未支付
			order.setCreated(new Date());
			order.setFree_time(park.getFree_time()); //当前订单的免费时间
			
			Integer price = park.getPrice();
			order.setPrice(price);
			order.setSpace(space);
			order.setUsing_time(new Date());
			order.setUser(user); //关联用户数据与订单数据
			/*
			 //非免费停车场
			 if(price > 0) { 
				order.setPay_type(pay_type);
			}*/
			orderMapper.insertByOrder(order);
			
			//更新车位数据using_state,parked_time
			space.setUsing_state(Space.USING_STATE_USED);
			space.setParked_time(new Date());
			space.setUpdated(new Date());
			space.setUser_id(user.getId()); //将车位与用户关联
			//将车位与当前订单关联
			space.setOrder_id(order.getId()); 
			
			//设置预计离开时间
			Integer leavingTime = info.getLeavingTime();
			if(leavingTime != null && leavingTime > 0) {
				space.setLeaving_time(new Date(new Date().getTime() + leavingTime * 1000));
			}
			spaceMapper.updateBySpace(space);
			space.setPark(park);
			//更新数据库中的用户表
			user.setSpace(space); //将车位数据与用户数据关联
			user.setPark_id(parkId); //将停车场与用户关联
			user.setState(User.STATE_CHECK_WAIT); //等待用户停车确认
			//userMapper.updateState(user);
			userMapper.updateByUser(user);
			
			//更新注册中心二级缓存中的User
			String json = JsonUtils.obj2Json(user);
			jedisClient.set(SESSION_NAME + info.getToken(), json);
			jedisClient.expire(SESSION_NAME + info.getToken(), SESSION_EXPIRE);
			//TODO:更新二级缓存
		}
		
		
		if(isVirtual) { //如果是虚拟停车场 直接将节点接近状态改为true 锁状态改为true
			Node node = new Node();
			node.setId(info.getNodeId());
			node.setIs_lock(true);
			node.setIs_close(true);
			nodeMapper.updateByNode(node);
		} else { //如果不是虚拟停车场 向目标控制器发送指令
			//新建socketClient,指令对象
			ConnCommand command = new ConnCommand();
			//根据密钥获取 连接校验码
			String connCode = CodeUtils.getConnCode(nodeAddress.getConn_key())[0];
			
			command.setNodeId(info.getNodeId());
			command.setOrder(ConnCommand.ORDER_LOCK_ON);
			command.setCode(connCode);
			
			CallBackResult connRes = sockConn.client(nodeAddress.getAddress(), nodeAddress.getPort(), command);
			
			//当指令操作失败,抛出异常 回滚数据
			if(connRes.getCode() != CallBackResult.CODE_OK) {
				throw new RuntimeException(nodeAddress.getAddress() + "指令操作失败");
				
			}
		}
		
		//在redis中新建一个SET 
		//SET里保存userId
		//当用户确认时移除SET中的对应的userId
		//在当前服务器中启动监听线程ComfirmLimitListener 在CommandServiceImpl中维护与监听线程中相同的数据结构
		//在ComfirmLimitListener中的数据结构需要包含停车失败后续处理中需要使用的所有数据
		//在监听线程中当对应车位时间到期后查询SET
		//如果SET中查询到匹配的userID 证明此次停车已经过期
		//如果SET中查询不到,证明停车已经确认
		
		//加入监听队列
		ConfirmTarget target = new ConfirmTarget();
		target.setAddress(nodeAddress.getAddress());
		target.setConn_key(nodeAddress.getConn_key());
		target.setExpire(System.currentTimeMillis() + CONFIRM_EXPIRE);
		target.setNode_id(info.getNodeId());
		target.setParkId(parkId);
		target.setPort(nodeAddress.getPort());
		target.setSpaceId(space.getId());
		target.setToken(info.getToken());
		target.setUserId(user.getId());
		target.setOrderId(order.getId());
		confirmParkQueue.offer(target);
		
		//在redis加入待确认用户
		jedisClient.sadd(CONFIRM_SET_NAME, user.getId());
		//在停车确认请求中会将CONFIRM_SET中对应的监听对象删除
		
		//当车位没有被预约直接使用了,需要更新索引库车位数量
		if(space.getReserve_time() == null) {
			jmsTemplate.send(subRemainQueueDestination, new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					//发送要修改的停车场id
					TextMessage message = session.createTextMessage(parkId + "");
					return message;
				}
			});
		}
		
		return CallBackResult.ok();
	}

	
	/**
	 * 释放一个车位<br>
	 * 
	 * 根据控制器ID查询数据库获取 目标通讯地址与端口号<br>
	 * 更新使用车位后的需要发生改变的各项数据,在数据库操作中发生异常将不会向目标控制器发送指令<br>
	 * 新建socket client 发送消息<br>
	 * 数据更新完毕后向目标控制器发送指定,如果指令操作失败 将抛出异常 该异常在函数体内,会回滚之前的数据库操作<br>
	 * 操作成功后更新solr索引库<br>
	 * 返回结果封装对象 
	 */
	@Override
	public CallBackResult leaveSpace(OrderInfoDTO info) {
		//判断是否是有效的连接对象
		if(info == null) 
			return CallBackResult.error("OrderInfoDTO is null");
		
		if(info.getControllerId() == null) 
			return CallBackResult.error("ControllerId is null");
		
		NodeAddress nodeAddress = nodeAddressMapper.selectById(info.getControllerId());
		//在这里我想要做一些虚拟的停车场
		//这些停车场不需要通讯就能操作
		
		if(nodeAddress == null) {
			return CallBackResult.error("未找到匹配的通讯地址");
		}
		
		if(nodeAddress.getState() != 1) {
			return CallBackResult.error("目标控制器 处于非正常状态");
		}
		
		//验证操作码
		String opCode = userMapper.getOpCode(info.getUserId());
		if(!opCode.equals(info.getOpCode())) {
			return CallBackResult.exception("操作码错误");
		}
		
		boolean isVirtual = false;
		
		//Space space = spaceMapper.selectByPrimaryKey(info.getSpaceId());
		//从注册中心的缓存中获取User
		String userStr = jedisClient.get(SESSION_NAME + info.getToken());
		if(userStr == null || "".equals(userStr)) {
			return CallBackResult.error("当前用户无权操作此车位");
		}
		
		User user = JsonUtils.json2Obj(userStr, User.class);
		
		if(!user.getSpace().getId().equals(info.getSpaceId())) {
			return CallBackResult.error("当前用户无权操作此车位");
		}
		
		//将用户状态设置为离开确认等待
		user.setState(User.STATE_LEAVE_WAIT);
		String json = JsonUtils.obj2Json(user);
		userMapper.updateState(user);
		jedisClient.set(SESSION_NAME + info.getToken(), json);
		jedisClient.expire(SESSION_NAME + info.getToken(), SESSION_EXPIRE);
		
		Space space = user.getSpace();
		
		//验证操作权限
		if(!space.getId().equals(info.getSpaceId())) {
			return CallBackResult.error("当前用户无权操作此车位");
		}

		//判断用户的支付能力
		Integer wallet = userMapper.getWallet(info.getUserId());
		if(wallet <= 0) {
			return CallBackResult.exception("余额不足");
		}
		
		if(nodeAddress.getAddress().startsWith("192.168.0.")) {
			isVirtual = true;
			//将节点接近状态改为false;
		}
		
		Boolean isClose = false;
		if(isVirtual) {
			Node node = new Node();
			node.setId(info.getNodeId());
			node.setIs_close(false);
			nodeMapper.updateIsClose(node);
		} else {
			//判断接近状态
			isClose = nodeMapper.isClose(info.getNodeId());
		}
		
		if(isClose) { //仍然处于接近状态
			//将该目标加入监听队列中
			ConfirmTarget target = new ConfirmTarget();
			target.setAddress(nodeAddress.getAddress());
			target.setConn_key(nodeAddress.getConn_key());
			target.setExpire(System.currentTimeMillis() + CONFIRM_EXPIRE);
			target.setNode_id(info.getNodeId());
			target.setParkId(info.getParkId());
			target.setPort(nodeAddress.getPort());
			target.setSpaceId(space.getId());
			target.setToken(info.getToken());
			target.setUserId(user.getId());
			//target.setOrderId(order.getId());
			
			confirmLeaveList.add(target);
			return CallBackResult.ok();
		} else {
			//TODO: 验证结算
			//关闭车位
			//同步数据
			//车位释放后处理
			CallBackResult connRes = spaceLeaveHandler(info, nodeAddress, user);
			return connRes;
		}
		
	}

	/**
	 * 重载方法<br>
	 * 用于在释放车辆离开监听队列中的目标
	 * @param target
	 * @return
	 */
	public CallBackResult spaceLeaveHandler(ConfirmTarget target) {
		
		/**
		 * 重新从redis中获取User
		 */
		String token = target.getToken();
		String userStr = jedisClient.get(SESSION_NAME + token);
		if(userStr == null || "".equals(userStr)) {
			return CallBackResult.error("当前用户无权操作此车位");
		}
		
		User user = JsonUtils.json2Obj(userStr, User.class);
		
		OrderInfoDTO info = new OrderInfoDTO();
		info.setParkId(target.getParkId());
		info.setSpaceId(target.getSpaceId());
		info.setToken(target.getToken());
		info.setNodeId(target.getNode_id());
		
		NodeAddress nodeAddress = new NodeAddress();
		nodeAddress.setAddress(target.getAddress());
		nodeAddress.setConn_key(target.getConn_key());
		nodeAddress.setNode_id(target.getNode_id());
		nodeAddress.setPort(target.getPort());
		
		CallBackResult result = spaceLeaveHandler(info, nodeAddress, user);
		//TODO: 给予用户反馈
		
		
		return result;
	}
	
	/**
	 * 车辆离开<br>
	 * 释放车位,验证结算,完成订单,同步数据
	 * @param info
	 * @param nodeAddress
	 * @param user 从redis中获取的user
	 * @return
	 */
	private CallBackResult spaceLeaveHandler(OrderInfoDTO info, NodeAddress nodeAddress, User user) {
		
		Park park = parkMapper.selectByPrimaryKey(info.getParkId());
		final Long parkId = park.getId();
		Space space = spaceMapper.selectByPrimaryKey(info.getSpaceId());
		//更新节点 锁状态
		
		//更新停车场使用数数据状态 using_count-1
		parkMapper.subUsingCount(info.getParkId());
		
		//完成订单
		Order order = orderMapper.selectByPrimaryKey(space.getOrder_id());//获取之前与车位关联的订单
		order.setEnd_time(new Date());
		//验证结算
		Integer price = order.getPrice();
		Integer subtotal = 0;
		if(price > 0) { //该停车不免费
			//计算订单小计
			Date start = null;
			//如果车位被预约过,计费时间从预约时间开始
			if(order.getReserve_time() != null) {
				start = order.getReserve_time();
			} else {
				start = order.getUsing_time();
			}
			subtotal = PriceUtils.subtotal(start, order.getEnd_time(), price, order.getFree_time());
			
			//扣款
			Integer wallet = userMapper.getWallet(info.getUserId());
			User temp = new User();
			temp.setId(info.getUserId());
			temp.setWallet(wallet - subtotal);
			userMapper.updateWallet(temp);
			
		}
		
		/**
		 * TODO:目前先全使用钱包缴费吧
		 */
		order.setPay_type(Order.PAY_TYPE_WALLET);
		
		order.setPay_state(Order.PAY_STATE_HAVE_PAY); //已经支付
		
		order.setSubtotal(subtotal);
		//更新订单状态
		orderMapper.updateByOrder(order);
		
		//更新车位数据using_state,parked_time
		space.setUsing_state(Space.USING_STATE_FREE); //自由状态
		space.setUpdated(new Date());
		space.setUser_id(null); //将车位与用户取消关联
		space.setOrder_id(null); //将车位与订单取消关联
		spaceMapper.updateBySpace(space);
		spaceMapper.removeTimeItem(space.getId()); //将车位各项使用时间写NULL
		
		
		//更新数据库中的用户表
		user.setSpace(null); //将车位数据与用户数据取消关联
		user.setPark_id(null); //将停车场与用户取消关联
		user.setState(User.STATE_NORMAL);
		userMapper.updateState(user);
		userMapper.updateByUser(user);
		
		//更新注册中心二级缓存中的User
		String json = JsonUtils.obj2Json(user);
		jedisClient.set(SESSION_NAME + info.getToken(), json);
		jedisClient.expire(SESSION_NAME + info.getToken(), SESSION_EXPIRE);
		//TODO:更新二级缓存
		
		if(nodeAddress.getAddress().startsWith("192.168.0.")) { //为虚拟停车场
			Node node = new Node();
			node.setId(info.getNodeId());
			node.setIs_lock(false);
			nodeMapper.updateIsLock(node);
		} else {
			//新建socketClient,指令对象
			ConnCommand command = new ConnCommand();
			//根据密钥获取 连接校验码
			String connCode = CodeUtils.getConnCode(nodeAddress.getConn_key())[0];
			
			command.setNodeId(info.getNodeId());
			command.setOrder(ConnCommand.ORDER_LOCK_OFF);
			command.setCode(connCode);
			
			CallBackResult connRes = sockConn.client(nodeAddress.getAddress(), nodeAddress.getPort(), command);
			
			//当指令操作失败,抛出异常 回滚数据
			if(connRes.getCode() != CallBackResult.CODE_OK) {
				throw new RuntimeException(nodeAddress.getAddress() + "指令操作失败");
				
			}
		}
		
		//更新索引库车位数量
		jmsTemplate.send(addRemainQueueDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				//发送要修改的停车场id
				TextMessage message = session.createTextMessage(parkId + "");
				return message;
			}
		});
		
		//TODO: 给予用户反馈
		
		return CallBackResult.ok();
	}
	
	
	/*private CallBackResult controlSpace(NodeAddress nodeAddress, String nodeId) {
		//新建socketClient,指令对象
		ConnCommand command = new ConnCommand();
		//根据密钥获取 连接校验码
		String connCode = CodeUtils.getConnCode(nodeAddress.getConn_key())[0];
		
		command.setNodeId(nodeId);
		command.setOrder(ConnCommand.ORDER_LOCK_OFF);
		command.setCode(connCode);
		
		CallBackResult connRes = sockConn.client(nodeAddress.getAddress(), nodeAddress.getPort(), command);
		
		return connRes;
	}*/

	/**
	 * 预约一个车位<br>
	 * 
	 * 根据控制器ID查询数据库获取 目标通讯地址与端口号<br>
	 * 更新使用车位后的需要发生改变的各项数据,在数据库操作中发生异常将不会向目标控制器发送指令<br>
	 * 新建socket client 发送消息<br>
	 * 数据更新完毕后向目标控制器发送指定,如果指令操作失败 将抛出异常 该异常在函数体内,会回滚之前的数据库操作<br>
	 * 操作成功后更新solr索引库<br>
	 * 返回结果封装对象 
	 */
	@Override
	public CallBackResult reserveSpace(OrderInfoDTO info) {
		//判断是否是有效的连接对象
		if(info == null) 
			return CallBackResult.error("OrderInfoDTO is null");
		
		if(info.getControllerId() == null) 
			return CallBackResult.error("ControllerId is null");
		
		NodeAddress nodeAddress = nodeAddressMapper.selectById(info.getControllerId());
		if(nodeAddress == null) {
			return CallBackResult.error("未找到匹配的通讯地址");
		}
		
		if(nodeAddress.getState() != 1) {
			return CallBackResult.error("目标控制器 处于非正常状态");
		}
		
		//验证支付能力
		Integer wallet = userMapper.getWallet(info.getUserId());
		if(wallet <= 0) {
			return CallBackResult.exception("余额不足");
		}
		
		//验证操作码
		String opCode = userMapper.getOpCode(info.getUserId());
		if(!opCode.equals(info.getOpCode())) {
			return CallBackResult.exception("操作码错误");
		}
		
		String userStr = jedisClient.get(SESSION_NAME + info.getToken());
		User user = JsonUtils.json2Obj(userStr, User.class);
		if(user.getSpace() != null && user.getSpace().getId() != null) {
			return CallBackResult.exception("用户有未结算的订单");
		}
		
		Park park = parkMapper.selectByPrimaryKey(info.getParkId());
		final Long parkId = park.getId();
		Space space = spaceMapper.selectByPrimaryKey(info.getSpaceId());
		//从注册中心的缓存中获取User
		
		//更新节点 锁状态
		
		//更新停车场数据状态 using_count+1
		parkMapper.addUsingCount(info.getParkId());
		
		//生成订单表order
		Order order = new Order();
		order.setId(UUIDUtils.getUUID32());	
		order.setPay_state(Order.PAY_STATE_NOT_PAY); //未支付
		order.setCreated(new Date());
		order.setFree_time(park.getFree_time()); //当前订单的免费时间
		
		Integer price = park.getPrice();
		order.setPrice(price);
		order.setSpace(space);
		order.setReserve_time(new Date());
		order.setUser(user); //关联用户数据与订单数据
		
		orderMapper.insertByOrder(order);
		
		//更新车位数据using_state,parked_time
		space.setUsing_state(Space.USING_STATE_RESERVED);
		space.setReserve_time(new Date());
		space.setUpdated(new Date());
		space.setUser_id(user.getId()); //将车位与用户关联
		//将车位与当前订单关联
		space.setOrder_id(order.getId()); 
		spaceMapper.updateBySpace(space);
		
		space.setPark(park);
		//更新数据库中的用户表
		user.setSpace(space); //将车位数据与用户数据关联
		user.setPark_id(parkId); //将停车场与用户关联
		user.setState(User.STATE_RESERVING); //将用户设为已有预约
		
		//userMapper.updateState(user);
		userMapper.updateByUser(user);
		
		//更新注册中心二级缓存中的User
		String json = JsonUtils.obj2Json(user);
		jedisClient.set(SESSION_NAME + info.getToken(), json);
		jedisClient.expire(SESSION_NAME + info.getToken(), SESSION_EXPIRE);
		//更新二级缓存
		
		/*
		//判断是否为虚拟停车场
		if(nodeAddress.getAddress().startsWith("192.168.0.")) {
			Node node = new Node();
			node.setIs_lock(true);
			nodeMapper.updateIsLock(node);
		} else {
			//新建socketClient,指令对象
			ConnCommand command = new ConnCommand();
			//根据密钥获取 连接校验码
			String connCode = CodeUtils.getConnCode(nodeAddress.getConn_key())[0];
			
			command.setNodeId(info.getNodeId());
			command.setOrder(ConnCommand.ORDER_LOCK_ON);
			command.setCode(connCode);
			
			CallBackResult connRes = sockConn.client(nodeAddress.getAddress(), nodeAddress.getPort(), command);
			
			//当指令操作失败,抛出异常 回滚数据
			if(connRes.getCode() != CallBackResult.CODE_OK) {
				throw new RuntimeException(nodeAddress.getAddress() + "指令操作失败");
				
			}
		}
		*/
		//将当前预约加入监听队列
		//在redis中加入待确认
		
		//加入监听队列
		ConfirmTarget target = new ConfirmTarget();
		target.setAddress(nodeAddress.getAddress());
		target.setConn_key(nodeAddress.getConn_key());
		target.setExpire(System.currentTimeMillis() + RESERVE_EXPIRE);
		target.setNode_id(info.getNodeId());
		target.setParkId(parkId);
		target.setPort(nodeAddress.getPort());
		target.setSpaceId(space.getId());
		target.setToken(info.getToken());
		target.setUserId(user.getId());
		target.setOrderId(order.getId());
		confirmReserveList.offer(target);
		
		//在redis加入待确认用户
		jedisClient.sadd(RESERVE_SET_NAME, user.getId());
		//在停车确认请求中会将RESERVE_SET_NAME中对应的监听对象删除
		
		
		//更新索引库车位数量
		jmsTemplate.send(subRemainQueueDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				//发送要修改的停车场id
				TextMessage message = session.createTextMessage(parkId + "");
				return message;
			}
		});
		
		return CallBackResult.ok();
	}


	/**
	 * 用户停车确认请求
	 */
	@Override
	public CallBackResult parkConfirm(String userToken) {
		//判断是否是有效的连接对象
		if(userToken == null || "".equals(userToken)) 
			return CallBackResult.error("userToken is null");
		
		//获取user
		String userStr = jedisClient.get(SESSION_NAME + userToken);
		if(userStr == null || "".equals(userStr)) {
			return CallBackResult.error("注册中心里未找到目标用户");
		}
		User user = JsonUtils.json2Obj(userStr, User.class);
		
		Space space = user.getSpace();
		if(space == null) {
			return CallBackResult.error("该用户没有正在使用的车位");
		}
		
		space = spaceMapper.selectByPrimaryKey(space.getId());
		
		//获取节点接近状态
		Boolean isClose = nodeMapper.isClose(space.getNode().getId());
		//当未检测到停靠时返回异常
		if(!isClose) {
			return CallBackResult.exception("未检测到车辆停靠");
		} 
		
		// 取消监听队列中的元素
		jedisClient.srem(CONFIRM_SET_NAME, user.getId());
		// 更改用户使用状态
		user.setState(User.STATE_NORMAL);
		//更新redis
		String json = JsonUtils.obj2Json(user);
		jedisClient.set(SESSION_NAME + userToken, json);
		jedisClient.expire(SESSION_NAME + userToken, SESSION_EXPIRE);
		//更新DB
		userMapper.updateState(user);
		
		return CallBackResult.ok();
	}
	
	/**
	 * 用户取消预约
	 */
	@Override
	public CallBackResult reserveCancel(String userToken, String opCode) {
		//判断是否是有效的连接对象
		if(userToken == null || "".equals(userToken)) 
			return CallBackResult.error("userToken is null");

		//获取user
		String userStr = jedisClient.get(SESSION_NAME + userToken);
		if(userStr == null || "".equals(userStr)) {
			return CallBackResult.error("注册中心里未找到目标用户");
		}
		User user = JsonUtils.json2Obj(userStr, User.class);
		
		Space space = user.getSpace();
		if(space == null) {
			return CallBackResult.error("该用户没有正在使用的车位");
		}
		
		//验证操作码
		String opCodeInDB = userMapper.getOpCode(user.getId());
		if(!opCodeInDB.equals(opCode)) {
			return CallBackResult.exception("操作码错误");
		}
		
		//更新停车场使用数数据状态 using_count-1
		final Long parkId = space.getPark().getId();
		parkMapper.subUsingCount(parkId);
		
		//完成订单
		Order order = orderMapper.selectByPrimaryKey(space.getOrder_id());//获取之前与车位关联的订单
		order.setEnd_time(new Date());
		//验证结算
		Integer price = order.getPrice();
		Integer subtotal = 0;
		if(price > 0) { //该停车不免费
			//计算订单小计
			Date start = null;
			//如果车位被预约过,计费时间从预约时间开始
			if(order.getReserve_time() != null) {
				start = order.getReserve_time();
			} else {
				start = order.getUsing_time();
			}
			subtotal = PriceUtils.subtotal(start, order.getEnd_time(), price, order.getFree_time());
			
			//扣款
			Integer wallet = userMapper.getWallet(user.getId());
			User temp = new User();
			temp.setId(user.getId());
			temp.setWallet(wallet - subtotal);
			userMapper.updateWallet(temp);
		}
		
		/**
		 * TODO:目前先全使用钱包缴费吧
		 */
		order.setPay_type(Order.PAY_TYPE_WALLET);
		
		order.setPay_state(Order.PAY_STATE_HAVE_PAY); //已经支付
		
		order.setSubtotal(subtotal);
		//更新订单状态
		orderMapper.updateByOrder(order);
		
		//更新车位数据using_state,parked_time
		space.setUsing_state(Space.USING_STATE_FREE); //自由状态
		space.setUpdated(new Date());
		space.setUser_id(null); //将车位与用户取消关联
		space.setOrder_id(null); //将车位与订单取消关联
		spaceMapper.updateBySpace(space);
		spaceMapper.removeTimeItem(space.getId()); //将车位各项使用时间写NULL
		
		
		//更新数据库中的用户表
		user.setSpace(null); //将车位数据与用户数据取消关联
		user.setPark_id(null); //将停车场与用户取消关联
		user.setState(User.STATE_NORMAL);
		//userMapper.updateState(user);
		userMapper.updateByUser(user);
		
		//更新注册中心二级缓存中的User
		String json = JsonUtils.obj2Json(user);
		jedisClient.set(SESSION_NAME + userToken, json);
		jedisClient.expire(SESSION_NAME + userToken, SESSION_EXPIRE);
		//TODO:更新二级缓存
		
		// 取消预约监听队列中的元素
		jedisClient.srem(RESERVE_SET_NAME, user.getId());
		
		//更新索引库车位数量
		jmsTemplate.send(addRemainQueueDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				//发送要修改的停车场id
				TextMessage message = session.createTextMessage(parkId + "");
				return message;
			}
		});
		
		return CallBackResult.ok();
	}

	/**
	 * 预约确认<br>
	 * 打开目标车位锁<br>
	 * 更新车位状态<br>
	 * 更新订单状态<br>
	 * 清除redis中的预约确认<br>
	 * 用户更新为停车确认状态
	 */
	@Override
	public CallBackResult reserveConfirm(String userToken, String opCode, Integer leavingTime) {
		//判断是否是有效的连接对象
		if(userToken == null || "".equals(userToken)) 
			return CallBackResult.error("userToken is null");

		//获取user
		String userStr = jedisClient.get(SESSION_NAME + userToken);
		if(userStr == null || "".equals(userStr)) {
			return CallBackResult.error("注册中心里未找到目标用户");
		}
		User user = JsonUtils.json2Obj(userStr, User.class);
		
		Space space = user.getSpace();
		if(space == null) {
			return CallBackResult.error("该用户没有正在使用的车位");
		}
		
		//验证支付能力
		Integer wallet = userMapper.getWallet(user.getId());
		if(wallet <= 0) {
			return CallBackResult.exception("余额不足");
		}
		
		//验证操作码
		String opCodeInDB = userMapper.getOpCode(user.getId());
		if(!opCodeInDB.equals(opCode)) {
			return CallBackResult.exception("操作码错误");
		}
		
		//更新订单 设置使用时间
		String order_id = space.getOrder_id();
		Order order = new Order();
		order.setId(order_id);
		order.setUsing_time(new Date());
		orderMapper.updateByOrder(order);
		
		if(leavingTime != null && leavingTime > 0) {
			space.setLeaving_time(new Date(new Date().getTime() + leavingTime * 1000));
		}
		
		//更新车位使用状态和使用时间
		space.setParked_time(new Date());
		space.setUsing_state(Space.USING_STATE_USED);
		spaceMapper.updateBySpace(space);
		
		//用户改为停车确认状态
		user.setState(User.STATE_CHECK_WAIT);
		userMapper.updateState(user);
		
		//获取通讯地址
		String controllerId = space.getPark().getNode().getId();
		NodeAddress nodeAddress = nodeAddressMapper.selectById(controllerId);
		//打开车位锁
		if(nodeAddress.getAddress().startsWith("192.168.0.")) {
			Node node = space.getNode();
			node.setIs_lock(false);
			node.setIs_close(true);
			nodeMapper.updateByNode(node);
		} else {
			//新建socketClient,指令对象
			ConnCommand command = new ConnCommand();
			//根据密钥获取 连接校验码
			String connCode = CodeUtils.getConnCode(nodeAddress.getConn_key())[0];
			
			command.setNodeId(space.getNode().getId());
			command.setOrder(ConnCommand.ORDER_LOCK_ON);
			command.setCode(connCode);
			
			CallBackResult connRes = sockConn.client(nodeAddress.getAddress(), nodeAddress.getPort(), command);
			
			//当指令操作失败,抛出异常 回滚数据
			if(connRes.getCode() != CallBackResult.CODE_OK) {
				throw new RuntimeException(nodeAddress.getAddress() + "指令操作失败");
			}
		}
		
		//加入停车监听队列
		ConfirmTarget target = new ConfirmTarget();
		target.setAddress(nodeAddress.getAddress());
		target.setConn_key(nodeAddress.getConn_key());
		target.setExpire(System.currentTimeMillis() + CONFIRM_EXPIRE);
		target.setNode_id(space.getNode().getId());
		target.setParkId(space.getPark().getId());
		target.setPort(nodeAddress.getPort());
		target.setSpaceId(space.getId());
		target.setToken(userToken);
		target.setUserId(user.getId());
		target.setOrderId(order.getId());
		confirmParkQueue.offer(target);
		
		//在redis加入待确认用户
		jedisClient.sadd(CONFIRM_SET_NAME, user.getId());
		//在停车确认请求中会将CONFIRM_SET中对应的监听对象删除
		
		// 取消预约监听队列中的元素
		jedisClient.srem(RESERVE_SET_NAME, user.getId());
		
		//更新redis
		String json = JsonUtils.obj2Json(user);
		jedisClient.set(SESSION_NAME + userToken, json);
		jedisClient.expire(SESSION_NAME + userToken, SESSION_EXPIRE);
		
		return CallBackResult.ok();
	}

}
