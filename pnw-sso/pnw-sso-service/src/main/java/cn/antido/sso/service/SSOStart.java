package cn.antido.sso.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.antido.sso.service.impl.SMS.SMSTools;

/**
 * @Description 加载spring容器,注册dubbo服务
 * @author Antido
 * @date 2018年9月3日 上午10:06:03
 */
public class SSOStart {
	public static void main(String[] args) {
		System.out.println("Init SSO service...");
		String[] applicationContext = {
				"classpath:resources/spring/applicationContext-dao.xml",
				"classpath:resources/spring/applicationContext-redis.xml",
				"classpath:resources/spring/applicationContext-service.xml",
				"classpath:resources/spring/applicationContext-trans.xml",
		};
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(applicationContext);
		context.start();
		SMSTools bean = context.getBean(SMSTools.class);
		System.out.println(bean);
		System.out.println("SSO service is start!");
		while(true)
			try {
				Thread.sleep(3600000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
}
