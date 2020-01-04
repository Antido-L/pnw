package cn.antido.admin;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.DigestUtils;

import cn.antido.admin.mapper.CarMapper;
import cn.antido.admin.mapper.NodeMapper;
import cn.antido.admin.mapper.SpaceMapper;
import cn.antido.admin.mapper.UserMapper;
import cn.antido.admin.pojo.Car;
import cn.antido.admin.pojo.Node;
import cn.antido.admin.pojo.Space;
import cn.antido.admin.pojo.User;
import cn.antido.common.utils.UUIDUtils;

public class TextNode {
	//make data
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/applicationContext-dao.xml");
		NodeMapper mapper = applicationContext.getBean(NodeMapper.class);
		
		Node node = new Node();
		node.setId(UUIDUtils.getUUID32());
		node.setIs_online(true);
		node.setNode_desc("发布测试");
		node.setCreated(new Date());
		node.setUpdated(new Date());
		mapper.insertByNode(node);
		
		Node node2 = new Node();
		node2.setId(UUIDUtils.getUUID32());
		node2.setIs_online(true);
		node2.setNode_desc("发布测试");
		node2.setCreated(new Date());
		node2.setUpdated(new Date());
		mapper.insertByNode(node2);
		
		Node node3 = new Node();
		node3.setId(UUIDUtils.getUUID32());
		node3.setIs_online(true);
		node3.setNode_desc("发布测试");
		node3.setCreated(new Date());
		node3.setUpdated(new Date());
		mapper.insertByNode(node3);
		
		Node node4 = new Node();
		node4.setId(UUIDUtils.getUUID32());
		node4.setIs_online(true);
		node4.setNode_desc("发布测试");
		node4.setCreated(new Date());
		node4.setUpdated(new Date());
		mapper.insertByNode(node4);
		
		Node node5 = new Node();
		node5.setId(UUIDUtils.getUUID32());
		node5.setIs_online(true);
		node5.setNode_desc("发布测试");
		node5.setCreated(new Date());
		node5.setUpdated(new Date());
		mapper.insertByNode(node5);
	}
	
	@Test
	public void addNode() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/applicationContext-dao.xml");
		NodeMapper mapper = applicationContext.getBean(NodeMapper.class);
		SpaceMapper spaceMapper = applicationContext.getBean(SpaceMapper.class);
		Node node = new Node();
		node.setId(UUIDUtils.getUUID32());
		node.setCreated(new Date());
		node.setUpdated(new Date());
		mapper.insertByNode(node);
	}
	
	@Test
	public void updateSpace() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/applicationContext-dao.xml");
		NodeMapper mapper = applicationContext.getBean(NodeMapper.class);
		SpaceMapper spaceMapper = applicationContext.getBean(SpaceMapper.class);
		Space space = spaceMapper.selectByPrimaryKey(21010601500010002L);
		Node node = mapper.selectByPrimaryKey("efda0a3c86534e0d886cc71a318affe9");		
		space.setNode(node);
		spaceMapper.updateBySpace(space);
	}
	
	@Test
	public void updateUser() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/applicationContext-dao.xml");
		NodeMapper Nodemapper = applicationContext.getBean(NodeMapper.class);
		SpaceMapper spaceMapper = applicationContext.getBean(SpaceMapper.class);
		UserMapper userMapper = applicationContext.getBean(UserMapper.class);
		CarMapper carMapper = applicationContext.getBean(CarMapper.class);
		
		Car car = new Car();
		String carId = UUIDUtils.getUUID32();
		car.setId(carId);
		car.setLicense("辽A32U11");
		car.setCreated(new Date());
		car.setUpdated(new Date());
		car.setName("捷豹");
		
		carMapper.insertByCar(car);
		
		User user = new User();
		user.setId("194ce5d0b89c47ff6b30bfb491f9dc26");
		user.setEmail("user@sina.com");
		user.setNick_name("农夫山泉");
		user.setPhone("13522013351");
		user.setCreated(new Date());
		user.setUpdated(new Date());
		user.setCar(car);
		user.setPassword(DigestUtils.md5DigestAsHex("123".getBytes()));
		
		userMapper.insertByUser(user);
	}
	
	/*{"space":
		{"id":21010601500010001,"code":"XXY001","space_type":0,"running_state":1,"using_state":1,"direction":0,"reserve_time":null,"parked_time":1514003139000,"leaving_time":null,"created":1514131200000,"updated":1514131200000,"remark":null,
		"park":{"id":2101060150001,"name":null,"position_desc":null,"park_type":null,"is_free":null,"state":null,"east_longitude":null,"north_latitude":null,"price":null,"created":null,"updated":null,"parkAdmin":null,"design_count":null,"working_count":null,"using_count":null,"street":null},
		"node":{"id":"bf9ae3557de84e62b8acf99e8457cdfc","is_online":false,"is_lock":false,"is_close":false,"power":null,"created":1514476800000,"updated":1514476800000,"node_desc":"Zigbee","space":{"id":21010601500010001,"code":null,"space_type":0,"running_state":1,"using_state":0,"direction":0,"reserve_time":null,"parked_time":null,"leaving_time":null,"created":null,"updated":null,"remark":null,"park":null,"node":null,"user":null,"order":null}},
		"user":{"id":"202cb962ac59075b964b07152d234b70","name":null,"password":null,"phone":null,"email":null,"client_type":null,"created":null,"updated":null,"car":null,"nice_name":null},"order":null},
		"createdStr":"2017年12月25日","updatedStr":"2017年12月25日","parkedStr":null,"parkedTimeStr":null,"leavingStr":null,"currentPrice":null}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
