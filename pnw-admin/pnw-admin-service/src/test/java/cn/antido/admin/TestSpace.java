package cn.antido.admin;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.antido.admin.mapper.ParkMapper;
import cn.antido.admin.mapper.SpaceMapper;
import cn.antido.admin.pojo.Park;
import cn.antido.admin.pojo.Space;

public class TestSpace {
	@Test
	public void TestInsert() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		SpaceMapper mapper = applicationContext.getBean(SpaceMapper.class);
		ParkMapper parkMapper = applicationContext.getBean(ParkMapper.class);
		//10000000000001
		Park park = parkMapper.selectByPrimaryKey(10000000000001L);
		for (Long i = 100000000000010001L; i < 100000000000010051L; i++) {
			Space space = new Space();
			space.setId(i);
			space.setCode("ABCD0000");
			space.setPark(park);
			space.setCreated(new Date());
			space.setUpdated(new Date());
			mapper.insertSpace(space);
		}
	}
	
	@Test
	public void TestSelect() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		SpaceMapper mapper = applicationContext.getBean(SpaceMapper.class);
		ParkMapper parkMapper = applicationContext.getBean(ParkMapper.class);
		//10000000000001
		List<Space> list = mapper.selectByParkId(10000000000001L);
		System.out.println(list.size());
	}
	
	@Test
	public void makeData() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		SpaceMapper mapper = applicationContext.getBean(SpaceMapper.class);
		List<Space> all = mapper.selectByParkId(10000000000001L);
		for (Space space : all) {
			space.setParked_time(new Date());
			space.setUpdated(new Date());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mapper.updateBySpace(space);
		}
	}
	
	/**
	 * 给翟家街道停车1 添加停车位
	 */
	@Test
	public void makeSpace() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		SpaceMapper mapper = applicationContext.getBean(SpaceMapper.class);
		ParkMapper parkMapper = applicationContext.getBean(ParkMapper.class);
		Park park = parkMapper.selectByPrimaryKey(2101060150001L);
		for (long i = 0L; i < 35L; i++) {
			Space space = new Space();
			space.setId(21010601500010001L+i);
			space.setCreated(new Date());
			space.setUpdated(new Date());
			space.setPark(park);
			mapper.insertSpace(space);
		}
	}
}
