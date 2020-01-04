package cn.antido.admin;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.antido.admin.mapper.ParkMapper;
import cn.antido.admin.pojo.Park;

/**
 * @Description 测试数据库分页查询
 * @author Antido
 * @date 2017年12月17日 下午2:37:28
 */
public class TestPageHelper {
	@Test
	public void testPageHelper() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		ParkMapper parkMapper = ac.getBean(ParkMapper.class);
		
		PageHelper.startPage(1, 5);
		List<Park> list = parkMapper.selectAll();
		PageInfo<Park> pageInfo = new PageInfo<>(list);
		
		System.out.println(pageInfo.getPageSize());
		System.out.println(pageInfo.getTotal());
		System.out.println(list.get(0));
	}
}
