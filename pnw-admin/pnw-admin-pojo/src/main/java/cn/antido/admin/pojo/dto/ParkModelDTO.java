package cn.antido.admin.pojo.dto;

import java.io.Serializable;

import cn.antido.admin.pojo.Park;

/**
 * @Description 停车场列表模态框数据传输对象
 * @author Antido
 * @date 2018年1月2日 下午4:15:33
 */
public class ParkModelDTO implements Serializable{
	
	private Park park;
	
	private String parkState;
	private String parkPrice;
	private String parkUsing;
	private String parkPosition;
	private String createdStr;
	private String updatedStr;
	private String serviceIp;
	
	public Park getPark() {
		return park;
	}
	public void setPark(Park park) {
		this.park = park;
	}
	public String getParkState() {
		return parkState;
	}
	public void setParkState(String parkState) {
		this.parkState = parkState;
	}
	public String getParkPrice() {
		return parkPrice;
	}
	public void setParkPrice(String parkPrice) {
		this.parkPrice = parkPrice;
	}
	public String getParkUsing() {
		return parkUsing;
	}
	public void setParkUsing(String parkUsing) {
		this.parkUsing = parkUsing;
	}
	public String getParkPosition() {
		return parkPosition;
	}
	public void setParkPosition(String parkPosition) {
		this.parkPosition = parkPosition;
	}
	public String getCreatedStr() {
		return createdStr;
	}
	public void setCreatedStr(String createdStr) {
		this.createdStr = createdStr;
	}
	public String getUpdatedStr() {
		return updatedStr;
	}
	public void setUpdatedStr(String updatedStr) {
		this.updatedStr = updatedStr;
	}
	public String getServiceIp() {
		return serviceIp;
	}
	public void setServiceIp(String serviceIp) {
		this.serviceIp = serviceIp;
	}
	
	
}
