package cn.antido.admin.pojo;

import java.io.Serializable;

/**
 * @Description 街道实体类<br>
 * id共9位 前六位为district实体id 后三位是自身id<br>
 * district_id为所属行政区
 * @author Antido
 * @date 2017年12月24日 上午11:33:16
 */
public class Street implements Serializable{
	/*
	CREATE TABLE `tb_street` (
	  `id` int(11) NOT NULL COMMENT '街道ID',
	  `name` varchar(16) DEFAULT NULL COMMENT '名称',
	  `district_id` int(11) DEFAULT NULL COMMENT '所属行政区域',
	  `park_admin_id` int(11) DEFAULT NULL COMMENT '负责人',
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8
	*/
	private Integer id;
	private String name;
	
	private District district;
	private ParkAdmin parkAdmin;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public District getDistrict() {
		return district;
	}
	public void setDistrict(District district) {
		this.district = district;
	}

	
	public ParkAdmin getParkAdmin() {
		return parkAdmin;
	}
	public void setParkAdmin(ParkAdmin parkAdmin) {
		this.parkAdmin = parkAdmin;
	}
	
	@Override
	public String toString() {
		return "Street [id=" + id + ", name=" + name + ", district=" + district + ", parkAdmin=" + parkAdmin + "]";
	}
	
	
}
