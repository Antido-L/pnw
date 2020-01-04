package cn.antido.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.antido.admin.mapper.ParkAdminMapper;
import cn.antido.admin.mapper.ParkMapper;
import cn.antido.admin.mapper.StreetMapper;
import cn.antido.admin.pojo.ParkAdmin;
import cn.antido.admin.pojo.Street;
import cn.antido.admin.pojo.filter.DaoFilter;

public class TestParkAdmin {
	
	@Test
	public void makeData() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		ParkAdminMapper mapper = applicationContext.getBean(ParkAdminMapper.class);
		ParkAdmin parkAdmin = new ParkAdmin();
		parkAdmin.setName("翟大爷");
		parkAdmin.setPhone("13899966611");
		parkAdmin.setCode("ADMIN");
		parkAdmin.setCreated(new Date());
		parkAdmin.setUpdated(new Date());
		mapper.insertParkAdmin(parkAdmin);
	}
	
	@Test
	public void filterSelect() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		ParkAdminMapper mapper = applicationContext.getBean(ParkAdminMapper.class);
		DaoFilter daoFilter = new DaoFilter();
		Integer arr[] = {1,2};
		List<Integer> l = new ArrayList<>();
		l.add(1);
		l.add(2);
		daoFilter.addAndCriteria("id in ", l);
		List<ParkAdmin> list = mapper.selectByDaoFilter(daoFilter);
		for (ParkAdmin parkAdmin : list) {
			System.out.println(parkAdmin);
		}
	}
	
	@Test
	public void testNPE() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		ParkAdminMapper mapper = applicationContext.getBean(ParkAdminMapper.class);
		StreetMapper streetMapper = applicationContext.getBean(StreetMapper.class);
		//210106013
		Street street = streetMapper.selectByPrimaryKey(210106013);
		System.out.println(street);
		ParkAdmin parkAdmin = street.getParkAdmin();
		System.out.println(parkAdmin);
		System.out.println(parkAdmin.getId());
		//210106015
		Street street1 = streetMapper.selectByPrimaryKey(210106015);
		System.out.println(street1);
		Integer id = street1.getParkAdmin().getId();
		System.out.println(id);
	}
	
	@Test
	public void testDao() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		ParkAdminMapper mapper = applicationContext.getBean(ParkAdminMapper.class);
		ParkAdmin parkAdmin = new ParkAdmin();
		parkAdmin.setId(1);
		parkAdmin.setCode("ADMIN");
		parkAdmin.setName("张国荣");
		parkAdmin.setGender((byte) 0);
		parkAdmin.setCreated(new Date());
		parkAdmin.setUpdated(new Date());
		mapper.insertParkAdmin(parkAdmin);
	}
	
	
	@Test
	public void testDao2() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		ParkAdminMapper mapper = applicationContext.getBean(ParkAdminMapper.class);
		ParkAdmin selectByPrimaryKey = mapper.selectByPrimaryKey(1);
		System.out.println(selectByPrimaryKey);
	}
	
	
}








