package cn.antido.admin;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.druid.pool.DruidDataSource;

import cn.antido.admin.mapper.ParkMapper;
import cn.antido.admin.mapper.StreetMapper;
import cn.antido.admin.pojo.Park;
import cn.antido.admin.pojo.ParkAdmin;
import cn.antido.admin.pojo.Street;

public class TestPark {
	
	@Test
	public void addID() {
		Integer i = 210106015;
		Long l = i * 10000L + 1L;
		System.out.println(l);
	}
	
	@Test
	public void groupBy() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		ParkMapper mapper = applicationContext.getBean(ParkMapper.class);
		List<Integer> list = mapper.selectAdminByStreetId(210106015);
		for (Integer integer : list) {
			
			System.out.println(integer);
		}
	}
	
	@Test
	public void testBaseResultMap() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		ParkMapper mapper = applicationContext.getBean(ParkMapper.class);
		List<Park> selectAll = mapper.selectAll();
		Park selectByPrimaryKey = mapper.selectByPrimaryKey(10000000000002L);
		
		System.out.println(selectAll);
		System.out.println(selectByPrimaryKey);
	}
	
	@Test
	public void testInsert() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		ParkMapper mapper = applicationContext.getBean(ParkMapper.class);
		Park park = new Park();
		ParkAdmin parkAdmin = new ParkAdmin();
		parkAdmin.setId(23132132);
		park.setId(10000000000013L);
		park.setName("测试用停车场7");
		park.setParkAdmin(parkAdmin);
		mapper.insertPark(park);
	}
	
	/**
	 * 插入测试数据
	 */
	@Test
	public void insert() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		ParkMapper mapper = applicationContext.getBean(ParkMapper.class);
		StreetMapper streetMapper = applicationContext.getBean(StreetMapper.class);
		ParkAdmin parkAdmin = new ParkAdmin();
		parkAdmin.setId(3);
		parkAdmin.setName("老王");
		Street street = streetMapper.selectByPrimaryKey(210106015);
		
		for (long i = 1; i < 20L; i++) {
			long id = 2101060150000L;
			Park park = new Park();
			park.setId(id+i);
			park.setParkAdmin(parkAdmin);
			park.setName("翟家街道停车场"+i);
			park.setCreated(new Date());
			park.setStreet(street);
			mapper.insertPark(park);
		}
	}
	
	@Test
	public void selectByDistrict() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		ParkMapper mapper = applicationContext.getBean(ParkMapper.class);
		List<Park> list = null;
		list = mapper.selectAllByProvinceId(210000);
		for (Park park : list) {
			System.out.println(park);
		}
		
	}
	
	@Test
	public void updatePark() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		ParkMapper mapper = applicationContext.getBean(ParkMapper.class);
		DruidDataSource dataSource = (DruidDataSource) applicationContext.getBean("dataSource");
		List<Park> list = mapper.selectAllByStreetId(210106015);
		for (Park park : list) {
			park.setUpdated(new Date());
			mapper.updateByPark(park);
		}
	}
	
	@Test
	public void test1() {
		Integer working_count = 29;
		Integer design_count = 300000;
		Integer using_count = 12;
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		double d =(double) working_count / design_count * 100;
		double d1 = using_count / (double)working_count * 100;
		double d2 = 1 / 3.0;
		String s1 = decimalFormat.format(d);
		String s2 = decimalFormat.format(d1);
		String s3 = decimalFormat.format(d2);
		System.out.println(d);
		System.out.println(d1);
		System.out.println(d2);
	
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);
		/*String workingPercent = decimalFormat.format(1);
		String usingPercent = decimalFormat.format(1);
		System.out.println(workingPercent);
		System.out.println(usingPercent);*/
		
	}
}
