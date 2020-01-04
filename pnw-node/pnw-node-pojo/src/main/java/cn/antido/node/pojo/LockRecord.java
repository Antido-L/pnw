package cn.antido.node.pojo;

import java.util.Date;

/**
 * @Description 锁状态记录实体类
 * @author Antido
 * @date 2018年3月15日 下午9:19:32
 */
public class LockRecord {
	/*
	CREATE TABLE `tb_lock_record` (
	  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '记录自增组件',
	  `name` varchar(4) DEFAULT NULL COMMENT '节点名',
	  `global_id` char(32) DEFAULT NULL COMMENT '全局ID',
	  `is_lock` tinyint(1) DEFAULT NULL COMMENT '锁状态:0-未锁,1-已锁',
	  `begin` datetime DEFAULT NULL COMMENT '开始时间',
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8
	*/
	private String name; 
	private String global_id;
	private boolean is_lock;
	private Date begin;
	
	public LockRecord() {
		
	}
	
	public LockRecord(String name, String global_id, boolean is_lock ,Date begin) {
		this.name = name;
		this.global_id = global_id;
		this.is_lock = is_lock;
		this.begin = begin;		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGlobal_id() {
		return global_id;
	}
	public void setGlobal_id(String global_id) {
		this.global_id = global_id;
	}
	public boolean isIs_lock() {
		return is_lock;
	}
	public void setIs_lock(boolean is_lock) {
		this.is_lock = is_lock;
	}
	public Date getBegin() {
		return begin;
	}
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LockRecord [name=" + name + ", global_id=" + global_id + ", is_lock=" + is_lock + ", begin=" + begin
				+ "]";
	}
	
	
	
}
