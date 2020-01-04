package cn.antido.admin.pojo;

import java.io.Serializable;

/**
 * @Description 车辆品牌实体类
 * @author Antido
 * @date 2018年8月15日 下午7:28:54
 */
public class CarBrand implements Serializable {
	
	private Integer id;
	private String name;
	
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CarBrand [id=" + id + ", name=" + name + "]";
	}
	
}
