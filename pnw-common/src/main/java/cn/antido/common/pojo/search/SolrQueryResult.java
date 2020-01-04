package cn.antido.common.pojo.search;

import java.io.Serializable;

import cn.antido.common.utils.FormatUtils;

/**
 * @Description 索引库查询结果封装
 * @author Antido
 * @date 2018年4月19日 上午9:44:51
 */
public class SolrQueryResult implements Serializable{
	private String id;
	private String name;
	private String desc;
	private Byte type;
	private Long price;
	private Long workingCount;
	private Long remainCount;
	private Double east;
	private Double north;
	private String districtName;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	public void setName(String name) {
		this.name = name;
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
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * @return the type
	 */
	public Byte getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Long type) {
		this.type = type.byteValue();
	}
	/**
	 * 重写此方法,将(分/小时) 转换 (元/小时)
	 * @return the price
	 */
	public String getPrice() {
		return FormatUtils.priceFormat(price);
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Long price) {
		this.price = price;
	}
	/**
	 * @return the workingCount
	 */
	public Long getWorkingCount() {
		return workingCount;
	}
	/**
	 * @param workingCount the workingCount to set
	 */
	public void setWorkingCount(Long workingCount) {
		this.workingCount = workingCount;
	}
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
	 * @return the east
	 */
	public Double getEast() {
		return east;
	}
	/**
	 * @param east the east to set
	 */
	public void setEast(Double east) {
		this.east = east;
	}
	/**
	 * @return the north
	 */
	public Double getNorth() {
		return north;
	}
	/**
	 * @param north the north to set
	 */
	public void setNorth(Double north) {
		this.north = north;
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
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	
	
	 
}
