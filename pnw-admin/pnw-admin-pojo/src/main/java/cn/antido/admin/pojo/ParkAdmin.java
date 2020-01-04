package cn.antido.admin.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 停车场负责人
 * @author Antido
 * @date 2017年12月18日 下午6:43:53
 */
public class ParkAdmin implements Serializable {
	
	/*
	CREATE TABLE `tb_park_admin` (
	  `id` int(11) NOT NULL COMMENT '管理员ID',
	  `code` varchar(16) DEFAULT NULL COMMENT '编号',
	  `name` varchar(16) DEFAULT NULL COMMENT '管理员姓名',
	  `password` char(32) DEFAULT NULL COMMENT '密码',
	  `brithday` date DEFAULT NULL COMMENT '生日',
	  `gender` tinyint(1) DEFAULT '0' COMMENT '性别',
	  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
	  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
	  `creaded` date DEFAULT NULL COMMENT '创建时间',
	  `updated` date DEFAULT NULL COMMENT '修改时间',
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='停车场管理员表'
	*/
	private Integer id;
	private String code;
	private String name;
	private String password;
	private Date birthday;
	private Byte gender;    //性别:0-男 , 1-女
	private String phone;
	private String email;
	private Date created;
	private Date updated;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Byte getGender() {
		return gender;
	}
	public void setGender(Byte gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	@Override
	public String toString() {
		return "ParkAdmin [id=" + id + ", code=" + code + ", name=" + name + ", password=" + password + ", birthday="
				+ birthday + ", gender=" + gender + ", phone=" + phone + ", email=" + email + ", created=" + created
				+ ", updated=" + updated + "]";
	}
	
	
}
