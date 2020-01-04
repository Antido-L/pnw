package cn.antido.sso.mapper;

import cn.antido.admin.pojo.User;


/**
 * @Description 用户DAO
 * @author Antido
 * @date 2018年6月5日 下午7:02:27
 */
public interface UserMapper {
	
	/**
	 * 根据电话和密码查询用户 
	 */
	User selectByPhone(User user);
	
	/**
	 * 根据邮箱和密码查询用户
	 */
	User selectByEmail(User user);
	
	/**
	 * 判断用户是否存在
	 * @param name
	 * @return
	 */
	Boolean isNickNameExist(String name);

	/**
	 * 判断手机号是否存在
	 * @param name
	 * @return
	 */
	Boolean isPhoneExist(String phone);

	/**
	 * 新增一条用户数据
	 * @param user
	 */
	void insertByUser(User user);
	
	/**
	 * 更新一条数据
	 * @param user
	 */
	void updateByUser(User user);
	
	/**
	 * 根据ID获取当前用户钱包余额
	 * @param user
	 */
	Integer getWallet(String userId);
	
	/**
	 * 更新当前用余额
	 * @param user
	 */
	void updateWallet(User user);

	/**
	 * 根据电话获取用户对象
	 * @param phone
	 * @return
	 */
	User getUserByPhone(String phone);
	
	/**
	 * 根据电话获取用户对象
	 * @param phone
	 * @return
	 */
	User getUserByNickName(String nickName);
	
	/**
	 * 根据电话获取用户对象
	 * @param phone
	 * @return
	 */
	User getUserByEmail(String email);

	/**
	 * 根据用户的基本信息查询用户对象
	 * 在nick_name 或 email 或 phone中 与password匹配的字段
	 * @param u
	 * @return
	 */
	User selectByPassword(User user);
	
}
