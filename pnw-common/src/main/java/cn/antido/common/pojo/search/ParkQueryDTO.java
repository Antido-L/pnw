package cn.antido.common.pojo.search;

import java.io.Serializable;

/**
 * @Description antido.search页面传来的park查询条件<br>
 * 根据查询条件从solr获取数据
 * @author Antido
 * @date 2018年4月18日 下午9:17:52
 */
public class ParkQueryDTO implements Serializable {
	
	private Byte parkType; //0-不限;1-室外;2-室内;3-立体
	private Byte spaceType; //0-不限;1-小型车;2-中大型车;
	private Byte serviceType; //0-不限;1-自助停车;2-入场计费
	private Byte chargeType; //0-不限;1-免费;2-收费
	private Boolean priceOrder; //true-升序
	private Boolean remainOrder; //true-降序
	private Boolean distanceOrder; //true-升序
	private String priceLimit; //(起始价格)-(最高价格)
	
	private String cityCode; //城市ID
	private Double x; //东经
	private Double y; //北纬
	
	private Integer currentPage;
	private Integer pageSize;
	
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
	 * @return the spaceType
	 */
	public Byte getSpaceType() {
		return spaceType;
	}
	/**
	 * @param spaceType the spaceType to set
	 */
	public void setSpaceType(Byte spaceType) {
		this.spaceType = spaceType;
	}
	/**
	 * @return the serviceType
	 */
	public Byte getServiceType() {
		return serviceType;
	}
	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(Byte serviceType) {
		this.serviceType = serviceType;
	}
	/**
	 * @return the chargeType
	 */
	public Byte getChargeType() {
		return chargeType;
	}
	/**
	 * @param chargeType the chargeType to set
	 */
	public void setChargeType(Byte chargeType) {
		this.chargeType = chargeType;
	}
	/**
	 * @return the priceOrder
	 */
	public Boolean getPriceOrder() {
		return priceOrder;
	}
	/**
	 * @param priceOrder the priceOrder to set
	 */
	public void setPriceOrder(Boolean priceOrder) {
		this.priceOrder = priceOrder;
	}
	/**
	 * @return the remainOrder
	 */
	public Boolean getRemainOrder() {
		return remainOrder;
	}
	/**
	 * @param remainOrder the remainOrder to set
	 */
	public void setRemainOrder(Boolean remainOrder) {
		this.remainOrder = remainOrder;
	}
	/**
	 * @return the distanceOrder
	 */
	public Boolean getDistanceOrder() {
		return distanceOrder;
	}
	/**
	 * @param distanceOrder the distanceOrder to set
	 */
	public void setDistanceOrder(Boolean distanceOrder) {
		this.distanceOrder = distanceOrder;
	}
	/**
	 * @return the priceLimit
	 */
	public String getPriceLimit() {
		return priceLimit;
	}
	/**
	 * @param priceLimit the priceLimit to set
	 */
	public void setPriceLimit(String priceLimit) {
		this.priceLimit = priceLimit;
	}
	
	
	/**
	 * @return the currentPage
	 */
	public Integer getCurrentPage() {
		return currentPage;
	}
	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}
	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	/**
	 * @return the x
	 */
	public Double getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(Double x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public Double getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(Double y) {
		this.y = y;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParkQueryDTO [parkType=" + parkType + ", spaceType=" + spaceType + ", serviceType=" + serviceType
				+ ", chargeType=" + chargeType + ", priceOrder=" + priceOrder + ", remainOrder=" + remainOrder
				+ ", distanceOrder=" + distanceOrder + ", priceLimit=" + priceLimit + ", cityCode=" + cityCode + ", x="
				+ x + ", y=" + y + ", currentPage=" + currentPage + ", pageSize=" + pageSize + "]";
	}
	
	
}
