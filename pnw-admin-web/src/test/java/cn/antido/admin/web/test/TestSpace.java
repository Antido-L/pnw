package cn.antido.admin.web.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpace {
	@Test
	public void TestPage() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/springmvc.xml");
		System.out.println(applicationContext);
	}
}
