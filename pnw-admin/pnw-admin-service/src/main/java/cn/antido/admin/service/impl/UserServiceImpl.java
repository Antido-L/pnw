package cn.antido.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.antido.admin.mapper.UserMapper;
import cn.antido.admin.pojo.User;
import cn.antido.admin.service.UserService;
import cn.antido.common.CallBackResult;
import cn.antido.common.redis.JedisClient;
import cn.antido.common.utils.JsonUtils;
import cn.antido.common.utils.UUIDUtils;

/**
 * @Description 管理员服务实体类
 * @author Antido
 * @date 2018年8月29日 下午12:10:16
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Value("${redis.session.admin.name}")
	private String SESSION_ADMIN_NAME;
	
	@Value("${redis.session.admin.expire}")
	private Integer SESSION_ADMIN_EXPIRE;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 验证登录
	 */
	@Override
	public CallBackResult checkLogin(User user) {
		// 将明文密码使用MD5加密
		if(user.getPassword() != null) {
			String md5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
			user.setPassword(md5);
		}
		user.setClient_type(User.CLIENT_TYPE_ADMIN);
		User admin = userMapper.getAdminUser(user);
		if(admin == null) return CallBackResult.exception();
		
		String token = UUIDUtils.getUUID32();
		String json = JsonUtils.obj2Json(admin);
		jedisClient.set(SESSION_ADMIN_NAME + token, json);
		jedisClient.expire(SESSION_ADMIN_NAME + token, SESSION_ADMIN_EXPIRE);
		
		return CallBackResult.ok(token);
	}

	@Override
	public User getAdmin(String token) {
		String json = jedisClient.get(SESSION_ADMIN_NAME + token); 
		if(json == null || json.equals("")) return null;
		
		//更新过期时间
		jedisClient.expire(SESSION_ADMIN_NAME + token, SESSION_ADMIN_EXPIRE);
		User user = JsonUtils.json2Obj(json, User.class);
		
		return user;
	}

}
