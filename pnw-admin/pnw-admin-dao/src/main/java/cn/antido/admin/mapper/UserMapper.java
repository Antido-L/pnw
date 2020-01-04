package cn.antido.admin.mapper;

import cn.antido.admin.pojo.User;


/**
 * @Description 用户DAO
 * @author Antido
 * @date 2017年12月26日 下午7:48:51
 */
public interface UserMapper {
	/**
	 * 根据主键获取完整用户对象(no password) 
	 */
	User selectByPrimaryKey(String user_id);
	
	/**
	 * 根据主键获取简单用户对象 (id,nick_name,car)
	 */
	User selectSimpleByPrimaryKey(String user_id);

	/**
	 * 插入一个用户 
	 */
	Integer insertByUser(User user);
	
	/**
	 * 更新用户数据
	 * @param user
	 */
	void updateByUser(User user);
	
	/**
	 * 根据ID获取用户操作码
	 * @param userId
	 * @return
	 */
	String getOpCode(String userId);

	/**
	 * 更新用户当前状态
	 * @param user
	 */
	void updateState(User user);
	
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
	 * 根据用户和密码获取管理员
	 * @param user
	 * @return
	 */
	User getAdminUser(User user);
}
