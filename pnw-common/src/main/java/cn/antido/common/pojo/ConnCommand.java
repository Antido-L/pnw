package cn.antido.common.pojo;

import java.io.Serializable;

/**
 * @Description 与节点控制器连接的指令封装对象
 * @author Antido
 * @date 2018年6月13日 下午3:28:20
 */
public class ConnCommand implements Serializable{
	
	public final static byte ORDER_LOCK_OFF = 0;
	public final static byte ORDER_LOCK_ON = 1;
	public final static byte ORDER_ALIVE = 2;
	
	
	private Byte order; //指令
	private String nodeId; //目标节点ID
	private String code; //校验码
	
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
