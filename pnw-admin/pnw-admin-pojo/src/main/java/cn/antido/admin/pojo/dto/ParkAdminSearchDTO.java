package cn.antido.admin.pojo.dto;

import java.io.Serializable;

/**
 * @Description 用于封装后台keyword搜索，返回数据 
 * @author Antido
 * @date:2018年2月5日 下午11:31:40
 */
public class ParkAdminSearchDTO implements Serializable {
	private String id;
	private String name;
	private String desc;
	private String cityName;     
	private String state;
	
	public String getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = (String) id;
	}
	public String getName() {
		return name;
	}
	public void setName(Object name) {
		this.name = (String) name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(Object desc) {
		this.desc = (String) desc;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(Object cityName) {
		this.cityName = (String) cityName;
	}
	public String getState() {
		return state;
	}
	public void setState(Object state) {
		String str = state.toString();
		
		if("0".equals(str)) {
			this.state = "废弃";
		} else if("1".equals(str)) {
			this.state = "使用中";
		} else if("2".equals(str)) {
			this.state = "维护中";
		} else if("3".equals(str)) {
			this.state = "开发中";
		}
	}
	
	@Override
	public String toString() {
		return "ParkAdminSearchDTO [id=" + id + ", name=" + name + ", desc=" + desc + ", cityName=" + cityName
				+ ", state=" + state + "]";
	} 
	
	
}
