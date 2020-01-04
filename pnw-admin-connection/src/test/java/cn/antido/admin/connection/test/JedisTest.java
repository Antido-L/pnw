package cn.antido.admin.connection.test;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

/**
 * @Description JedisTest
 * @author Antido
 * @date 2018年3月26日 下午4:40:16
 */
public class JedisTest {
	public static void main(String[] args) {
		//simple();
		JedisPool jedisPool = new JedisPool("192.168.161.128", 6379);
		Jedis jedis = jedisPool.getResource();
		
		//jedis.zrange("", 0, -1);
		Set<String> zrange = jedis.zrangeByScore("pulse", 0L, -1L);
		/*Set<Tuple> z = jedis.zrangeWithScores("st", 0, -1);
		for (Tuple tuple : z) {
			System.out.println(tuple);
		}*/
	
		System.out.println(zrange.isEmpty());
		for (String string : zrange) {
			System.out.println(string);
		}
		
		
		jedis.close();
		
	}

	private static void simple() {
		Jedis jedis = new Jedis("192.168.161.128", 6379);
		String str = jedis.get("name");
		
		System.out.println(str);
		jedis.close();
	}
}
