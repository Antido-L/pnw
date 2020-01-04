package cn.antido.connection.pojo;

import java.io.Serializable;

/**
 * @Description 停车确认数据封装对象
 * 实现Comparable接口用于在PriorityBlockingQueue判断优先级别<br>
 * Compare方法在返回-1时优先级高
 * 优先级别以expire字段为比较对象向 expire越小优先级越高
 * @author Antido
 * @date 2018年6月28日 下午8:53:02
 */
public class ConfirmTarget implements Serializable, Comparable<ConfirmTarget>{
	
	private String node_id; //目标ID
	private String address;
	private Integer port;
	private String conn_key;
	private Long parkId; //所在停车场
	private Long spaceId; //被操作的车位
	private String userId; //操作的用户
	private String token;
	private String orderId;
	
	private Long expire; //过期时间
	
	private Integer confirmTimes = 0; //确认的次数(默认为0)
	private Integer confirmLeaveTimes = 0;

	
	/**
	 * @return the confirmLeaveTimes
	 */
	public Integer getConfirmLeaveTimes() {
		return confirmLeaveTimes;
	}

	/**
	 * @param confirmLeaveTimes the confirmLeaveTimes to set
	 */
	public void setConfirmLeaveTimes(Integer confirmLeaveTimes) {
		this.confirmLeaveTimes = confirmLeaveTimes;
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
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	 * @return the parkId
	 */
	public Long getParkId() {
		return parkId;
	}

	/**
	 * @param parkId the parkId to set
	 */
	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}

	/**
	 * @return the spaceId
	 */
	public Long getSpaceId() {
		return spaceId;
	}

	/**
	 * @param spaceId the spaceId to set
	 */
	public void setSpaceId(Long spaceId) {
		this.spaceId = spaceId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the expire
	 */
	public Long getExpire() {
		return expire;
	}

	/**
	 * @param expire the expire to set
	 */
	public void setExpire(Long expire) {
		this.expire = expire;
	}

	/**
	 * @return the confirmTimes
	 */
	public Integer getConfirmTimes() {
		return confirmTimes;
	}

	/**
	 * @param confirmTimes the confirmTimes to set
	 */
	public void setConfirmTimes(Integer confirmTimes) {
		this.confirmTimes = confirmTimes;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConfirmTarget [node_id=" + node_id + ", address=" + address + ", port=" + port + ", conn_key="
				+ conn_key + ", parkId=" + parkId + ", spaceId=" + spaceId + ", userId=" + userId + ", token=" + token
				+ ", orderId=" + orderId + ", expire=" + expire + ", confirmTimes=" + confirmTimes
				+ ", confirmLeaveTimes=" + confirmLeaveTimes + "]";
	}

	@Override
	public int compareTo(ConfirmTarget o) {
		return (int)(this.expire - o.expire);
	}
	
	
}
