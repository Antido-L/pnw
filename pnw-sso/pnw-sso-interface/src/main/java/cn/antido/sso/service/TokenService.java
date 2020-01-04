package cn.antido.sso.service;

import cn.antido.common.CallBackResult;

/**
 * @Description token服务接口
 * @author Antido
 * @date 2018年6月6日 下午6:20:51
 */
public interface TokenService {

	/**
	 * 根据token获取结果集<br>
	 * 如果token是有效的 将对应的User对象放入CallBackResult中 
	 * @param token
	 */
	CallBackResult getUserByToken(String token);

}
