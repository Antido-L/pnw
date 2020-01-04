package cn.antido.admin.pojo;

import java.io.Serializable;

/**
 * @Description 品牌车型实体类
 * @author Antido
 * @date 2018年8月15日 下午7:30:37
 */
public class CarModel implements Serializable {
	private Integer id;
	private String name;
	private Integer brand;
	private String car_type;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
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
	 * @return the brand
	 */
	public Integer getBrand() {
		return brand;
	}
	/**
	 * @param brand the brand to set
	 */
	public void setBrand(Integer brand) {
		this.brand = brand;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CarModel [id=" + id + ", name=" + name + ", brand=" + brand + ", car_type=" + car_type + "]";
	}
	
	
}
