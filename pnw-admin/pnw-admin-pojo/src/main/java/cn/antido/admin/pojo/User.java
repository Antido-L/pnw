package cn.antido.admin.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 用户实体类<br>
 * id设置规则为UUID
 * @author Antido
 * @date 2017年12月20日 上午11:13:48
 */
public class User implements Serializable {
	/*
	CREATE TABLE `tb_user` (
	  `id` char(32) NOT NULL,
	  `nick_name` varchar(16) NOT NULL DEFAULT 'pnw_user' COMMENT '用户名',
	  `name` varchar(16) DEFAULT NULL COMMENT '姓名',
	  `password` char(32) DEFAULT NULL COMMENT '密码',
	  `phone` varchar(15) DEFAULT NULL COMMENT '电话',
	  `email` varchar(32) DEFAULT NULL COMMENT '电子邮箱',
	  `car_id` char(32) DEFAULT NULL COMMENT '当前使用车辆',
	  `created` date DEFAULT NULL,
	  `updated` date DEFAULT NULL,
	  `client_type` tinyint(4) DEFAULT NULL COMMENT '使用的客户端类型',
	  `space_id` bigint(20) unsigned DEFAULT NULL COMMENT '用户正在使用的车位',
  	  `park_id` bigint(20) unsigned DEFAULT NULL COMMENT '用户正在使用的停车场',
	  `state` tinyint(3) unsigned DEFAULT '0' COMMENT '用户当前状态',
	  `opcode` int(6) unsigned DEFAULT NULL COMMENT '操作码',
	  `wallet` INT(10) UNSIGNED DEFAULT NULL COMMENT '钱包余额(单位:角)',
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表'
	*/
	
	public static final byte STATE_NORMAL = 0; //正常
	public static final byte STATE_CHECK_WAIT = 1; //等待停车确认
	public static final byte STATE_CHECK_TIMEOUT = 2; //等待停车确认超时
	public static final byte STATE_LEAVE_WAIT = 3; //等待车辆离开
	public static final byte STATE_LEAVE_TIMEOUT = 4; //车辆离开超时
	public static final byte STATE_NOT_PARK = 5; //禁止使用
	public static final byte STATE_RESERVING = 6; //已有预约
	
	public static final byte CLIENT_TYPE_ADMIN = 0; //已有预约
	public static final byte CLIENT_TYPE_USER = 1; //已有预约
	
	private String id;
	private String nick_name;
	private String name;
	private String password;
	private String phone;
	private String email;
	private Byte client_type;
	private Date created;
	private Date updated;
	private String opCode; //操作码 
	private Byte state; //当前状态
	private Integer wallet; //钱包余额(单位:角)
	
	private Space space; //当前
	private Long park_id;
	private Car car; //当前使用车辆
	
	private Boolean show_phone;
	private Boolean show_license;
	private Boolean show_name;
	
	
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

	/**
	 * @return the park_id
	 */
	public Long getPark_id() {
		return park_id;
	}

	/**
	 * @param park_id the park_id to set
	 */
	public void setPark_id(Long park_id) {
		this.park_id = park_id;
	}

	/**
	 * @return the space
	 */
	public Space getSpace() {
		return space;
	}

	/**
	 * @param space the space to set
	 */
	public void setSpace(Space space) {
		this.space = space;
	}

	/**
	 * @return the nick_name
	 */
	public String getNick_name() {
		return nick_name;
	}

	/**
	 * @param nick_name the nick_name to set
	 */
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Byte getClient_type() {
		return client_type;
	}

	public void setClient_type(Byte client_type) {
		this.client_type = client_type;
	}
	
	

	/**
	 * @return the state
	 */
	public Byte getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Byte state) {
		this.state = state;
	}

	/**
	 * @return the wallet
	 */
	public Integer getWallet() {
		return wallet;
	}

	/**
	 * @param wallet the wallet to set
	 */
	public void setWallet(Integer wallet) {
		this.wallet = wallet;
	}

	
	/**
	 * @return the show_phone
	 */
	public Boolean getShow_phone() {
		return show_phone;
	}

	/**
	 * @param show_phone the show_phone to set
	 */
	public void setShow_phone(Boolean show_phone) {
		this.show_phone = show_phone;
	}

	/**
	 * @return the show_license
	 */
	public Boolean getShow_license() {
		return show_license;
	}

	/**
	 * @param show_license the show_license to set
	 */
	public void setShow_license(Boolean show_license) {
		this.show_license = show_license;
	}

	/**
	 * @return the show_name
	 */
	public Boolean getShow_name() {
		return show_name;
	}

	/**
	 * @param show_name the show_name to set
	 */
	public void setShow_name(Boolean show_name) {
		this.show_name = show_name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", nick_name=" + nick_name + ", name=" + name + ", password=" + password + ", phone="
				+ phone + ", email=" + email + ", client_type=" + client_type + ", created=" + created + ", updated="
				+ updated + ", opCode=" + opCode + ", state=" + state + ", wallet=" + wallet + ", space=" + space
				+ ", park_id=" + park_id + ", car=" + car + "]";
	}

	
}
