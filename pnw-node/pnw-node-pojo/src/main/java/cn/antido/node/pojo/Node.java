package cn.antido.node.pojo;

import java.util.Date;

/**
 * @Description Node
 * @author Antido
 * @date 2018年3月15日 下午8:25:33
 */
public class Node {
	
	private String name;
	
	private String global_id; //全局ID,与服务器端记录ID一致
	private Boolean is_online;
	private Boolean is_lock;
	private Boolean is_close;
	private Date power;
	private Date created;
	private Date updated;
	private String node_desc;
	
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
	
	@Override
	public String toString() {
		return "Node [name=" + name + ", global_id=" + global_id + ", is_online=" + is_online + ", is_lock=" + is_lock
				+ ", is_close=" + is_close + ", power=" + power + ", created=" + created + ", updated=" + updated
				+ ", node_desc=" + node_desc + "]";
	}
	
	
	
}
