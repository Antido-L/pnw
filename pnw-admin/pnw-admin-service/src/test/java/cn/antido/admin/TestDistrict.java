package cn.antido.admin;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.antido.admin.mapper.DistrictMapper;
import cn.antido.admin.mapper.ParkAdminMapper;
import cn.antido.admin.pojo.District;

public class TestDistrict {
	
	@Test
	public void testDao() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		DistrictMapper mapper = applicationContext.getBean(DistrictMapper.class);
		District selectByPrimaryKey = mapper.selectByPrimaryKey(110105);
		List<District> selectByParentId = mapper.selectByParentId(110000);
		System.out.println(selectByPrimaryKey.getName());
		System.out.println(selectByParentId.size());
	}
	
}
