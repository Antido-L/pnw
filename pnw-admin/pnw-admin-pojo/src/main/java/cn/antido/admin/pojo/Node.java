package cn.antido.admin.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 车位通信节点实体类
 * @author Antido
 * @date 2017年12月26日 下午2:36:36
 */
public class Node implements Serializable {
	/*
	CREATE TABLE `tb_node` (
	  `id` char(32) NOT NULL,
	  `is_online` tinyint(1) DEFAULT '0' COMMENT '工作状态:0-离线,1-在线',
	  `is_lock` tinyint(1) DEFAULT '0' COMMENT '车位锁电平:0-低,1-高',
	  `is_close` tinyint(1) DEFAULT '0' COMMENT '传感器状态:0-低,1-高',
	  `power` int(11) DEFAULT '0' COMMENT '电量用尽时间',
	  `created` date DEFAULT NULL COMMENT '创建时间',
	  `updated` date DEFAULT NULL COMMENT '修改时间',
	  `node_desc` varchar(32) DEFAULT 'Zigbee' COMMENT '描述',
	  `space_id` char(32) DEFAULT NULL COMMENT '所属车位',
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Zigbee节点表'
	*/
	private String id;
	private Boolean is_online;
	private Boolean is_lock;
	private Boolean is_close;
	private Date power;
	private Date created;
	private Date updated;
	private String node_desc;
	
	private String parent_id; //所属协调器 协调器本身为null
	
	private Long space_id;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getIs_online() {
		return is_online;
	}

	public void setIs_online(Boolean is_online) {
		this.is_online = is_online;
	}

	public Boolean getIs_lock() {
		return is_lock;
	}

	public void setIs_lock(Boolean is_lock) {
		this.is_lock = is_lock;
	}

	public Boolean getIs_close() {
		return is_close;
	}

	public void setIs_close(Boolean is_close) {
		this.is_close = is_close;
	}

	public Date getPower() {
		return power;
	}

	public void setPower(Date power) {
		this.power = power;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getNode_desc() {
		return node_desc;
	}

	public void setNode_desc(String node_desc) {
		this.node_desc = node_desc;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public Long getSpace_id() {
		return space_id;
	}

	public void setSpace_id(Long space_id) {
		this.space_id = space_id;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", is_online=" + is_online + ", is_lock=" + is_lock + ", is_close=" + is_close
				+ ", power=" + power + ", created=" + created + ", updated=" + updated + ", node_desc=" + node_desc
				+ ", parent_id=" + parent_id + ", space_id=" + space_id + "]";
	}

	
	
}
