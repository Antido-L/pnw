package cn.antido.sso.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

import cn.antido.admin.pojo.Car;
import cn.antido.admin.pojo.CarBrand;
import cn.antido.admin.pojo.CarModel;
import cn.antido.admin.pojo.SMSItem;
import cn.antido.admin.pojo.User;
import cn.antido.admin.pojo.dto.CarAddDTO;
import cn.antido.common.CallBackResult;
import cn.antido.common.redis.JedisClient;
import cn.antido.common.utils.JsonUtils;
import cn.antido.common.utils.UUIDUtils;
import cn.antido.sso.mapper.CarBrandMapper;
import cn.antido.sso.mapper.CarMapper;
import cn.antido.sso.mapper.CarModelMapper;
import cn.antido.sso.mapper.SMSMapper;
import cn.antido.sso.mapper.UserMapper;
import cn.antido.sso.service.RegisterService;
import cn.antido.sso.service.impl.SMS.SMSTools;

/**
 * @Description 注册服务实体类实现
 * @author Antido
 * @date 2018年6月5日 下午3:22:04
 */
@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {
	
	@Value("${redis.regis.code.name}")
	private String REGIS_CODE_NAME;
	
	@Value("${redis.regis.code.expire}")
	private Integer REGIS_CODE_EXPIRE;
	
	@Value("${redis.session.SESSION_NAME}")
	private String SESSION_NAME;
	
	@Value("${redis.session.SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private SMSMapper smsMapper;
	
	@Autowired
	private CarBrandMapper carBrandMapper;
	
	@Autowired
	private CarModelMapper carModelMapper;
	
	@Autowired
	private CarMapper carMapper;
	
	@Autowired
	private SMSTools smsTools;
	
	@Autowired
	private JedisClient jedisClient;
	
	/**
	 * 判断当前用户名是否已经被使用
	 */
	@Override
	@Transactional(readOnly = true)
	public Boolean isNameExist(String name) {
		Boolean isExist = userMapper.isNickNameExist(name);
		return isExist;
	}

	/**
	 * 判断当前手机号是否已经被使用
	 */
	@Override
	@Transactional(readOnly = true)
	public Boolean isPhoneExist(String phone) {
		Boolean isExist = userMapper.isPhoneExist(phone);
		return isExist;
	}

	/**
	 * 向目标手机号发送注册验证码<br>
	 * 在发送过程中验证上次发送的时间,当在一分钟内已经向目标发送过验证码返回exception(重发倒计时)<br>
	 * 当发送成功后返回OK()并在redis中保存当前手机注册码设置过期时间5分钟<br>
	 * @param phone
	 * @return CallBackResult
	 */
	@Override
	public CallBackResult sendRegisMsg(String phone) {
		long currTime = System.currentTimeMillis();
		
		String old = jedisClient.get(REGIS_CODE_NAME + phone);
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
		//存放格式REGIS-138xxxxxxxx:code-currTime
		
		//发送消息
		try {
			SendSmsResponse response = smsTools.sendRegisMsg(phone,code);
			
			//保存记录
			SMSItem item = new SMSItem();
			item.setBiz_id(response.getBizId());
			item.setPhone(phone);
			item.setType(SMSItem.TYEP_REGIS);
			item.setTime(new Date());
			smsMapper.insertBySMSItem(item);
			
			//短信发送失败返回错误码
			if(response.getCode() == null || !"OK".equals(response.getCode())) {
				return CallBackResult.error("短信发送响应错误码:" + response.getCode());
			}
		} catch (ClientException e) {
			return CallBackResult.error("短信发送异常:"+e.toString());
		}
		
		jedisClient.set(REGIS_CODE_NAME + phone, code + "-" + currTime);
		jedisClient.expire(REGIS_CODE_NAME + phone, REGIS_CODE_EXPIRE);
		
		return CallBackResult.ok();
	}

	/**
	 * 新增一个user<br>
	 * 验证手机验证码的正确性<br>
	 * 当用户注册成功后,生成对应的session<br>
	 * @param user 表单封装的user对象
	 * @param code 手机验证码
	 * @return CallBackResult
	 */
	@Override
	public CallBackResult addUser(User user, String code) {
		//验证手机验证码
		String redisCode = jedisClient.get(REGIS_CODE_NAME + user.getPhone());
		
		if(redisCode == null || !redisCode.startsWith(code)) {
			return CallBackResult.exception("验证码不正确!");
		}
		
		//补全user表中需要的字段
		String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(password);
		user.setCreated(new Date());
		user.setUpdated(new Date());
		user.setId(UUIDUtils.getUUID32());
		user.setState(User.STATE_NORMAL);
		
		//存入DB
		userMapper.insertByUser(user);
		
		user.setPassword(null);
		user.setCreated(null);
		user.setUpdated(null);
		//生成
		String token = UUIDUtils.getUUID32();
		String json = JsonUtils.obj2Json(user);
		
		//将User存入redis中
		jedisClient.set(SESSION_NAME + token, json);
		//设置过期时间
		jedisClient.expire(SESSION_NAME + token, SESSION_EXPIRE);
		
		//验证成功后将redis中保存的注册码取消
		jedisClient.expire(REGIS_CODE_NAME + user.getPhone(), 0);
		
		//将随机生成的token返回给web层用于设置cookie
		return CallBackResult.ok(token);
	}

	/**
	 * 获取所有车辆品牌列表
	 */
	@Override
	@Transactional(readOnly=true)
	public List<CarBrand> getBrands() {
		List<CarBrand> list = carBrandMapper.selectAll();
		return list;
	}

	/**
	 * 获取所有目标品牌的车型
	 * @param brandId
	 * @return
	 */
	@Override
	@Transactional(readOnly=true)
	public List<CarModel> getModelByBrand(Integer brandId) {
		List<CarModel> list = carModelMapper.selectAllByBrand(brandId);
		return list;
	}

	/**
	 * 为用户绑定车位等操作
	 * @param dto
	 * @return
	 */
	@Override
	public CallBackResult addCar(CarAddDTO dto) {
		//获取User
		String userStr = jedisClient.get(SESSION_NAME + dto.getToken());
		if(userStr == null || userStr.equals("")) {
			return CallBackResult.exception("用户未注册或已过期");
		}
		User user = JsonUtils.json2Obj(userStr, User.class);
		
		//新建车辆
		Car car = new Car();
		
		String color = dto.getColor();
		car.setColor(color);
		
		String carName = dto.getBrand() + dto.getModel(); 
		car.setName(carName);
		
		//根据用户需求设置公开的描述
		Byte showType = dto.getShowType();
		if(showType == CarAddDTO.SHOW_TYEP_NOT){
			// do nothing
		} else if(showType == CarAddDTO.SHOW_TYEP_SIMPLE) {
			car.setCar_desc(color + dto.getCarType());
		} else if(showType == CarAddDTO.SHOW_TYEP_NORMAL) {
			car.setCar_desc(color + dto.getBrand());
		} else if(showType == CarAddDTO.SHOW_TYEP_COMPLETE) {
			car.setCar_desc(carName + "-" + color);
		}
		
		car.setCar_type(dto.getCarType());
		car.setLicense(dto.getLicense());
		car.setCreated(new Date());
		car.setId(UUIDUtils.getUUID32());
		
		//DB
		carMapper.insertByCar(car);
		
		//更新用户
		user.setShow_license(dto.getIsShowLicense());
		user.setShow_name(dto.getIsShowName());
		user.setShow_phone(dto.getIsShowPhone());
		
		user.setOpCode(dto.getOpCode());
		//先给个1000块
		user.setWallet(100000);
		user.setCar(car);
		user.setUpdated(new Date());
		
		//DB
		userMapper.updateByUser(user);
		userMapper.updateWallet(user);
		
		//将User存入redis中
		String json = JsonUtils.obj2Json(user);
		jedisClient.set(SESSION_NAME + dto.getToken(), json);
		//设置过期时间
		jedisClient.expire(SESSION_NAME + dto.getToken(), SESSION_EXPIRE);
		
		return CallBackResult.ok(dto.getToken());
	}

}
