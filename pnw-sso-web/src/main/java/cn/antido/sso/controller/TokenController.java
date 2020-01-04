package cn.antido.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.antido.common.CallBackResult;
import cn.antido.common.utils.CookieUtils;
import cn.antido.sso.service.TokenService;

/**
 * @Description 用户token相关controller
 * @author Antido
 * @date 2018年6月6日 下午6:14:12
 */
@Controller
public class TokenController {
	
	@Value("${session.SESSION_KEY}")
	private String SESSION_KEY;
	
	@Value("${session.SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Autowired
	private TokenService tokenService;

	/**
	 * 根据token获取当前user对象<br>
	 * 一般由前端异步请求获取登录信息<br>
	 * 当校验通过后会重置客户端cookie过期时间,重置redis中对应的token过期时间
	 * @param token
	 */
	@RequestMapping(value="/user/token")
	@ResponseBody
	public Object getUser(String token, String callback,
			HttpServletRequest request, HttpServletResponse response) {
		
		if(token == null || "".equals(token)) {
			return CallBackResult.error("异常的token");
		}
		
		CallBackResult res = tokenService.getUserByToken(token);
		//当token校验通过,更新过期时间
		if(res.getCode() == CallBackResult.CODE_OK) {
			//设置cookie
			CookieUtils.setCookie(request, response, SESSION_KEY, res.getMsg(), SESSION_EXPIRE);
		}
		
		
		//判断是否为jsonp请求
		if(callback != null && !"".equals(callback)) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(res);
			mappingJacksonValue.setJsonpFunction(callback);
			
			return mappingJacksonValue;
		}
		
		return res;
	}
	
	
	/**
	 * 删除domain = .antido.cn的cookie
	 * 实现Logout
	 */
	@RequestMapping(value="/user/logout")
	@ResponseBody
	public Object logout(String token, String callback,
			HttpServletRequest request, HttpServletResponse response) {
		if(token == null || "".equals(token)) {
			return CallBackResult.error("异常的token");
		}
		
		//在CookieUtils中会获取request中的顶级域名
		//删除该顶级域名下的
		CookieUtils.deleteCookie(request, response, SESSION_KEY);
		
		//判断是否为jsonp请求
		if(callback != null && !"".equals(callback)) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(CallBackResult.ok());
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		
		return CallBackResult.ok();
	}
	
}
