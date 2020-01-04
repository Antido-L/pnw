package cn.antido.admin.pojo;

import java.io.Serializable;

/**
 * @Description 行政区域<br>
 * 6位区域编码 字段内包含父ID 即所属ID<br>
 * 1,2位:所在省份<br>
 * 3,4位:所在城市<br>
 * 5,6位:所在区县<br>
 * 当位置精确到区县时后两位不为0<br>
 * @author Antido
 * @date 2017年12月19日 上午10:00:22
 */
public class District implements Serializable{
	/*	
	CREATE TABLE `tb_district` (
	  `id` int(11) NOT NULL COMMENT '区域编码',
	  `name` varchar(20) DEFAULT NULL,
	  `parent_id` int(11) DEFAULT NULL,
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8
	*/
	private Integer id;
	private String name;
	private Integer parent_id;
	
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
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	
	@Override
	public String toString() {
		return "District [id=" + id + ", name=" + name + ", parent_id=" + parent_id + "]";
	}
	
	
}
