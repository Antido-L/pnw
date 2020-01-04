package cn.antido.admin.pojo.dto;

import java.io.Serializable;

import cn.antido.admin.pojo.Car;
import cn.antido.admin.pojo.Node;
import cn.antido.admin.pojo.Order;
import cn.antido.admin.pojo.Space;
import cn.antido.admin.pojo.User;

/**
 * @Description 后台查看车位数据模态框显示数据对象
 * @author Antido
 * @date 2017年12月25日 下午9:11:52
 */
public class SpaceModelDTO implements Serializable{
	
	private Space space;
	private Order order;
	private User user;
	
	private String createdStr;
	private String updatedStr;
	private String parkedStr;
	private String parkedTimeStr;
	private String leavingStr;
	private String currentPrice;
	/**
	 * @return the space
	 */
	public Space getSpace() {
		return space;
	}
	/**
	 * @param space the space to set
	 */
	public void setSpace(Space space) {
		this.space = space;
	}
	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the createdStr
	 */
	public String getCreatedStr() {
		return createdStr;
	}
	/**
	 * @param createdStr the createdStr to set
	 */
	public void setCreatedStr(String createdStr) {
		this.createdStr = createdStr;
	}
	/**
	 * @return the updatedStr
	 */
	public String getUpdatedStr() {
		return updatedStr;
	}
	/**
	 * @param updatedStr the updatedStr to set
	 */
	public void setUpdatedStr(String updatedStr) {
		this.updatedStr = updatedStr;
	}
	/**
	 * @return the parkedStr
	 */
	public String getParkedStr() {
		return parkedStr;
	}
	/**
	 * @param parkedStr the parkedStr to set
	 */
	public void setParkedStr(String parkedStr) {
		this.parkedStr = parkedStr;
	}
	/**
	 * @return the parkedTimeStr
	 */
	public String getParkedTimeStr() {
		return parkedTimeStr;
	}
	/**
	 * @param parkedTimeStr the parkedTimeStr to set
	 */
	public void setParkedTimeStr(String parkedTimeStr) {
		this.parkedTimeStr = parkedTimeStr;
	}
	/**
	 * @return the leavingStr
	 */
	public String getLeavingStr() {
		return leavingStr;
	}
	/**
	 * @param leavingStr the leavingStr to set
	 */
	public void setLeavingStr(String leavingStr) {
		this.leavingStr = leavingStr;
	}
	/**
	 * @return the currentPrice
	 */
	public String getCurrentPrice() {
		return currentPrice;
	}
	/**
	 * @param currentPrice the currentPrice to set
	 */
	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	
}
