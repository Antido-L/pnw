package cn.antido.admin;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.antido.admin.mapper.StreetMapper;
import cn.antido.admin.pojo.District;
import cn.antido.admin.pojo.Street;

public class TestStreet {
	@Test
	public void TestDao() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		StreetMapper mapper = applicationContext.getBean(StreetMapper.class);
		District district = new District();
		district.setId(210106);
		List<Street> list = mapper.selectByDistrict(district);
		for (Street street : list) {
			System.out.println(street);
		}
		
		
	}
}
