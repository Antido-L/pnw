package cn.antido.sso.service;

import cn.antido.admin.pojo.User;
import cn.antido.common.CallBackResult;

/**
 * @Description 用户登录服务接口
 * @author Antido
 * @date 2018年5月31日 上午11:21:46
 */
public interface LoginService {
	
	/**
	 * 验证用户登录信息
	 */
	@Deprecated
	CallBackResult checkLogin(User user, Byte method);
	
	/**
	 * 向目标手机号发送登录短信验证码<br>
	 * 在redis中存放接受短信的手机号与验证码,5分钟后过期<br>
	 * 在一分钟内重复发送短信将返回CallBackResult.exception(msg)<br>
	 * 在msg中存放下次发送的等待时间
	 * @param phone
	 * @return
	 */
	CallBackResult sendLoginMsg(String phone);

	/**
	 * 根据手机号与短信验证码进行登录校验<br>
	 * 登录成功后返回CallBackResult.ok(msg)<br>
	 * 在msg中包含生成的用户token
	 * @param phone
	 * @param code
	 * @return 
	 */
	CallBackResult checkPhoneLogin(String phone, String code);

	/**
	 * 根据用户信息和密码进行登录校验<br>
	 * 登录成功后返回CallBackResult.ok(msg)<br>
	 * 在msg中包含生成的用户token
	 * @param phone
	 * @param code
	 * @return 
	 */	
	CallBackResult checkPasswordLogin(String username, String password);

}
