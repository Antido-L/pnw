package cn.antido.node.mapper;

import cn.antido.node.pojo.LockRecord;
/**
 * @Description 锁状态记录表操作
 * @author Antido
 * @date 2018年3月16日 下午3:20:33
 */
public interface LockRecordMapper {
	/**
	 * 插入一条日志 
	 */
	void insert(LockRecord record);
}
