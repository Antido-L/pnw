package cn.antido.sso.mapper;

import cn.antido.admin.pojo.SMSItem;

/**
 * @Description 短信平台记录数据库操作接口
 * @author Antido
 * @date 2018年8月10日 下午5:02:27
 */
public interface SMSMapper {
	/**
	 * 插入一条数据
	 */
	void insertBySMSItem(SMSItem item);
}
	
