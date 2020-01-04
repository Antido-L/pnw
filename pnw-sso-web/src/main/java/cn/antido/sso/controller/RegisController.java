package cn.antido.sso.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.antido.admin.pojo.CarBrand;
import cn.antido.admin.pojo.CarModel;
import cn.antido.admin.pojo.User;
import cn.antido.admin.pojo.dto.CarAddDTO;
import cn.antido.common.CallBackResult;
import cn.antido.common.utils.CookieUtils;
import cn.antido.sso.service.RegisterService;

/**
 * 
 * @Description 注册页面控制器
 * @author Antido
 * @date 2018年8月8日 下午8:32:51
 */
@Controller
public class RegisController {
	
	@Value("${session.SESSION_KEY}")
	private String SESSION_KEY;
	
	@Value("${session.SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Autowired
	private RegisterService registerService;
	
	/**
	 * 展示用户注册页面
	 */
	@RequestMapping(value="/user/register")
	public String showDefaultPage() {
		return "register";
	}
	
	/**
	 * 展示车辆绑定页面
	 */
	@RequestMapping(value="/user/setCar")
	public String showSetCar() {
		//判断用户
		return "setcar";
	}
	
	/**
	 * 车辆绑定表单数据提交
	 */
	@RequestMapping(value="/user/addCar")
	@ResponseBody
	public CallBackResult addCar(CarAddDTO dto,
			HttpServletRequest request, HttpServletResponse response) {
		if(
			dto.getIsShowLicense() == null 
			|| dto.getIsShowName() == null 
			|| dto.getIsShowPhone() == null 
			|| dto.getBrand() == null
			|| dto.getCarType() == null
			|| dto.getColor() == null
			|| dto.getLicense() == null
			|| dto.getModel() == null
			|| dto.getOpCode() == null
			|| dto.getToken() == null
			|| dto.getUserId() == null
		) {
			return CallBackResult.exception("数据不全!");
		}
		
		//验证数据合法性
		String re = "([0-9]|[A-Za-z]|[\u4e00-\u9fa5]|_)+";
		boolean isValid = Pattern.matches(re, dto.getBrand());
		if(!isValid) {
			return CallBackResult.exception("包含非法字符！");
		}
		
		isValid = Pattern.matches(re, dto.getModel());
		if(!isValid) {
			return CallBackResult.exception("包含非法字符！");
		}
		
		isValid = Pattern.matches(re, dto.getCarType());
		if(!isValid) {
			return CallBackResult.exception("包含非法字符！");
		}
		
		isValid = Pattern.matches(re, dto.getColor());
		if(!isValid) {
			return CallBackResult.exception("包含非法字符！");
		}
		
		isValid = Pattern.matches(re, dto.getLicense());
		if(!isValid) {
			return CallBackResult.exception("包含非法字符！");
		}
		
		//验证车牌
		String isLicense = "[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}";
		isValid = Pattern.matches(isLicense, dto.getLicense());
		if(!isValid) {
			return CallBackResult.exception("车牌号不合法!");
		}
		
		String isOpCode = "[0-9]{6}";
		isValid = Pattern.matches(isOpCode, dto.getOpCode());
		if(!isValid) {
			return CallBackResult.exception("操作码不合法");
		}
		
		//执行入库操作
		CallBackResult res = registerService.addCar(dto);
		if(res.getCode() == CallBackResult.CODE_OK) {
			//设置cookie
			CookieUtils.setCookie(request, response, SESSION_KEY, res.getMsg(), SESSION_EXPIRE);
		}
		return res;
	}
	
	
	/**
	 * 验证用户名是否合法是否存在<br>
	 * 返回CallBackResult对象<br>
	 * CallBackResult.exception()中包含异常原因
	 */
	@RequestMapping(value="/user/register/vaildName")
	@ResponseBody
	public CallBackResult vaildName(String name) {
		//验证name是否含有非法字符
		String re = "([0-9]|[A-Za-z]|[\u4e00-\u9fa5]|_)+";
		boolean isVaild = Pattern.matches(re, name);
		if(!isVaild){
			return CallBackResult.exception("用户名包含非法字符！");
		}
		
		//验证name是否已经被占用
		Boolean isExist = registerService.isNameExist(name);
		if(isExist) {
			return CallBackResult.exception("用户名已存在！");
		}
		
		return CallBackResult.ok();
	}
	
	
	/**
	 * 验证输入手机号是否已经被使用<br>
	 * 返回CallBackResult对象<br>
	 * CallBackResult.exception()中包含异常原因
	 */
	@RequestMapping(value="/user/register/vaildPhone")
	@ResponseBody
	public CallBackResult vaildPhone(String phone) {
		//验证手机号
		String re = "[1][3,4,5,7,8][0-9]{9}";
		boolean isVaild = Pattern.matches(re, phone);
		if(!isVaild){
			return CallBackResult.exception("手机格式不正确!");
		}
		
		//验证name是否已经被占用
		Boolean isExist = registerService.isPhoneExist(phone);
		if(isExist) {
			return CallBackResult.exception("该手机号已经被注册，可以尝试登陆！");
		}
		
		return CallBackResult.ok();
	}
	
	/**
	 * 向目标手机号发送注册验证码<br>
	 * 在redis中存放当前手机号的验证码<br>
	 * 当手机号在一分钟能重复提交请求时 将不会发送短信 返回CallBackResult.exception(msg)<br>
	 * 在msg中包含短信发送的等待时间<br>
	 * 返回CallBackResult对象<br>
	 */
	@RequestMapping(value="/user/register/sendRegisMsg")
	@ResponseBody
	public CallBackResult sendRegisMsg(String phone) {
		//验证手机号的合法型
		String re = "[1][3,4,5,7,8][0-9]{9}";
		boolean isVaild = Pattern.matches(re, phone);
		if(!isVaild){
			return CallBackResult.error("手机格式不正确!");
		}
		
		//向目标手机号发送注册验证码
		CallBackResult result = registerService.sendRegisMsg(phone);
		return result;
	}
	
	/**
	 * 新增一个user对象<br>
	 * 当用户注册成功向用户浏览器放置cookie<br>
	 * 返回CallBackResult对象<br>
	 * CallBackResult.exception()中包含异常原因
	 */
	@RequestMapping(value="/user/register/addUser")
	@ResponseBody
	public CallBackResult regisUser(User user, String code, 
			HttpServletRequest request, HttpServletResponse response) {
		
		//验证邮箱
		String emailRe = "[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+";
		String email = user.getEmail();
		if(email == null || "".equals(email)) 
			return CallBackResult.error("表单数据不完整");
		boolean isEmail = Pattern.matches(emailRe, email);
		if(!isEmail) return CallBackResult.error("含有非法数据");
			
		//验证电话
		String phoneRe = "[1][3,4,5,7,8][0-9]{9}";
		String phone = user.getPhone();
		if(phone == null || "".equals(phone)) 
			return CallBackResult.error("表单数据不完整");
		boolean isPhone = Pattern.matches(phoneRe, phone);
		if(!isPhone) {
			return CallBackResult.error("表单数据含有非法字段");
		}
		
		String nameRe = "([0-9]|[A-Za-z]|[\u4e00-\u9fa5]|_)+";
		String name = user.getNick_name();
		if(name == null || "".equals(name)) 
			return CallBackResult.error("表单数据不完整");
		if(name.length() > 20 || name.length() < 2) {
			return CallBackResult.error("表单数据不正确");
		}
		boolean isName = Pattern.matches(nameRe, name);
		if(!isName) {
			return CallBackResult.error("表单数据含有非法字段");
		}
		
		if(code == null || code.equals("") || code.length() != 6)
			return CallBackResult.error("表单数据不完整");
		
		String password = user.getPassword();
		if(password == null || password.length() < 6 || password.length() > 20) {
			return CallBackResult.error("表单数据不正确");
		}
		
		CallBackResult res = registerService.addUser(user,code);
		//向用户浏览器设置cookie
		if(res.getCode() == CallBackResult.CODE_OK) {
			//设置cookie
			CookieUtils.setCookie(request, response, SESSION_KEY, res.getMsg(), SESSION_EXPIRE);
		}
		
		return res;
		
	}
	
	/**
	 * 获取所有品牌列表
	 */
	@RequestMapping(value="/user/register/getBrands")
	@ResponseBody
	public List<CarBrand> getBrands() {
		List<CarBrand> list = registerService.getBrands();
		return list;
	}
	
	/**
	 * 根据品牌获取该品牌下的所有车型
	 */
	@RequestMapping(value="/user/register/getModelByBrand")
	@ResponseBody
	public List<CarModel> getModelByBrand(Integer brandId) {
		List<CarModel> list = registerService.getModelByBrand(brandId);
		return list;
	}
	
}
