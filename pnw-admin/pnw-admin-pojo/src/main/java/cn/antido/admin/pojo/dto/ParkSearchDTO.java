package cn.antido.admin.pojo.dto;

import java.io.Serializable;

/**
 * @Description 前台关键字搜索显示结果数据传输对象
 * @author Antido
 * @date 2018年3月29日 下午2:21:42
 */
public class ParkSearchDTO implements Serializable {
	
	private String id;
	private String name;
	private String desc;
	private String cityName;
	private String districtName;
	private Long remainCount;
	
	
	/**
	 * @return the remainCount
	 */
	public Long getRemainCount() {
		return remainCount;
	}
	/**
	 * @param remainCount the remainCount to set
	 */
	public void setRemainCount(Long remainCount) {
		this.remainCount = remainCount;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Object id) {
		this.id = (String)id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(Object name) {
		this.name = (String)name;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(Object desc) {
		this.desc = (String)desc;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(Object cityName) {
		this.cityName = (String)cityName;
	}
	/**
	 * @return the districtName
	 */
	public String getDistrictName() {
		return districtName;
	}
	/**
	 * @param districtName the districtName to set
	 */
	public void setDistrictName(Object districtName) {
		this.districtName = (String)districtName;
	}     
	
	
}
