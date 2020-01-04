package cn.antido.node.pojo;

import java.util.Date;

/**
 * @Description 接近状态记录实体类
 * @author Antido
 * @date 2018年3月15日 下午9:28:44
 */
public class CloseRecord {
	/*
		CREATE TABLE `tb_close_record` (
		  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '记录自增组件',
		  `name` varchar(4) DEFAULT NULL COMMENT '节点名',
		  `global_id` char(32) DEFAULT NULL COMMENT '全局ID',
		  `is_close` tinyint(1) DEFAULT NULL COMMENT '接近状态:0-未接近,1-接近',
		  `begin` datetime DEFAULT NULL COMMENT '开始时间',
		  PRIMARY KEY (`id`)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8
	 */
	private String name; 
	private String global_id;
	private boolean is_close;
	private Date begin;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the global_id
	 */
	public String getGlobal_id() {
		return global_id;
	}
	/**
	 * @param global_id the global_id to set
	 */
	public void setGlobal_id(String global_id) {
		this.global_id = global_id;
	}
	/**
	 * @return the is_close
	 */
	public boolean isIs_close() {
		return is_close;
	}
	/**
	 * @param is_close the is_close to set
	 */
	public void setIs_close(boolean is_close) {
		this.is_close = is_close;
	}
	/**
	 * @return the begin
	 */
	public Date getBegin() {
		return begin;
	}
	/**
	 * @param begin the begin to set
	 */
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CloseRecord [name=" + name + ", global_id=" + global_id + ", is_close=" + is_close + ", begin=" + begin
				+ "]";
	}
	
	
	
	
	
}
