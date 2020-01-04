package cn.antido.admin;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.antido.admin.mapper.SpaceMapper;
import cn.antido.admin.pojo.Space;
import cn.antido.admin.pojo.dto.SpaceDTO;
import cn.antido.admin.pojo.filter.DaoFilter;

public class TestDaoFilter {

	@Test
	public void testAnd() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		SpaceMapper mapper = ac.getBean(SpaceMapper.class);
		DaoFilter filter = new DaoFilter();
		filter.addAndCriteria("space_type = ", (byte)1);
		filter.addAndCriteria("running_state = ", (byte)2);
		filter.addAndCriteria("using_state in", new Byte[]{1,2,3,4});
		List<Space> list = mapper.selectByFilter(filter);
		for (Space space : list) {
			System.out.println(space.getId());
		}
	}
	
	@Test
	public void testOR() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		SpaceMapper mapper = ac.getBean(SpaceMapper.class);
		DaoFilter filter = new DaoFilter();
		filter.addOrCriteria("running_state = ", (byte)1);
		filter.setOrderBy("id desc");
		List<Space> list = mapper.selectByFilter(filter);
		for (Space space : list) {
			System.out.println(space.getId());
		}
	}
	
	@Test
	public void testByte() {
		SpaceDTO spaceQuery = new SpaceDTO();
		System.out.println(spaceQuery.getReserve_time() == null);
		System.out.println(spaceQuery.getPark() == null);
	}
	
}
