package cn.antido.admin.pojo.dto;

import java.io.Serializable;

/**
 * @Description 车辆绑定页面数据传输对象
 * @author Antido
 * @date 2018年8月21日 上午11:13:01
 */
public class CarAddDTO implements Serializable{
	
	public static final byte SHOW_TYEP_NOT = 0;
	public static final byte SHOW_TYEP_SIMPLE = 1;
	public static final byte SHOW_TYEP_NORMAL = 2;
	public static final byte SHOW_TYEP_COMPLETE = 3;
	
	private String userId;
	private String token;
	private String brand;
	private String model;
	private String carType;
	private String color;
	private Byte showType;
	private String license;
	private Integer city;
	private Boolean isShowPhone;
	private Boolean isShowLicense;
	private Boolean isShowName;
	private String opCode;
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}
	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * @return the carType
	 */
	public String getCarType() {
		return carType;
	}
	/**
	 * @param carType the carType to set
	 */
	public void setCarType(String carType) {
		this.carType = carType;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return the showType
	 */
	public Byte getShowType() {
		return showType;
	}
	/**
	 * @param showType the showType to set
	 */
	public void setShowType(Byte showType) {
		this.showType = showType;
	}
	/**
	 * @return the license
	 */
	public String getLicense() {
		return license;
	}
	/**
	 * @param license the license to set
	 */
	public void setLicense(String license) {
		this.license = license;
	}
	/**
	 * @return the city
	 */
	public Integer getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(Integer city) {
		this.city = city;
	}
	/**
	 * @return the isShowPhone
	 */
	public Boolean getIsShowPhone() {
		return isShowPhone;
	}
	/**
	 * @param isShowPhone the isShowPhone to set
	 */
	public void setIsShowPhone(Boolean isShowPhone) {
		this.isShowPhone = isShowPhone;
	}
	/**
	 * @return the isShowLicense
	 */
	public Boolean getIsShowLicense() {
		return isShowLicense;
	}
	/**
	 * @param isShowLicense the isShowLicense to set
	 */
	public void setIsShowLicense(Boolean isShowLicense) {
		this.isShowLicense = isShowLicense;
	}
	/**
	 * @return the isShowName
	 */
	public Boolean getIsShowName() {
		return isShowName;
	}
	/**
	 * @param isShowName the isShowName to set
	 */
	public void setIsShowName(Boolean isShowName) {
		this.isShowName = isShowName;
	}
	/**
	 * @return the opCode
	 */
	public String getOpCode() {
		return opCode;
	}
	/**
	 * @param opCode the opCode to set
	 */
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CarAddDTO [userId=" + userId + ", token=" + token + ", brand=" + brand + ", model=" + model
				+ ", carType=" + carType + ", color=" + color + ", showType=" + showType + ", license=" + license
				+ ", city=" + city + ", isShowPhone=" + isShowPhone + ", isShowLicense=" + isShowLicense
				+ ", isShowName=" + isShowName + ", opCode=" + opCode + "]";
	}
	
}
