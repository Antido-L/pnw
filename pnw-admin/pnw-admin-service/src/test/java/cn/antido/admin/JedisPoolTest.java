package cn.antido.admin;

import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.antido.common.redis.JedisClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisPoolTest {
	public static void main(String[] args) {
		/*ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisClient j = (JedisClient) ac.getBean("jedisClient");
		JedisClient j = ac.getBean(JedisClient.class);
		JedisPool jedisPool = ac.getBean(JedisPool.class);
		Jedis jedis = jedisPool.getResource();
		
		//Set<String> z = jedis.zrange("st", 0L, -1L);
		String string = j.get("asda s ");
		
		System.out.println(string);
		*/
		Jedis jedis = new Jedis("192.168.161.128", 6379);
		String s = jedis.get("abc");
		System.out.println(s);
		
	}
}
