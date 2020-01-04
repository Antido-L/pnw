package cn.antido.admin.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.antido.admin.mapper.CarMapper;
import cn.antido.admin.mapper.NodeAddressMapper;
import cn.antido.admin.mapper.NodeMapper;
import cn.antido.admin.mapper.OrderMapper;
import cn.antido.admin.mapper.ParkMapper;
import cn.antido.admin.mapper.SpaceMapper;
import cn.antido.admin.mapper.UserMapper;
import cn.antido.admin.pojo.Car;
import cn.antido.admin.pojo.ConnCallBack;
import cn.antido.admin.pojo.Node;
import cn.antido.admin.pojo.NodeAddress;
import cn.antido.admin.pojo.Order;
import cn.antido.admin.pojo.Park;
import cn.antido.admin.pojo.Space;
import cn.antido.admin.pojo.User;
import cn.antido.admin.pojo.dto.SpaceDTO;
import cn.antido.admin.pojo.dto.SpaceInfoDTO;
import cn.antido.admin.pojo.dto.SpaceModelDTO;
import cn.antido.admin.pojo.filter.DaoFilter;
import cn.antido.admin.service.SpaceService;
import cn.antido.admin.service.sender.MsgSender;
import cn.antido.common.pojo.PageBean;
import cn.antido.common.utils.FormatUtils;
import cn.antido.common.utils.PageBeanUtils;
import cn.antido.common.utils.PriceUtils;
import cn.antido.common.utils.TimeUtils;
/**
 * @Description 车位服务实现类
 * @author Antido
 * @date 2017年12月19日 下午5:06:48
 */
@Service
@Transactional
public class SpaceServiceImpl implements SpaceService {
	
	@Autowired
	private SpaceMapper spaceMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private NodeMapper nodeMapper;
	
	@Autowired
	private CarMapper carMapper;
	
	@Autowired
	private NodeAddressMapper nodeAddressMapper;
	
	@Autowired
	private ParkMapper parkMapper;
	
	/**
	 * 织入消息发送者
	 */
	@Autowired
	private MsgSender msgSender;
	
	//Spring提供的JMS工具类 ，用来发送和接收消息
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private Destination addRemainQueueDestination; //增加剩余车辆消息
	
	@Autowired
	private Destination subRemainQueueDestination; //减少剩余车辆消息
	
	/**
	 * 根据停车场id获取所用车位对象
	 */
	@Override
	@Transactional(readOnly=true)
	public PageBean<Space> getSpacePageBeanByParkId(Integer PAGE_SIZE, Integer currentPage, Long parkId) {
		//设置分页条件
		PageHelper.startPage(currentPage, PAGE_SIZE);
		List<Space> list = spaceMapper.selectByParkId(parkId);
		//获取pageBean
		PageBean<Space> pageBean = PageBeanUtils.getPageBean(list, currentPage);
		return pageBean;
	}
	
	
	/**
	 * 获取space对象
	 */
	@Override
	@Transactional(readOnly=true)
	public Space getSpaceById(Long spaceId) {
		Space Space = spaceMapper.selectByPrimaryKey(spaceId);
		return Space;
	}
	
	/**
	 * 新增一个车位
	 */
	@Override
	public Space addSpace(Space space) {
		Long maxId = spaceMapper.selectMaxId(space.getPark().getId());
		if(maxId == null) { //为首次添加
			//生成新ID
			String parkIdStr = space.getPark().getId() + "";
			parkIdStr = parkIdStr + "0001";
			Long id = Long.parseLong(parkIdStr);
			space.setId(id);
		} else {
			if(maxId.equals(9999)) {
				//TODO: 处理ID溢出问题
			} else {
				//id自增1
				space.setId(maxId + 1);
			}
			
		}
		
		//补全Node
		if(space.getNode() != null && !"".equals(space.getNode().getId())) {
			Node node = nodeMapper.selectByPrimaryKey(space.getNode().getId());
			if(node != null) {
				space.setNode(node);
				//为当前节点绑定车位
				node.setSpace_id(space.getId());
				node.setUpdated(new Date());
				nodeMapper.updateByNode(node);
				
				//是一个绑定了节点的车位 将停车场的working_count字段加一
				parkMapper.addWorkingCount(space.getPark().getId());
			}
		} else {
			space.setNode(null);
		}
		
		space.setCreated(new Date());
		space.setUpdated(new Date());
		
		Integer insertSpace = spaceMapper.insertSpace(space);
		
		if(insertSpace.equals(1)) {
			return space;
		} else {
			return null;
		}
	}

	/**
	 * 根据页面条件查询车位列表分页对象
	 */
	@Override
	@Transactional(readOnly=true)
	public PageBean<Space> getPageBeanBySpaceDTO(Integer PAGE_SIZE, Integer currentPage, SpaceDTO spaceDTO) {
		//设置分页查询对象
		PageHelper.startPage(currentPage, PAGE_SIZE);
		//遍历space对象可能会使用的过滤条件拼接查询过滤器
		DaoFilter filter = new DaoFilter();
		
		Park park = spaceDTO.getPark();
		if(park != null && park.getId() != null && park.getId() != 0) {
			filter.addAndCriteria("park_id = ", park.getId());
		} else { //
			return null;
		}
		
		Byte space_type = spaceDTO.getSpace_type();
		if(space_type != null && space_type >= 0) {
			filter.addAndCriteria("space_type = ", space_type);
		} 
		
		Byte using_state = spaceDTO.getUsing_state();
		if(using_state != null && using_state >= 0) {
			filter.addAndCriteria("using_state = ", using_state);
		}
		
		Byte running_state = spaceDTO.getRunning_state();
		if(running_state != null && running_state >= 0) {
			filter.addAndCriteria("running_state= ", running_state);
		}
		
		//时间排序三个应该只有一个生效
		Byte leaving_time = spaceDTO.getLeaving_time();
		if(leaving_time != null && leaving_time >= 0) {
			if(leaving_time == 0) {
				filter.setOrderBy("leaving_time");
			} else {
				filter.setOrderBy("leaving_time desc");
			}
		}
		
		Byte reserve_time = spaceDTO.getReserve_time();
		if(reserve_time != null && reserve_time >= 0) {
			if(reserve_time == 0) {
				filter.setOrderBy("reserve_time");
			} else {
				filter.setOrderBy("reserve_time desc");
			}
		}
		
		Byte parked_time = spaceDTO.getParked_time();
		if(parked_time != null && parked_time >= 0) {
			if(parked_time == 0) {
				filter.setOrderBy("parked_time");
			} else {
				filter.setOrderBy("parked_time desc");
			}
		}
		//获取查询结果
		List<Space> list = spaceMapper.selectByFilter(filter);
		//封装PageBean
		PageBean<Space> pageBean = PageBeanUtils.getPageBean(list, currentPage);
		
		return pageBean;
	}

	/**
	 * 封装SpaceModelDTO对象
	 */
	@Override
	@Transactional(readOnly=true)
	public SpaceModelDTO getSpaceModelDTO(Long spaceId) {
		SpaceModelDTO spaceModelDTO = new SpaceModelDTO();
		
		Space space = spaceMapper.selectByPrimaryKey(spaceId);
		if(space == null) {
			return null;
		}
		
		//User user = space.getUser();
		
		User user = new User();
		user.setId(space.getUser_id());
		String orderId = space.getOrder_id();
		if(user != null && orderId != null) { //当前车位被使用
			//获取补全space内引用的对象
			Order orderFull = orderMapper.selectByPrimaryKey(orderId);
			
			//计算当前时刻订单小计
			Date created = orderFull.getCreated();
			Integer price = orderFull.getPrice();
			Integer free_time = orderFull.getFree_time();
			String currentPrice = PriceUtils.currentSubtotalStr(created, price, free_time);
			spaceModelDTO.setCurrentPrice(currentPrice);
			
			User userFull = userMapper.selectSimpleByPrimaryKey(user.getId()); //获取简单用户对象
			Car car = carMapper.selectByPrimaryKey(userFull.getCar().getId());
			
			userFull.setCar(car);
			//space.setUser(userFull);
			space.setUser_id(userFull.getId());
			space.setOrder_id(orderFull.getId());
			
			spaceModelDTO.setUser(userFull);
			spaceModelDTO.setOrder(orderFull);
			
			Date leaving_time = space.getLeaving_time();
			Date parked_time = space.getParked_time();
			
			//计算停靠时间
			long currentTime = System.currentTimeMillis();
			long minusTime = currentTime - parked_time.getTime();
			String minusTimeStr = TimeUtils.timeCalculate(minusTime);
			
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
			
			if(parked_time != null) {
				String parkedTimeStr = dateTimeFormat.format(parked_time);
				spaceModelDTO.setParkedStr(parkedTimeStr);
			}
			if(leaving_time != null) {
				String leavingTimeStr = dateTimeFormat.format(leaving_time);
				spaceModelDTO.setLeavingStr(leavingTimeStr);
			}
			
			spaceModelDTO.setParkedTimeStr(minusTimeStr);
		}
		
		//补全节点对象
		Node node = space.getNode();
		if(node != null) {
			Node nodeFull = nodeMapper.selectByPrimaryKey(node.getId());
			space.setNode(nodeFull);
		}
		
		Date created = space.getCreated();
		Date updated = space.getUpdated();

		//转换字符串时间信息
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		String createdStr = dateFormat.format(created);
		String updatedStr = dateFormat.format(updated);

		//封装字符串时间信息
		spaceModelDTO.setCreatedStr(createdStr);
		spaceModelDTO.setUpdatedStr(updatedStr);
	
		spaceModelDTO.setSpace(space);
		
		return spaceModelDTO;
	}

	
	/**
	 * 根据parkId获取车位列表
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Space> getSpaceListByParkID(Long parkId) {
		List<Space> list = spaceMapper.selectByParkId(parkId);
		//封装node数据
		for (Space space : list) {
			if(space.getNode() != null) {
				String nodeId = space.getNode().getId();
				if(nodeId != null) {
					Node node = nodeMapper.selectByPrimaryKey(nodeId);
					space.setNode(node);
				}
			}
		}
		return list;
	}

	/**
	 * 删除一个车位
	 */
	@Override
	public Integer deleteSpace(Long spaceId) {
		Space space = spaceMapper.selectByPrimaryKey(spaceId);
		if(space == null) {
			return null;
		}
		//解除节点绑定关系
		if(space.getNode() != null && !"".equals(space.getNode().getId())) {
			nodeMapper.removeSpaceId(space.getNode().getId());
			//更新停车场车位数量信息
			parkMapper.subWorkingCount(space.getPark().getId());
		}
		
		return spaceMapper.deleteSpaceById(spaceId);
	}

	/**
	 * 更新一个车位信息
	 */
	@Override
	public Space updateSpace(Space space) {
		//之前的停车场
		Space oldSpace = spaceMapper.selectByPrimaryKey(space.getId());
		if(oldSpace == null) {
			return null;
		}
		space.setUpdated(new Date());
		
		String nodeId = null;
		String oldNodeId = null;
		//处理节点关系
		if(space.getNode() != null && space.getNode().getId() != null) {
			if("".equals(space.getNode().getId())) {
				space.setNode(null);
			} else {
				nodeId = space.getNode().getId();
			}
		}
		if(oldSpace.getNode() != null && oldSpace.getNode().getId() != null) {
			oldNodeId = oldSpace.getNode().getId();
		}
		
		if(nodeId != null && oldNodeId == null) { //之前没有现在有了 
			//在停车场新增一个可用的车位
			parkMapper.addWorkingCount(space.getPark().getId());
		} 
		
		if(oldNodeId != null && nodeId == null) {//之前有现在没有了
			//在停车场减少一个车位
			parkMapper.subWorkingCount(space.getPark().getId());
		}
		
		//当节点信息未发生变化时直接更新space
		//当节点信息发生变化时更新node表中的space_id
		if(nodeId == null) { //新space现在没有绑定Node
			if(oldNodeId != null) { //之前有 现在没有
				nodeMapper.removeSpaceId(oldNodeId); //解除space与node的绑定关系
			} 
			spaceMapper.updateBySpace(space);
		} else { //现在有id
			//把需要更新的节点信息取出
			Node currentNode = nodeMapper.selectByPrimaryKey(nodeId);
			space.setNode(currentNode);
			
			if(!nodeId.equals(oldNodeId)) { //之前和现在不一样
				Node node = space.getNode();
				node.setUpdated(new Date());
				node.setSpace_id(space.getId());
				nodeMapper.updateByNode(node);
			} 
			spaceMapper.updateBySpace(space);
		}
		
		return space;
	}


	/**
	 * 对一个车位进行上锁操作<br>
	 * 向控制端发送上锁指令<br>
	 * 当指令正确执行后,改变车位的状态信息,改变停车场状态信息,生成对应订单信息,更新索引库,更新缓存<br>
	 * 
	 */
	@Override
	public ConnCallBack lockOnSpace(Long spaceId, String nodeId, String parent_id) {
		//通过父ID获取控制端地址
		NodeAddress nodeAddress = nodeAddressMapper.selectById(parent_id);
		if(nodeAddress == null) return ConnCallBack.EXCEPTION("未找到对应地址");
		
		Space space = spaceMapper.selectByPrimaryKey(spaceId);
		if(space == null) return ConnCallBack.EXCEPTION("车位信息异常");
		
		//向控制端发送上锁指令
		ConnCallBack lockOn = msgSender.lockOn(nodeAddress.getAddress(), nodeId);
		if(lockOn.getCode() != 0) //状态不正常,直接返回
			return lockOn;
		
		//节点锁状态已改变,修改车位数据
		space.setUsing_state(Space.USING_STATE_USED);
		space.setParked_time(new Date());
		space.setUpdated(new Date());
		//TODO: 与用户绑定
		spaceMapper.updateBySpace(space);
		
		//改变停车场状态
		final Long parkId = space.getPark().getId();
		parkMapper.addUsingCount(parkId); //将正在使用数加1
		
		//TODO: 生成订单
		//更新索引库
		//有一个车位被上锁了,所以要减少索引库中记录的剩余车位数
		jmsTemplate.send(subRemainQueueDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				//发送要修改的停车场id
				TextMessage message = session.createTextMessage(parkId+"");
				return message;
			}
		});
		
		//TODO:缓存
		
		return ConnCallBack.OK();
	}


	/**
	 * 对一个车位进行解锁操作<br>
	 * 向控制端发送解锁指令<br>
	 * 当指令正确执行后,改变车位的状态信息,改变停车场状态信息,生成对应订单信息,更新索引库,更新缓存<br>
	 * 
	 */
	@Override
	public ConnCallBack lockOffSpace(Long spaceId, String nodeId, String parent_id) {
		//通过父ID获取控制端地址
		NodeAddress nodeAddress = nodeAddressMapper.selectById(parent_id);
		if(nodeAddress == null) return ConnCallBack.EXCEPTION("未找到对应地址");
		
		Space space = spaceMapper.selectByPrimaryKey(spaceId);
		if(space == null) return ConnCallBack.EXCEPTION("车位信息异常");
		
		//向控制端发送上锁指令
		ConnCallBack lockOn = msgSender.lockOff(nodeAddress.getAddress(), nodeId);
		if(lockOn.getCode() != 0) //状态不正常,直接返回
			return lockOn;
		
		//节点锁状态已改变,修改车位数据
		space.setUsing_state(Space.USING_STATE_FREE);
		space.setLeaving_time(new Date());
		space.setUpdated(new Date());
		//TODO: 与用户绑定
		spaceMapper.updateBySpace(space);
		
		//改变停车场状态
		final Long parkId = space.getPark().getId();
		parkMapper.subUsingCount(parkId); //将正在使用数减1
		
		//TODO: 生成订单
		//更新索引库
		//有一个车位被解锁了,所以要增加索引库中记录的剩余车位数
		jmsTemplate.send(addRemainQueueDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				//发送要修改的停车场id
				TextMessage message = session.createTextMessage(parkId+"");
				return message;
			}
		});
		
		//TODO:缓存
		return ConnCallBack.OK();
	}


	/**
	 * 根据车位ID获取车位信息,用于前端客户查看使用<br>
	 * 当车位被使用时将包含用户数据,用户数据具体封装情况由原用户决定
	 * @param spaceId
	 * @return
	 */
	@Override
	public SpaceInfoDTO getSpaceInfoDTO(Long spaceId) {
		Space space = spaceMapper.selectByPrimaryKey(spaceId);
		if(space == null) {
			return null;
		}
		
		//补全node对象
		String nodeId = space.getNode().getId();
		Node node = nodeMapper.selectByPrimaryKey(nodeId);
		space.setNode(node);
		
		SpaceInfoDTO dto = new SpaceInfoDTO();
		dto.setSpace(space);
		
		String userId = space.getUser_id();
		if(userId == null) {
			return dto;
		}
		
		User user = userMapper.selectSimpleByPrimaryKey(userId);
		if(user == null) {
			return dto;
		}
		
		//补全Car对象
		if(user.getCar() != null) {
			Car car = carMapper.selectByPrimaryKey(user.getCar().getId());
			user.setCar(car);
			if(!user.getShow_license()) {
				car.setLicense(null);
			}
		}
		
		//根据用户意愿封装回显对象
		if(!user.getShow_name()) {
			user.setName(null);
			user.setNick_name(null);
		}
		
		if(!user.getShow_phone()) user.setPhone(null);
		dto.setUser(user);
		
		
		if(space.getLeaving_time() != null) {
			String leavingTime = FormatUtils.dateTimeFormat(space.getLeaving_time());
			dto.setLeavingTimeStr(leavingTime);
		}
		
		return dto;
	}

}






