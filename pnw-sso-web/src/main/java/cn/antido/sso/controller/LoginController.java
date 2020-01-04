package cn.antido.sso.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.antido.admin.pojo.User;
import cn.antido.common.CallBackResult;
import cn.antido.common.redis.JedisClient;
import cn.antido.common.utils.CookieUtils;
import cn.antido.common.utils.ImgCodeUtils;
import cn.antido.sso.service.LoginService;

/**
 * @Description 登录控制器
 * @author Antido
 * @date 2018年5月31日 上午10:39:32
 */
@Controller
public class LoginController {
	
	/**
	 * 客户端cookie的name
	 */
	@Value("${session.SESSION_KEY}")
	private String SESSION_KEY;
	
	/**
	 * 客户端cookie超时时间
	 */
	@Value("${session.SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	
	@Value("${redis.login.imgCode.name}")
	private String IMG_CODE_NAME;
	
	@Value("${redis.login.imgCode.expire}")
	private Integer IMG_CODE_EXPIRE;
	
	@Value("${login.imgCode.size}")
	private Integer IMG_CODE_SIZE;
	
	@Value("${login.imgCode.width}")
	private Integer IMG_CODE_WIDTH;
	
	@Value("${login.imgCode.height}")
	private Integer IMG_CODE_HEIGHT;
	
	
	@Autowired
	private JedisClient jedisClient;
	
	@Autowired
	private LoginService loginService;
	
	
	/**
	 * 处理404
	 */
	@RequestMapping(value="*")
	public String showNotFount() {
		return "notFound";
	}
	
	/**
	 * 展示登录页面
	 */
	@RequestMapping(value="/")
	public String showDefaultPage() {
		return "login";
	}
	
	/**
	 * 展示登录页面<br>
	 * 如果登录成功返回之前浏览的页面
	 */
	@RequestMapping(value="/login")
	public String showLogin(String redirect, Model model) {
		//将需要返回的路径放入request区中
		if(redirect != null) {
			model.addAttribute("redirect", redirect);
		}
		return "login";
	}
	
	/**
	 * 用户登录校验<br>
	 * 使用短信验证码进行登录<br>
	 * 当登录成功后向客户端写入session
	 */
	@RequestMapping(value="/user/phoneLogin")
	@ResponseBody
	public CallBackResult userLogin(String phone, String code,
			HttpServletRequest request, HttpServletResponse response) {
		//验证合法性
		if(phone == null || "".equals(phone)) {
			return CallBackResult.exception("手机号与验证码不符");
		}
		
		if(code == null || "".equals(code)) return CallBackResult.exception("手机号与验证码不符");
			
		String re = "[1][3,4,5,7,8][0-9]{9}";
		boolean isVaild = Pattern.matches(re, phone);
		if(!isVaild){
			return CallBackResult.exception("手机号与验证码不符");
		}
		
		//获取校验结果
		CallBackResult res = loginService.checkPhoneLogin(phone, code);
		
		if(res.getCode() != CallBackResult.CODE_OK) {
			return res;
		}
		
		//设置cookie
		CookieUtils.setCookie(request, response, SESSION_KEY, res.getMsg(), SESSION_EXPIRE);
		
		return res;
	}
	
	
	/**
	 * 用户登录校验<br>
	 * 使用用户信息和密码进行登录<br>
	 * 当登录成功后向客户端写入session
	 */
	@RequestMapping(value="/user/passwordLogin")
	@ResponseBody
	public CallBackResult userLogin(String username, String password, String code, String timestamp,
			HttpServletRequest request, HttpServletResponse response) {
		//验证码
		if(code == null || "".equals(code)) 
			return CallBackResult.exception("验证码不正确");
		
		String redisCode = jedisClient.get(IMG_CODE_NAME + timestamp);
		if(!code.equalsIgnoreCase(redisCode)) {
			return CallBackResult.exception("验证码不正确");
		}
		
		//用户名
		if(username == null || "".equals(username)) 
			return CallBackResult.exception("用户名或密码不正确");
		
		//密码
		if(password == null || "".equals(password)) 
			return CallBackResult.exception("用户名或密码不正确");
		
		//获取校验结果
		CallBackResult res = loginService.checkPasswordLogin(username, password);
		
		if(res.getCode() != CallBackResult.CODE_OK) {
			return res;
		}
		
		//设置cookie
		CookieUtils.setCookie(request, response, SESSION_KEY, res.getMsg(), SESSION_EXPIRE);
		
		return res;
	}
	
	
	/**
	 * 输出图形验证码<br>
	 * 将生成的验证码的存入redis中,key为请求时间戳 value为验证码的值<br>
	 * redis中的过期时间为30s<br>
	 * 当有两个用户在同一毫秒值内请求验证码时,后请求的用户会覆盖之前请求的用户,这种情况发生的较少<br>
	 * @throws IOException 
	 */
	@RequestMapping(value="/login/imgCode")
	@ResponseBody
	public void setCodeImg(String time,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		
	    // 设置响应的类型格式为图片格式  
	    response.setContentType("image/jpeg");  
	    //禁止图像缓存。  
	    response.setHeader("Pragma", "no-cache");  
	    response.setHeader("Cache-Control", "no-cache");  
	    response.setDateHeader("Expires", 0);  
		
		//获取随机的图形验证码
		String code = ImgCodeUtils.generateVerifyCode(IMG_CODE_SIZE);
		
		//在redis中存放输出的验证码设置过期时间
		jedisClient.set(IMG_CODE_NAME + time, code);
		jedisClient.expire(IMG_CODE_NAME + time, IMG_CODE_EXPIRE);
		
		//向客户端输出图片流
		ImgCodeUtils.outputImage(IMG_CODE_WIDTH, IMG_CODE_HEIGHT, response.getOutputStream(), code);
		
	}
	
	/**
	 * 向目标手机号发送登录验证码<br>
	 * 在redis中存放当前手机号的验证码<br>
	 * 当手机号在一分钟能重复提交请求时 将不会发送短信 返回CallBackResult.exception(msg)<br>
	 * 在msg中包含短信发送的等待时间<br>
	 * 返回CallBackResult对象<br>
	 */
	@RequestMapping(value="/user/login/sendLoginMsg")
	@ResponseBody
	public CallBackResult sendLoginMsg(String phone) {
		//验证手机号的合法型
		String re = "[1][3,4,5,7,8][0-9]{9}";
		boolean isVaild = Pattern.matches(re, phone);
		if(!isVaild){
			return CallBackResult.error("手机格式不正确!");
		}
		
		//向目标手机号发送注册验证码
		CallBackResult result = loginService.sendLoginMsg(phone);
		return result;
	}
	
}
