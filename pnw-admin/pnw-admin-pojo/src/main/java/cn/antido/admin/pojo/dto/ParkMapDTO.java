package cn.antido.admin.pojo.dto;

import java.io.Serializable;
import java.util.List;

import cn.antido.admin.pojo.ParkModel;

/**
 * @Description 停车场模拟图数据传输对象 
 * @author Antido
 * @date:2018年1月19日 下午1:57:21
 */
public class ParkMapDTO implements Serializable{
	
	private Integer row;
	private Integer col;
	private Byte direction;
	private List<ParkModelItemDTO> items;
	
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Integer getCol() {
		return col;
	}
	public void setCol(Integer col) {
		this.col = col;
	}
	public Byte getDirection() {
		return direction;
	}
	public void setDirection(Byte direction) {
		this.direction = direction;
	}
	public List<ParkModelItemDTO> getItems() {
		return items;
	}
	public void setItems(List<ParkModelItemDTO> items) {
		this.items = items;
	}
	@Override
	public String toString() {
		return "ParkMapDTO [row=" + row + ", col=" + col + ", direction=" + direction + ", items=" + items + "]";
	}
	
	
}
