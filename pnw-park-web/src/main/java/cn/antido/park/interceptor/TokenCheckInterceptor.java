package cn.antido.park.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.antido.common.CallBackResult;
import cn.antido.common.redis.JedisClient;
import cn.antido.common.utils.CookieUtils;
import cn.antido.common.utils.JsonUtils;

/**
 * @Description 用户登录确认拦截器<br>
 * 当用户需要对车位进行操作时需要通过此拦截器<br>
 * @author Antido
 * @date 2018年6月13日 上午11:14:52
 */
public class TokenCheckInterceptor implements HandlerInterceptor {

	@Value("${session.SESSION_KEY}")
	private String SESSION_KEY;
	
	@Value("${redis.session.SESSION_NAME}")
	private String SESSION_NAME;
	
	@Autowired
	private JedisClient jedisClient;
	
	/**
	 * 确认用户cookie未过期,redis中对应的token过期时间与客户端token过期时间一致
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = CookieUtils.getCookieValue(request, SESSION_KEY);
		//当获取到用户的合法cookie时放行
		if(token != null && !"".equals(token)) {
			//根据token查询redis 查询成功放行
			String user = jedisClient.get(SESSION_NAME + token);
			if(user != null) 
				return true;
		}
		
		//当cookie出现异常时处理 在拦截器内处理该请求
		CallBackResult res = CallBackResult.expired("未找到匹配的cookie");
		String json = JsonUtils.obj2Json(res);
		//写回json格式数据
		response.getWriter().write(json);
		
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
