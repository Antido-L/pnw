package cn.antido.admin;

import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.antido.admin.mapper.ParkMapper;
import cn.antido.admin.pojo.Park;
import cn.antido.admin.service.impl.ParkServiceImpl;
import cn.antido.common.utils.UUIDUtils;

/**
 * @Description :测试Dao
 * @author Antido 
 * @date 2017年12月14日  下午7:03:16
 */
public class TestDao {
	public static void main(String[] args) {
		/*Integer i = null;
		System.out.println(i);
		System.out.println(i == null);
		System.out.println(i != null);*/
		
		Integer a = 123123;
		Integer b = 123123;
		System.out.println(a == b);
		System.out.println(a.equals(b));
		System.out.println(a == 123123);
		System.out.println(b == 123123);
	}
	
	/*@Test
	public void testDao() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		ParkMapper mapper = applicationContext.getBean(ParkMapper.class);
		Park park = mapper.selectByPrimaryKey("1");
		System.out.println(park);
		ParkServiceImpl psl = applicationContext.getBean(ParkServiceImpl.class);
		Park parkById = psl.getParkById("1");
		System.out.println(parkById);
	}
	
	@Test
	public void testService() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		ParkServiceImpl psl = applicationContext.getBean(ParkServiceImpl.class);
		Park parkById = psl.getParkById("1");
		System.out.println(parkById);
	}*/
	
	//测试插入
	/*@Test
	public void testInsert() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		ParkMapper mapper = applicationContext.getBean(ParkMapper.class);
		Park park = new Park();
		park.setId(UUIDUtils.getUUID32());
		park.setName("测试停车场2");
		park.setUpdated(new Date());
		park.setCreated(new Date());
		park.setPark_type(Park.TYPE_OUTSIDE);
		mapper.insertPark(park);
	}*/
	
	//测试事务
	/*@Test
	public void testTrans() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		ParkServiceImpl service = applicationContext.getBean(ParkServiceImpl.class);
		Park park = new Park();
		park.setId(UUIDUtils.getUUID32());
		park.setName("测试停车场4");
		park.setUpdated(new Date());
		park.setCreated(new Date());
		park.setPark_type(Park.TYPE_OUTSIDE);
		park.setState(Park.STATE_DEVELOPING);
		service.addPark(park);
	}*/
}
