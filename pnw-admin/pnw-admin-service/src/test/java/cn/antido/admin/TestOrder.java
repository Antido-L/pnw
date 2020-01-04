package cn.antido.admin;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.antido.admin.mapper.CarMapper;
import cn.antido.admin.mapper.NodeMapper;
import cn.antido.admin.mapper.OrderMapper;
import cn.antido.admin.mapper.SpaceMapper;
import cn.antido.admin.mapper.UserMapper;
import cn.antido.admin.pojo.Order;
import cn.antido.admin.pojo.Space;
import cn.antido.admin.pojo.User;
import cn.antido.common.utils.UUIDUtils;

public class TestOrder {
	
	public static void main(String[] args) {
		String s = "辽宁省沈阳市经济技术开发区沈辽西路<span style='color:red'>111</span>号，沈阳工业大学中央校区，东门进入右转<span style='color:red'>信息学院</span>门";
		
		while(Pattern.matches(".*<span style='color:red'>.*", s)) {
			s = s.replaceAll("<span style='color:red'>", "");
		}
		
		while(Pattern.matches(".*</span>.*", s)) {
			s = s.replaceAll("</span>", "");
		}
		
		System.out.println(s);
		
		/*Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(s);
		if(m.find()){
	         System.out.println("Found value: " + m.group(0));
		}*/
		
	}

	@Test
	public void addOrder() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/applicationContext-dao.xml");
		NodeMapper Nodemapper = applicationContext.getBean(NodeMapper.class);
		SpaceMapper spaceMapper = applicationContext.getBean(SpaceMapper.class);
		UserMapper userMapper = applicationContext.getBean(UserMapper.class);
		CarMapper carMapper = applicationContext.getBean(CarMapper.class);
		OrderMapper orderMapper = applicationContext.getBean(OrderMapper.class);
		Order order = new Order();
		order.setId(UUIDUtils.getUUID32());
		order.setFree_time(15*60);
		order.setPrice(6*100);
		
		Space space = new Space();
		space.setId(21010601500010001L);
		order.setSpace(space);
		
		User user = new User();
		user.setId("202cb962ac59075b964b07152d234b70");
		order.setUser(user);
		
		order.setCreated(new Date());
		orderMapper.insertByOrder(order);
		
	}
}
