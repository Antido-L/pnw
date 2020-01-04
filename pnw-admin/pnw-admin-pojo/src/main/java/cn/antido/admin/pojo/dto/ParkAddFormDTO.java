package cn.antido.admin.pojo.dto;

import java.io.Serializable;

/**
 * @Description 停车场添加页面表单提交数据传输对象<br>
 * 停车场修改页面表单提交数据传输对象<br>
 * 封装了页面表单中对应的数据
 * @author Antido
 * @date 2018年1月5日 上午11:23:10
 */
public class ParkAddFormDTO implements Serializable{
/*	provinceId:210000
	parkId:2101060150020
	cityId:210100
	districtId:210106
	streetId:210106015
	name:12
	parkType:0
	designCount:30
	modDirect:0
	modRow:3
	modCol:2
	modInfo:2,0,0,2,0,1,
	isFree:1
	freeTime:12
	price:12
	parkPositionDesc:12
	east:12
	north:12
	radioAdmin:2
	selectParkAdmin:2
	defaultParkAdmin:4
	state:3
	serviceIp:12.1.1.1*/
	
	private Integer provinceId;
	private Integer cityId;
	private Integer districtId;
	private Integer streetId;
	private String name;
	private Byte parkType;
	private Integer designCount;
	private Byte modDirect;
	private Integer modRow;
	private Integer modCol;
	private String modInfo;
	private Integer isFree;
	private Integer freeTime;
	private Double price;
	private String parkPositionDesc;
	private Double east;
	private Double north;
	private Integer radioAdmin;
	private Integer selectParkAdmin;
	private Integer defaultParkAdmin;
	private String serviceIp;
	
	//只有添加页面才有的属性
	private Long parkId;
	private	Byte state;
	
	
	@Override
	public String toString() {
		return "ParkAddFormDTO [provinceId=" + provinceId + ", cityId=" + cityId + ", districtId=" + districtId
				+ ", streetId=" + streetId + ", name=" + name + ", parkType=" + parkType + ", designCount="
				+ designCount + ", modDirect=" + modDirect + ", modRow=" + modRow + ", modCol=" + modCol + ", modInfo="
				+ modInfo + ", isFree=" + isFree + ", freeTime=" + freeTime + ", price=" + price + ", parkPositionDesc="
				+ parkPositionDesc + ", east=" + east + ", north=" + north + ", radioAdmin=" + radioAdmin
				+ ", selectParkAdmin=" + selectParkAdmin + ", defaultParkAdmin=" + defaultParkAdmin + ", serviceIp="
				+ serviceIp + ", parkId=" + parkId + ", state=" + state + "]";
	}


	/**
	 * @return the provinceId
	 */
	public Integer getProvinceId() {
		return provinceId;
	}


	/**
	 * @param provinceId the provinceId to set
	 */
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}


	/**
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}


	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}


	/**
	 * @return the districtId
	 */
	public Integer getDistrictId() {
		return districtId;
	}


	/**
	 * @param districtId the districtId to set
	 */
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}


	/**
	 * @return the streetId
	 */
	public Integer getStreetId() {
		return streetId;
	}


	/**
	 * @param streetId the streetId to set
	 */
	public void setStreetId(Integer streetId) {
		this.streetId = streetId;
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
	 * @return the parkType
	 */
	public Byte getParkType() {
		return parkType;
	}


	/**
	 * @param parkType the parkType to set
	 */
	public void setParkType(Byte parkType) {
		this.parkType = parkType;
	}


	/**
	 * @return the designCount
	 */
	public Integer getDesignCount() {
		return designCount;
	}


	/**
	 * @param designCount the designCount to set
	 */
	public void setDesignCount(Integer designCount) {
		this.designCount = designCount;
	}


	/**
	 * @return the modDirect
	 */
	public Byte getModDirect() {
		return modDirect;
	}


	/**
	 * @param modDirect the modDirect to set
	 */
	public void setModDirect(Byte modDirect) {
		this.modDirect = modDirect;
	}


	/**
	 * @return the modRow
	 */
	public Integer getModRow() {
		return modRow;
	}


	/**
	 * @param modRow the modRow to set
	 */
	public void setModRow(Integer modRow) {
		this.modRow = modRow;
	}


	/**
	 * @return the modCol
	 */
	public Integer getModCol() {
		return modCol;
	}


	/**
	 * @param modCol the modCol to set
	 */
	public void setModCol(Integer modCol) {
		this.modCol = modCol;
	}


	/**
	 * @return the modInfo
	 */
	public String getModInfo() {
		return modInfo;
	}


	/**
	 * @param modInfo the modInfo to set
	 */
	public void setModInfo(String modInfo) {
		this.modInfo = modInfo;
	}


	/**
	 * @return the isFree
	 */
	public Integer getIsFree() {
		return isFree;
	}


	/**
	 * @param isFree the isFree to set
	 */
	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}


	/**
	 * @return the freeTime
	 */
	public Integer getFreeTime() {
		return freeTime;
	}


	/**
	 * @param freeTime the freeTime to set
	 */
	public void setFreeTime(Integer freeTime) {
		this.freeTime = freeTime;
	}


	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}


	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}


	/**
	 * @return the parkPositionDesc
	 */
	public String getParkPositionDesc() {
		return parkPositionDesc;
	}


	/**
	 * @param parkPositionDesc the parkPositionDesc to set
	 */
	public void setParkPositionDesc(String parkPositionDesc) {
		this.parkPositionDesc = parkPositionDesc;
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
	 * @return the radioAdmin
	 */
	public Integer getRadioAdmin() {
		return radioAdmin;
	}


	/**
	 * @param radioAdmin the radioAdmin to set
	 */
	public void setRadioAdmin(Integer radioAdmin) {
		this.radioAdmin = radioAdmin;
	}


	/**
	 * @return the selectParkAdmin
	 */
	public Integer getSelectParkAdmin() {
		return selectParkAdmin;
	}


	/**
	 * @param selectParkAdmin the selectParkAdmin to set
	 */
	public void setSelectParkAdmin(Integer selectParkAdmin) {
		this.selectParkAdmin = selectParkAdmin;
	}


	/**
	 * @return the defaultParkAdmin
	 */
	public Integer getDefaultParkAdmin() {
		return defaultParkAdmin;
	}


	/**
	 * @param defaultParkAdmin the defaultParkAdmin to set
	 */
	public void setDefaultParkAdmin(Integer defaultParkAdmin) {
		this.defaultParkAdmin = defaultParkAdmin;
	}


	/**
	 * @return the serviceIp
	 */
	public String getServiceIp() {
		return serviceIp;
	}


	/**
	 * @param serviceIp the serviceIp to set
	 */
	public void setServiceIp(String serviceIp) {
		this.serviceIp = serviceIp;
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
	 * @return the state
	 */
	public Byte getState() {
		return state;
	}


	/**
	 * @param state the state to set
	 */
	public void setState(Byte state) {
		this.state = state;
	}
	
	
	
	
}
