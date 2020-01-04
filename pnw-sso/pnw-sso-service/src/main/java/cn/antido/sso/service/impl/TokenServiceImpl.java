package cn.antido.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.antido.admin.pojo.User;
import cn.antido.common.CallBackResult;
import cn.antido.common.redis.JedisClient;
import cn.antido.common.utils.JsonUtils;
import cn.antido.sso.service.TokenService;

/**
 * @Description token服务实体类
 * @author Antido
 * @date 2018年6月6日 下午6:23:11
 */
@Service
public class TokenServiceImpl implements TokenService{
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${redis.session.SESSION_NAME}")
	private String SESSION_NAME;
	
	@Value("${redis.session.SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	

	/**
	 * 根据token获取结果集<br>
	 * 查询redis如果token是有效的 将对应的User对象放入CallBackResult中,并更新对应token的过期时间
	 * @param token
	 */
	@Override
	public CallBackResult getUserByToken(String token) {
		//根据token到redis中取用户信息
		String json = jedisClient.get(SESSION_NAME + token);
		//取不到用户信息，登录已经过期，返回登录过期
		if (json == null || "".equals(json)) {
			return CallBackResult.exception("登录已经过期");
		}
		//取到用户信息更新token的过期时间
		jedisClient.expire(SESSION_NAME + token, SESSION_EXPIRE);
		
		//返回结果，CallBackResult其中包含User对象
		User user = JsonUtils.json2Obj(json, User.class);
		
		return CallBackResult.build(CallBackResult.CODE_OK, token, user);
	}

}
