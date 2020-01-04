package cn.antido.admin.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 控制器地址注册表实体类
 * @author Antido
 * @date 2018年3月26日 上午10:45:57
 */
public class NodeAddress implements Serializable {
	/*
	CREATE TABLE `tb_node_address` (
	  `node_id` char(32) NOT NULL COMMENT '协调器ID',
	  `address` int(10) unsigned DEFAULT NULL COMMENT '控制端地址',
	  `port` int(10) unsigned DEFAULT NULL COMMENT '端口号',
	  `state` tinyint(3) unsigned DEFAULT NULL COMMENT '状态0-下线,1-上线,2-异常',
	  `key` varchar(32) DEFAULT NULL COMMENT '通讯密钥',
	  `created` datetime DEFAULT NULL COMMENT '创建时间',
  	  `updated` datetime DEFAULT NULL COMMENT '上次修改时间',
	  PRIMARY KEY (`node_id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8
	*/
	private String node_id;
	private String address;
	private Integer port;
	private Byte state;
	private String conn_key;
	private Date created;
	private Date updated;
	
	/**
	 * @return the conn_key
	 */
	public String getConn_key() {
		return conn_key;
	}
	/**
	 * @param conn_key the conn_key to set
	 */
	public void setConn_key(String conn_key) {
		this.conn_key = conn_key;
	}
	/**
	 * @return the node_id
	 */
	public String getNode_id() {
		return node_id;
	}
	/**
	 * @param node_id the node_id to set
	 */
	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}
	
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the state
	 */
	public Byte getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(Byte state) {
		this.state = state;
	}
	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}
	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}
	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}
	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	/**
	 * @return the port
	 */
	public Integer getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(Integer port) {
		this.port = port;
	}
	
	
}
