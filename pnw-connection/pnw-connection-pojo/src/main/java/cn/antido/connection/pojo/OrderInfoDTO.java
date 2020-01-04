package cn.antido.connection.pojo;

import java.io.Serializable;

/**
 * @Description 调用车位控制指令所需的参数
 * @author Antido
 * @date 2018年5月25日 下午7:01:18
 */
public class OrderInfoDTO implements Serializable{
	
	private String controllerId; //目标控制器ID
	private String nodeId; //目标节点ID
	private Long parkId; //所在停车场
	private Long spaceId; //被操作的车位
	private String userId; //操作的用户
	private String token;
	
	private Integer leavingTime; //预计停靠时间 (单位:秒) 数据由前端计算得出
	private String opCode; //用户操作码;
	
	private Byte order; 
	
	/**
	 * 判断对象中是否存在空的成员属性<br>
	 * 检查的都是必要属性
	 * @return
	 */
	public boolean hasEmptyItem() {
		if(this.getUserId() == null || "".equals(this.getUserId()))
			return true;
		if(this.getControllerId() == null || "".equals(this.getControllerId()))
			return true;
		if(this.getNodeId() == null || "".equals(this.getNodeId()))
			return true;
		if(this.getParkId() == null || "".equals(this.getParkId()))
			return true;
		if(this.getSpaceId() == null || "".equals(this.getSpaceId()))
			return true;
		if(this.getToken() == null || "".equals(this.getToken()))
			return true;
		/*
		if(this.getLeavingTime() == null || "".equals(this.getLeavingTime()))
			return true;
		*/
		if(this.getOpCode() == null || "".equals(this.getOpCode()))
			return true;
		
		return false;
	}
	
	
	
	
	/**
	 * @return the leavingTime
	 */
	public Integer getLeavingTime() {
		return leavingTime;
	}




	/**
	 * @param leavingTime the leavingTime to set
	 */
	public void setLeavingTime(Integer leavingTime) {
		this.leavingTime = leavingTime;
	}




	/**
	 * @return the opCode
	 */
	public String getOpCode() {
		return opCode;
	}




	/**
	 * @param opCode the opCode to set
	 */
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}




	/**
	 * @return the controllerId
	 */
	public String getControllerId() {
		return controllerId;
	}
	/**
	 * @param controllerId the controllerId to set
	 */
	public void setControllerId(String controllerId) {
		this.controllerId = controllerId;
	}
	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
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
	 * @return the order
	 */
	public Byte getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Byte order) {
		this.order = order;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	
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
	
	@Override
	public String toString() {
		return "OrderInfoDTO [controllerId=" + controllerId + ", nodeId=" + nodeId + ", parkId=" + parkId + ", spaceId="
				+ spaceId + ", userId=" + userId + ", order=" + order + "]";
	}
}
