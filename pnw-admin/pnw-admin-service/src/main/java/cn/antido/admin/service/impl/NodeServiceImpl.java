package cn.antido.admin.service.impl;

import java.util.Date;
import java.util.List;

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
import cn.antido.admin.mapper.ParkMapper;
import cn.antido.admin.mapper.SpaceMapper;
import cn.antido.admin.pojo.ConnCallBack;
import cn.antido.admin.pojo.Node;
import cn.antido.admin.pojo.NodeAddress;
import cn.antido.admin.pojo.Space;
import cn.antido.admin.pojo.filter.DaoFilter;
import cn.antido.admin.service.NodeService;
import cn.antido.admin.service.thread.PulseTimeOutListener;
import cn.antido.common.redis.JedisClient;

/**
 * @Description NodeServiceImpl
 * @author Antido
 * @date 2018年3月21日 下午7:21:22
 */
@Service
@Transactional
public class NodeServiceImpl implements NodeService {
	
	@Autowired
	private NodeMapper nodeMapper;
	
	@Autowired
	private NodeAddressMapper nodeAddressMapper;
	
	@Autowired
	private SpaceMapper spaceMapper;
	
	@Autowired
	private ParkMapper parkMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	//Spring提供的JMS工具类 ，用来发送和接收消息
	@Autowired
	private JmsTemplate jmsTemplate;
	
	//根据id注入 消息名为parkAddTopicDestination
	@Resource
	private Destination addRemainQueueDestination;
	
	@Resource
	private Destination subRemainQueueDestination;
	
	@Resource
	private Destination addWorkingQueueDestination;
	
	@Resource
	private Destination subWorkingQueueDestination;
	
	/**
	 * 注入配置文件
	 */
	@Value("${node.connection.timeOut}")
	private Long timeOut;
	
	@Value("${node.connection.sleepTime}")
	private Long sleepTime;
	
	@Value("${node.connection.confirmPath}")
	private String confirmPath;
	
	@Value("${redis.key.pulse}")
	private String pulse;
	
	
	private PulseTimeOutListener timeOutListener;
	
	/**
	 * 根据parentId获取所有未绑定车位的节点
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Node> getFreeNodeByParentId(String parentId) {
		
		DaoFilter filter = new DaoFilter();
		filter.addAndCriteria("parent_id = ", parentId);
		filter.addAndCriteria("space_id is null");
		
		List<Node> list = nodeMapper.selectByFilter(filter);
		
		return list;
	}
	
	/**
	 * 插入一个节点
	 */
	@Override
	public void insert(Node node) {
		node.setIs_lock(false);
		node.setIs_close(false);
		node.setIs_online(true);
		node.setCreated(new Date());
		
		nodeMapper.insertByNode(node);
		
	}
	
	/**
	 * 更新上线信息<br>
	 * 当节点已经绑定车位时更新节点车位状态<br>
	 * 更新停车场车位数量状态
	 */
	@Override
	public void onlineNode(Node node) {
		node.setIs_online(true);
		node.setUpdated(new Date());
		Node nodeInDB = nodeMapper.selectByPrimaryKey(node.getId());
		if(nodeInDB == null) {
			return ;
		}
		Long space_id = nodeInDB.getSpace_id();
		if(space_id != null) {
			Space space = spaceMapper.selectByPrimaryKey(space_id);
			if(space != null) {
				Byte running_state = space.getRunning_state();
				//该车位时正在运行的车位
				if(running_state == Space.RUNNING_STATE_INUSE) {
					Byte using_state = space.getUsing_state();
					if(using_state == Space.USING_STATE_FREE) { //当上线节点是未被使用节点时
						//将停车场可用节点数加一
						final Long parkId = space.getPark().getId();
						parkMapper.addWorkingCount(parkId);
						//parkMapper.addUsingCount(space.getPark().getId());
						
						//正常工作车位数增加
						jmsTemplate.send(addWorkingQueueDestination,new MessageCreator() {
							
							@Override
							public Message createMessage(Session session) throws JMSException {
								TextMessage textMessage = session.createTextMessage(parkId+"");
								return textMessage;
							}
						});
						
						//车位剩余数也会增加
						jmsTemplate.send(addRemainQueueDestination,new MessageCreator() {
							@Override
							public Message createMessage(Session session) throws JMSException {
								TextMessage textMessage = session.createTextMessage(parkId+"");
								return textMessage;
							}
						});
						
						
					} else {
						//TODO: 一个节点在上线时正处于被使用的状态
					}
				}
			}
		}
		
		nodeMapper.updateIsOnline(node);
	}
	
	/**
	 * 更新下线信息
	 */
	@Override
	public void outlineNode(Node node) {
		node.setIs_online(false);
		node.setUpdated(new Date());
		Node nodeInDB = nodeMapper.selectByPrimaryKey(node.getId());
		if(nodeInDB == null) {
			return ;
		}
		Long space_id = nodeInDB.getSpace_id();
		if(space_id != null) {
			Space space = spaceMapper.selectByPrimaryKey(space_id);
			if(space != null) {
				Byte running_state = space.getRunning_state();
				//该车位时正在运行的车位
				if(running_state == Space.RUNNING_STATE_INUSE) {
					Byte using_state = space.getUsing_state();
					if(using_state == Space.USING_STATE_FREE) { //当下线线节点是未被使用节点时
						//将停车场可用节点数加一
						final Long parkId = space.getPark().getId();
						parkMapper.subWorkingCount(parkId);
						//parkMapper.addUsingCount(space.getPark().getId());
						
						//正常工作车位数增加
						jmsTemplate.send(subWorkingQueueDestination,new MessageCreator() {
							
							@Override
							public Message createMessage(Session session) throws JMSException {
								TextMessage textMessage = session.createTextMessage(parkId+"");
								return textMessage;
							}
						});
						
						//车位剩余数也会增加
						jmsTemplate.send(subRemainQueueDestination,new MessageCreator() {
							@Override
							public Message createMessage(Session session) throws JMSException {
								TextMessage textMessage = session.createTextMessage(parkId+"");
								return textMessage;
							}
						});
						
						
					} else {
						//TODO: 一个节点在下线时正处于被使用的状态
					}
				}
			}
		}
		nodeMapper.updateIsOnline(node);
		
	}
	
	/**
	 * 更新接近信息 
	 */
	@Override
	public void nodeClosed(Node node) {
		node.setIs_close(true);
		nodeMapper.updateIsClose(node);
	}
	
	/**
	 * 更新离开信息
	 */
	@Override
	public void nodeLeft(Node node) {
		node.setIs_close(false);
		nodeMapper.updateIsClose(node);
		
	}
	
	/**
	 * 收到一个控制器的注册信息<br>
	 * 更新控制器地址注册表<br>
	 */
	@Override
	public void regis(NodeAddress nodeAddress) {
		NodeAddress old = nodeAddressMapper.selectByNodeAddress(nodeAddress);
		if(old == null) {
			nodeAddress.setState((byte)1);
			nodeAddress.setCreated(new Date());
			nodeAddressMapper.insert(nodeAddress);
		} else { // 节点被记录过,可能改变了通讯地址,或者状态
			nodeAddress.setState((byte)1);
			nodeAddress.setUpdated(new Date());
			nodeAddressMapper.updateState(nodeAddress);
			//TODO: 根据情况需要作出一些处理
		}		
	}
	
	/**
	 * 收到协调器心跳消息<br>
	 * 每次收到心跳时将当前时间作为score,ID为member<br>
	 * 启动监听线程定时查询score小于一定值成员,可是判断查询到的成员是长时间未接到心跳的,可能已经失联<br>
	 * 向可能失联的控制器地址发送确认消息,如果未收到反馈,可断定目标节点已断线. 
	 */
	@Override
	@Transactional(readOnly=true)
	public void pulse(String id) {
		//第一次加载此方法时启动监听线程
		if(this.timeOutListener == null) {
			synchronized (this) {
				if(this.timeOutListener == null) {
					this.timeOutListener = new PulseTimeOutListener(jedisClient, pulse, timeOut, sleepTime);
					this.timeOutListener.setConfirmPath(confirmPath);
					this.timeOutListener.setNodeAddressMapper(nodeAddressMapper);
					this.timeOutListener.setNodeMapper(nodeMapper);
					//启动
					Thread thread = new Thread(timeOutListener);
					thread.start();
				}
			}
		}
		
		//更新为当前时间戳
		long currentTime = System.currentTimeMillis();
		jedisClient.zadd("pulse", currentTime, id);
	}
	
	/**
	 * 根据通讯地址获取对方状态
	 */
	@Override
	public ConnCallBack conn(String address) {
		NodeAddress nodeAddress = nodeAddressMapper.selectByAddress(address);
		
		if(nodeAddress == null) 
			return ConnCallBack.EXCEPTION("该地址未被注册");
		if(nodeAddress.getState() != 1) 
			return ConnCallBack.EXCEPTION("该地址正处于离线状态");
			
		return ConnCallBack.OK();
		
	}
	
}
