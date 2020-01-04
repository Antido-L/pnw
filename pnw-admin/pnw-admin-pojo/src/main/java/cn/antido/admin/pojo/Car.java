package cn.antido.admin.pojo;

import java.io.Serializable;
import java.util.Date;

public class Car implements Serializable{
	/*
	CREATE TABLE `tb_car` (
	  `id` CHAR(32) NOT NULL,
	  `name` VARCHAR(24) DEFAULT '车辆名称' COMMENT '车辆名称',
	  `license` VARCHAR(10) DEFAULT '0000000' COMMENT '车牌号',
	  `desc` VARCHAR(24) DEFAULT NULL COMMENT '车辆公开描述',
	  `color` VARCHAR(8) DEFAULT NULL COMMENT '车身颜色',
	  `car_type` VARCHAR(8) DEFAULT NULL COMMENT '车辆类型',
	  `created` DATE DEFAULT NULL,
	  `updated` DATE DEFAULT NULL,
	  PRIMARY KEY (`id`)
	) ENGINE=INNODB DEFAULT CHARSET=utf8
	*/
	private String id;
	private String name;
	private String license;
	private String color;
	private String car_desc;
	private String car_type;
	private Date created;
	private Date updated;
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
	 * @return the car_desc
	 */
	public String getCar_desc() {
		return car_desc;
	}
	/**
	 * @param car_desc the car_desc to set
	 */
	public void setCar_desc(String car_desc) {
		this.car_desc = car_desc;
	}
	/**
	 * @return the car_type
	 */
	public String getCar_type() {
		return car_type;
	}
	/**
	 * @param car_type the car_type to set
	 */
	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}
	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}
	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}
	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}
	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Car [id=" + id + ", name=" + name + ", license=" + license + ", color=" + color + ", desc=" + car_desc
				+ ", car_type=" + car_type + ", created=" + created + ", updated=" + updated + "]";
	}
	
	
}
