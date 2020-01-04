package cn.antido.admin.pojo;

import java.io.Serializable;

/**
 * @Description 数据字典实体类<br>
 * 1:停车场类型<br>
 * 2:停车场状态<br>
 * @author Antido
 * @date 2018年1月4日 下午3:10:02
 */
public class BaseDict implements Serializable {
	/*
	CREATE TABLE `base_dict` (
	  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
	  `type_code` int(11) DEFAULT NULL COMMENT '分类编码',
	  `type_name` varchar(32) DEFAULT NULL COMMENT '分类名称',
	  `item_code` int(11) DEFAULT NULL COMMENT '项目编码',
	  `item_name` varchar(32) DEFAULT NULL COMMENT '项目名称',
	  `item_sort` int(11) NOT NULL DEFAULT '1' COMMENT '项目排序',
	  `is_online` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否上线',
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8
	*/
	private Integer type_code;
	private String type_name;
	private Integer item_code;
	private String item_name;
	
	public Integer getType_code() {
		return type_code;
	}
	public void setType_code(Integer type_code) {
		this.type_code = type_code;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public Integer getItem_code() {
		return item_code;
	}
	public void setItem_code(Integer item_code) {
		this.item_code = item_code;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	
}
