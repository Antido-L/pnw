package cn.antido.admin.pojo;

import java.io.Serializable;

/**
 * @Description 停车场模型数据实体类<br>
 * id = park_id(13) + x_axis(3) + y_axis
 * @author Antido
 * @date:2018年1月18日 下午11:29:56
 */
public class ParkModel implements Serializable {
	/*
	CREATE TABLE `tb_park_model` (
	  `id` char(19) NOT NULL,
	  `state` tinyint(3) unsigned DEFAULT NULL COMMENT '状态 0-为空 1-车位槽 2-禁止使用',
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8
	*/
	
	private String id;
	
	//2018-6-13
	//这个state表示的是车位模拟图的类型 0-为空 1-车位槽 2-禁止使用
	private Byte state; 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}
	
	
}
