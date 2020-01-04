package cn.antido.admin.pojo.dto;

import java.io.Serializable;

import cn.antido.admin.pojo.ParkModel;

/**
 * @Description 正确格式的modelItem数据<br>
 * 方便前端处理 
 * @author Antido
 * @date:2018年1月19日 下午2:31:41
 */
public class ParkModelItemDTO implements Serializable{
	
	private Integer xAxis;
	private Integer yAxis;
	private Byte state;
	
	public Integer getxAxis() {
		return xAxis;
	}
	public void setxAxis(Integer xAxis) {
		this.xAxis = xAxis;
	}
	public Integer getyAxis() {
		return yAxis;
	}
	public void setyAxis(Integer yAxis) {
		this.yAxis = yAxis;
	}
	public Byte getState() {
		return state;
	}
	public void setState(Byte state) {
		this.state = state;
	}
	
}
