package cn.antido.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.antido.admin.pojo.User;
import cn.antido.admin.service.UserService;
import cn.antido.common.CallBackResult;
import cn.antido.common.utils.CookieUtils;

/**
 * @Description 管理元登录
 * @author Antido
 * @date 2018年8月29日 上午11:21:40
 */
@Controller
public class LoginController {
	
	@Value("${session.admin.key}")
	private String ADMIN_SESSION_KEY;
	
	@Autowired
	private UserService useService;
	
	/**
	 * 跳转到登录页面
	 */
	@RequestMapping("/login")
	public String showLogin() {
		return "login";
	}
	
	/**
	 * 验证登录
	 */
	@RequestMapping("/loginCheck")
	@ResponseBody
	public CallBackResult checkLogin(Model model, User user,
			HttpServletRequest request, HttpServletResponse response) {
		if(user == null) return CallBackResult.exception();
		if(user.getName() == null || user.getName().equals("")) return CallBackResult.exception();
		if(user.getPassword() == null || user.getPassword().equals("")) return CallBackResult.exception();
		
		CallBackResult res = useService.checkLogin(user);
		
		if(res.getCode() != CallBackResult.CODE_OK) {
			return CallBackResult.exception();
		}
		
		//放置cookie
		//关闭浏览器失效
		CookieUtils.setCookie(request, response, ADMIN_SESSION_KEY, res.getMsg());
		
		return res;
	}
	
}
