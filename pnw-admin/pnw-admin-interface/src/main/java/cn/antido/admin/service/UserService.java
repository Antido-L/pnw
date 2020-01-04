package cn.antido.admin.service;

import cn.antido.admin.pojo.User;
import cn.antido.common.CallBackResult;

/**
 * @Description 管理员服务接口
 * @author Antido
 * @date 2018年8月29日 下午12:09:01
 */
public interface UserService {
	/**
	 * 验证登录
	 */
	CallBackResult checkLogin(User user);

	/**
	 * 根据
	 * @param token
	 * @return
	 */
	User getAdmin(String token);
	
}	
