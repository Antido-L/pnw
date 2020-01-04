package cn.antido.admin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.antido.admin.pojo.User;
import cn.antido.admin.service.UserService;
import cn.antido.common.redis.JedisClient;
import cn.antido.common.utils.CookieUtils;

/**
 * @Description 管理员登录校验
 * @author Antido
 * @date 2018年8月29日 上午11:19:44
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	@Value("${session.admin.key}")
	private String ADMIN_SESSION_KEY;
	
	@Value("${redis.session.admin.name}")
	private String SESSION_ADMIN_NAME;
	
	@Autowired
	private UserService userService;

	/**
	 * 验证用户cookie 与REDIS中的token 做对比
	 * 验证失败重定向到login
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//从cookie中取token
		String token = CookieUtils.getCookieValue(request, ADMIN_SESSION_KEY);
		if(token == null || token.equals("")) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}
		
		//验证REDIS
		User user = userService.getAdmin(token);
		if(user == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
