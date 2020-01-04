package cn.antido.search.service.test;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MQTest {
	
	@Test
	public void testConsumer() throws IOException {
		//启动spring容器后开始监听
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		System.in.read();
	}
}
