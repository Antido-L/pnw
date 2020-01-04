package cn.antido.admin.pojo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.antido.admin.pojo.BaseDict;
import cn.antido.admin.pojo.District;
import cn.antido.admin.pojo.Street;

/**
 * @Description 停车场基本信息编辑数据回显对象
 * @author Antido
 * @date 2018年1月6日 下午3:54:05
 */
public class ParkInfoEditEchoDTO implements Serializable {
	
	private Long parkId;
	
	private String parkName;
	private String createdStr;
	private String updatedStr;
	
	private List<District> provinceEcho;
	private List<District> cityEcho;
	private List<District> districtEcho;
	private List<Street> streetEcho;
	private Map<String,Number> selectedEcho;
	
	private Integer parkType;
	private List<BaseDict> parkTypeDict;
	
	private Integer state;
	private List<BaseDict> stateDict;
	
	private Integer designCount;
	private Integer workingCount;
	
	private Boolean isFree;
	private String freeTime;
	private String price;
	private String positionDesc;
	private String east;
	private String north;
	private String adminName;
	private Integer adminId;
	
	private String serviceIp;

	public Long getParkId() {
		return parkId;
	}

	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}

	
	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
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

	public List<District> getProvinceEcho() {
		return provinceEcho;
	}

	public void setProvinceEcho(List<District> provinceEcho) {
		this.provinceEcho = provinceEcho;
	}

	public List<District> getCityEcho() {
		return cityEcho;
	}

	public void setCityEcho(List<District> cityEcho) {
		this.cityEcho = cityEcho;
	}

	public List<District> getDistrictEcho() {
		return districtEcho;
	}

	public void setDistrictEcho(List<District> districtEcho) {
		this.districtEcho = districtEcho;
	}

	public List<Street> getStreetEcho() {
		return streetEcho;
	}

	public void setStreetEcho(List<Street> streetEcho) {
		this.streetEcho = streetEcho;
	}

	public Map<String, Number> getSelectedEcho() {
		return selectedEcho;
	}

	public void setSelectedEcho(Map<String, Number> selectedEcho) {
		this.selectedEcho = selectedEcho;
	}

	public Integer getParkType() {
		return parkType;
	}

	public void setParkType(Integer parkType) {
		this.parkType = parkType;
	}

	public List<BaseDict> getParkTypeDict() {
		return parkTypeDict;
	}

	public void setParkTypeDict(List<BaseDict> parkTypeDict) {
		this.parkTypeDict = parkTypeDict;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public List<BaseDict> getStateDict() {
		return stateDict;
	}

	public void setStateDict(List<BaseDict> stateDict) {
		this.stateDict = stateDict;
	}

	public Integer getDesignCount() {
		return designCount;
	}

	public void setDesignCount(Integer designCount) {
		this.designCount = designCount;
	}

	public Integer getWorkingCount() {
		return workingCount;
	}

	public void setWorkingCount(Integer workingCount) {
		this.workingCount = workingCount;
	}

	public Boolean getIsFree() {
		return isFree;
	}

	public void setIsFree(Boolean isFree) {
		this.isFree = isFree;
	}

	public String getFreeTime() {
		return freeTime;
	}

	public void setFreeTime(String freeTime) {
		this.freeTime = freeTime;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPositionDesc() {
		return positionDesc;
	}

	public void setPositionDesc(String positionDesc) {
		this.positionDesc = positionDesc;
	}

	public String getEast() {
		return east;
	}

	public void setEast(String east) {
		this.east = east;
	}

	public String getNorth() {
		return north;
	}

	public void setNorth(String north) {
		this.north = north;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getServiceIp() {
		return serviceIp;
	}

	public void setServiceIp(String serviceIp) {
		this.serviceIp = serviceIp;
	}

	
}
