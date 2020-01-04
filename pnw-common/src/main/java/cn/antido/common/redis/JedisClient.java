package cn.antido.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Description redis操作工具类<br>
 * 由spring管理,封装的常用操作方法<br>
 * @author Antido
 * @date 2018年3月26日 下午8:15:01
 */
public class JedisClient {
	
	/**
	 * 由spring建立的jedisPool
	 */
	private JedisPool jedisPool;
	
	public JedisClient(JedisPool jedisPool) {
		//System.out.println(jedisPool);
		this.jedisPool = jedisPool;
	}
	
	/**
	 * 获取一个jedis操作对象
	 */
	public Jedis getJedis() {
		return jedisPool.getResource();
	}
	
	/**
	 * 放置一个字符串 键值对
	 */
	public String set(String key, String value) {
		Jedis jedis = getJedis();
		String result = jedis.set(key, value);
		jedis.close();
		return result;
	}

	/**
	 * 获取字符串key对应的值
	 */
	public String get(String key) {
		Jedis jedis = getJedis();
		String result = jedis.get(key);
		jedis.close();
		return result;
	}

	/**
	 * 是否存在key 
	 */
	public Boolean exists(String key) {
		Jedis jedis = getJedis();
		Boolean result = jedis.exists(key);
		jedis.close();
		return result;
	}
	
	/**
	 * 向有序集合插入一条数据
	 */
	public Long zadd(String key, Long score, String member) {
		Jedis jedis = getJedis();
		Long result = jedis.zadd(key, score, member);
		jedis.close();
		return result;
	}
	
	/**
	 * 批量向有序集合中插入数据
	 */
	public Long zadd(String key, Map<String,Double> scoreMembers) {
		Jedis jedis = getJedis();
		Long result = jedis.zadd(key, scoreMembers);
		jedis.close();
		return result;
	}
	
	/**
	 * 根据排名范围获取有序集合中的数据
	 */
	public Set<String> zrange(String key, Long start, Long end) {
		Jedis jedis = getJedis();
		Set<String> resultSet = jedis.zrange(key, start, end);
		jedis.close();
		return resultSet;
	}
	
	/**
	 * 移除有序集合中的成员,如果成员不存在return 0
	 */
	public Long zrem(String key, String member) {
		Jedis jedis = getJedis();
		Long result = jedis.zrem(key, member);
		jedis.close();
		return result;
		
	}
	
	/**
	 * 根据分数范围获取成员
	 */
	public Set<String> zrangeByScore(String key, Long start, Long end) {
		Jedis jedis = getJedis();
		Set<String> result = jedis.zrangeByScore(key, start, end);
		jedis.close();
		return result;
	}
	
	/**
	 * 将hash表key 中的field的值设置为value<br>
	 * 如果hash表不存在将创建<br>
	 * 如果field存在则更新值
	 */
	public Long hset(String key, String field, String value) {
		Jedis jedis = getJedis();
		Long hset = jedis.hset(key, field, value);
		jedis.close();
		return hset;
	}
	
	/**
	 * 从hash表key中 获取指定field的值<br>
	 * 如果field 不存在返回null
	 */
	public String hget(String key, String field) {
		Jedis jedis = getJedis();
		String hget = jedis.hget(key, field);
		jedis.close();
		return hget;
	}
	
	/**
	 * 返回hash表key中所有field的值
	 * @param key
	 */
	public List<String> HVALS(String key) {
		Jedis jedis = getJedis();
		List<String> hvals = jedis.hvals(key);
		jedis.close();
		return hvals;
	}
	
	/**
	 * 删除hash表key中的指定field<br>
	 * 如果field不存在 则忽略 
	 */
	public Long hdel(String key, String... fields) {
		Jedis jedis = getJedis();
		Long hdel = jedis.hdel(key, fields);
		jedis.close();
		return hdel;
	}
	
	/**
	 * hash表key中是否存在指定的field
	 */
	public Boolean hexists(String key, String field) {
		Jedis jedis = getJedis();
		Boolean result = jedis.hexists(key, field);
		jedis.close();
		return result;
	}
	
	/**
	 * 返回hash表key 中field的数量
	 */
	public Long hlen(String key) {
		Jedis jedis = getJedis();
		Long hlen = jedis.hlen(key);
		jedis.close();
		return hlen;
	}
	
	/**
	 * 给指定的key设置过期时间
	 */
	public Long expire(String key, int seconds) {
		Jedis jedis = getJedis();
		Long expire = jedis.expire(key, seconds);
		jedis.close();
		return expire;
	}
	
	/**
	 * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
	 * @param key
	 * @param members
	 * @return
	 */
	public Long sadd(String key, String... members) {
		Jedis jedis = getJedis();
		Long sadd = jedis.sadd(key, members);
		jedis.close();
		return sadd;
	}
	
	/**
	 * 判断 member 元素是否集合 key 的成员。
	 * @param key
	 * @param member
	 * @return
	 */
	public Boolean smember(String key, String member) {
		Jedis jedis = getJedis();
		Boolean isMember = jedis.sismember(key, member);
		jedis.close();
		return isMember;
	}
	
	/**
	 * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。<br>
	 * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
	 * @param key
	 * @param members
	 * @return
	 */
	public Long srem(String key, String... members) {
		Jedis jedis = getJedis();
		Long srem = jedis.srem(key, members);
		jedis.close();
		return srem;
	}
	
}
