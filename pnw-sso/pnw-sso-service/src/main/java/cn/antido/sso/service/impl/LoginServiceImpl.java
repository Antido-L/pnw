package cn.antido.sso.service.impl;


import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

import cn.antido.admin.pojo.Car;
import cn.antido.admin.pojo.Park;
import cn.antido.admin.pojo.SMSItem;
import cn.antido.admin.pojo.Space;
import cn.antido.admin.pojo.User;
import cn.antido.common.CallBackResult;
import cn.antido.common.redis.JedisClient;
import cn.antido.common.utils.JsonUtils;
import cn.antido.common.utils.UUIDUtils;
import cn.antido.sso.mapper.CarMapper;
import cn.antido.sso.mapper.ParkMapper;
import cn.antido.sso.mapper.SMSMapper;
import cn.antido.sso.mapper.SpaceMapper;
import cn.antido.sso.mapper.UserMapper;
import cn.antido.sso.service.LoginService;
import cn.antido.sso.service.impl.SMS.SMSTools;

/**
 * @Description 用户登录接口实现类
 * @author Antido
 * @date 2018年5月31日 上午11:31:18
 */
@Service
public class LoginServiceImpl implements LoginService {
	
	@Value("${redis.session.SESSION_NAME}")
	private String SESSION_NAME;
	
	@Value("${redis.session.SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Value("${redis.login.code.name}")
	private String LOGIN_CODE_NAME;
	
	@Value("${redis.login.code.expire}")
	private Integer LOGIN_CODE_EXPIRE;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private SpaceMapper spaceMapper;
	
	@Autowired
	private CarMapper carMapper;
	
	@Autowired
	private ParkMapper parkMapper;
	
	@Autowired
	private SMSMapper smsMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Autowired
	private SMSTools smsTools;
	
	/**
	 * 验证登录<br>
	 * 验证成功后,将生成的token放入 CallBackResult的msg中
	 */
	@Override
	@Deprecated
	public CallBackResult checkLogin(User user, Byte method) {
		if(user == null) return CallBackResult.error("user == null");
		
		// 将明文密码使用MD5加密
		if(user.getPassword() != null) {
			String md5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
			user.setPassword(md5);
			
		}
		
		User u = null;
		if(method == 0) { //使用手机号和密码进行登录
			u = userMapper.selectByPhone(user);
		} else if(method == 1) { //使用邮箱和密码进行登录
			u = userMapper.selectByEmail(user);
		} else if(method == 2) { //使用手机短信验证码进行登录
			//TODO:短信验证码
		}
		
		if(u == null) {
			return CallBackResult.error("用户名或密码错误");
		}
		
		//补全User中需要关联的其他对象
		if(u.getSpace() != null && u.getSpace().getId() != null) {
			Space space = spaceMapper.selectByPrimaryKey(u.getSpace().getId());
			Park park = parkMapper.selectByPrimaryKey(space.getPark().getId());
			space.setPark(park);
			u.setSpace(space);
		}
		
		if(u.getCar() != null && u.getCar().getId() != null) {
			Car car = carMapper.selectByPrimaryKey(u.getCar().getId());
			u.setCar(car);
		}

		String token = UUIDUtils.getUUID32();
		String json = JsonUtils.obj2Json(u);
		
		//将User存入redis中
		jedisClient.set(SESSION_NAME + token, json);
		//设置过期时间
		jedisClient.expire(SESSION_NAME + token, SESSION_EXPIRE);
		
		//将随机生成的token返回给web层
		return CallBackResult.ok(token);
	}

	/**
	 * 向目标手机号发送登录短信验证码<br>
	 * 在redis中存放接受短信的手机号与验证码,5分钟后过期<br>
	 * 在一分钟内重复发送短信将返回CallBackResult.exception(msg)<br>
	 * 在msg中存放下次发送的等待时间<br>
	 * 存放格式LOGIN-138xxxxxxxx:code-currTime
	 * @param phone
	 * @return
	 */
	@Override
	public CallBackResult sendLoginMsg(String phone) {
		long currTime = System.currentTimeMillis();
		
		String old = jedisClient.get(LOGIN_CODE_NAME + phone);
		if(old != null) { //在5分钟内不是第一次请求向同一号码发送注册码
			String sendTimeStr = old.split("-")[1];
			long sendTime = Long.parseLong(sendTimeStr);
			int remainTime = (int)(currTime - sendTime) / 1000;
			if(remainTime < 60) {//当发送时间未超过1min
				//返回剩余的等待时间,不向目标手机发送新的验证码
				return CallBackResult.exception(remainTime + "");
			}
		}
		
		//生成6位验证码
		String code = (int)((Math.random()*9+1)*100000) + "";
		//在redis中保存已发送的用户验证码
		//存放格式Login-138xxxxxxxx:code-currTime
		jedisClient.set(LOGIN_CODE_NAME + phone, code + "-" + currTime);
		jedisClient.expire(LOGIN_CODE_NAME + phone, LOGIN_CODE_EXPIRE);
		
		//发送消息
		try {
			SendSmsResponse response = smsTools.sendLoginMsg(phone,code);
			
			//保存记录
			SMSItem item = new SMSItem();
			item.setBiz_id(response.getBizId());
			item.setPhone(phone);
			item.setType(SMSItem.TYEP_LOGIN);
			item.setTime(new Date());
			smsMapper.insertBySMSItem(item);
			
			//短信发送失败返回错误码
			if(response.getCode() == null || !"OK".equals(response.getCode())) {
				return CallBackResult.error("短信发送响应错误码:" + response.getCode());
			}
		} catch (ClientException e) {
			return CallBackResult.error("短信发送异常:"+e.toString());
		}
			
		return CallBackResult.ok();
	}

	/**
	 * 根据短信验证码进行登录<br>
	 * 在redis中查找与目标手机号匹配的字段,比对验证码<br>
	 * 登录成功后在注册中心放置用户session,并返回与其匹配的token<br>
	 * 验证失败返回exception
	 */
	@Override
	public CallBackResult checkPhoneLogin(String phone, String code) {
		String value = jedisClient.get(LOGIN_CODE_NAME + phone);
		if(value == null || "".equals(value)) {
			return CallBackResult.exception("手机号与验证码不符");
		}
		
		//比对验证码
		if(!code.equals(value.split("-")[0])) {
			return CallBackResult.exception("手机号与验证码不符");
		}
		
		//验证通过
		User user = userMapper.getUserByPhone(phone);
		if(user == null) {
			return CallBackResult.exception("notExist");
		}
		
		user.setWallet(userMapper.getWallet(user.getId()));
		
		//补全User中需要关联的其他对象
		if(user.getSpace() != null && user.getSpace().getId() != null) {
			Space space = spaceMapper.selectByPrimaryKey(user.getSpace().getId());
			Park park = parkMapper.selectByPrimaryKey(space.getPark().getId());
			space.setPark(park);
			user.setSpace(space);
		}
		
		if(user.getCar() != null && user.getCar().getId() != null) {
			Car car = carMapper.selectByPrimaryKey(user.getCar().getId());
			user.setCar(car);
		}

		String token = UUIDUtils.getUUID32();
		String json = JsonUtils.obj2Json(user);
		
		//将User存入redis中
		jedisClient.set(SESSION_NAME + token, json);
		//设置过期时间
		jedisClient.expire(SESSION_NAME + token, SESSION_EXPIRE);
		
		//将随机生成的token返回给web层
		return CallBackResult.ok(token);
	}

	/**
	 * 只能用用户名 
	 * 登录成功后在注册中心放置用户session,并返回与其匹配的token<br>
	 * 验证失败返回exception
	 */
	@Override
	public CallBackResult checkPasswordLogin(String username, String password) {
		//获取MD5后的密码
		String md5 = DigestUtils.md5DigestAsHex(password.getBytes());
		
		//在数据库中匹配
		User user = userMapper.getUserByNickName(username);
		
		if(user == null) return CallBackResult.exception("用户名或密码不正确");
		
		if(!user.getPassword().equals(md5)) return CallBackResult.exception("用户名或密码不正确");
		
		user.setPassword(null);
		
		user.setWallet(userMapper.getWallet(user.getId()));
		
		//补全User中需要关联的其他对象
		if(user.getSpace() != null && user.getSpace().getId() != null) {
			Space space = spaceMapper.selectByPrimaryKey(user.getSpace().getId());
			Park park = parkMapper.selectByPrimaryKey(space.getPark().getId());
			space.setPark(park);
			user.setSpace(space);
		}
		
		if(user.getCar() != null && user.getCar().getId() != null) {
			Car car = carMapper.selectByPrimaryKey(user.getCar().getId());
			user.setCar(car);
		}

		String token = UUIDUtils.getUUID32();
		String json = JsonUtils.obj2Json(user);
		
		//将User存入redis中
		jedisClient.set(SESSION_NAME + token, json);
		//设置过期时间
		jedisClient.expire(SESSION_NAME + token, SESSION_EXPIRE);
		
		//将随机生成的token返回给web层
		return CallBackResult.ok(token);
			
	}

}
