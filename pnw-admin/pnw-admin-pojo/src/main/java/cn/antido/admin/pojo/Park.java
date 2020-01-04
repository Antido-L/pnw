package cn.antido.admin.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 停车场实体类
 * @author Antido 
 * @date 2017年12月14日  下午5:08:41
 */
public class Park implements Serializable{
	/*
	CREATE TABLE `tb_park` (
	  `id` bigint(20) NOT NULL,
	  `name` varchar(32) NOT NULL COMMENT '停车场名称',
	  `position_desc` varchar(128) DEFAULT NULL COMMENT '位置信息描述',
	  `park_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '停车场类型:0-室外,1-室内,2-立体,',
	  `free_time` int(11) COMMENT '免费时间',
	  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '停车场状态:0-废弃,1-使用中,2-维护中,3-开发中',
	  `east_longitude` int(11) DEFAULT NULL COMMENT '东经 精确到小数点后6位',
	  `north_latitude` int(11) DEFAULT NULL COMMENT '北纬',
	  `price` int(11) DEFAULT NULL COMMENT '停车价格(分/小时)',
	  `created` date DEFAULT NULL COMMENT '创建时间',
	  `updated` date DEFAULT NULL COMMENT '修改时间',
	  `park_admin_id` int(11) NOT NULL DEFAULT '-1' COMMENT '停车场负责人',
	  `park_admin_name` varchar(16) DEFAULT NULL COMMENT '管理员姓名',
	  `design_count` int(11) NOT NULL DEFAULT '30' COMMENT '设计车位数',
	  `working_count` int(11) DEFAULT NULL COMMENT '正常工作车位数',
	  `using_count` int(11) DEFAULT NULL COMMENT '正在被使用车位数',
	  `street_id` int(11) NOT NULL COMMENT '所属街道',
	  `service_ip` int(10) unsigned DEFAULT NULL COMMENT '控制器地址',
	  `direction` tinyint(3) unsigned DEFAULT NULL COMMENT '车位朝向：0-东西，1-南北',
	  `model_row` int(10) unsigned DEFAULT NULL COMMENT '模型行个数',
	  `model_col` int(10) unsigned DEFAULT NULL COMMENT '模型列个数',
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='停车场表'
	*/
	private Long id;
	
	private String name;
	private String position_desc;
	private Byte park_type; //停车场类型:0-室外,1-室内,2-立体
	private Integer free_time; //免费时间
	private Byte state; //停车场状态:0-废弃,1-使用中,2-维护中,3-开发中
	private Integer east_longitude;
	private Integer north_latitude;
	private Integer price; //停车价格(分/小时)
	private Date created;
	private Date updated;
	private Integer design_count;
	private Integer working_count;
	private Integer using_count;
	private String district_id; //冗余字段,用于做区县一级范围查询
	private String city_id;     //冗余字段,用于做市一级范围查询
	private String province_id; //冗余字段,用于做省一级范围查询
	private Long service_ip;
	private Byte direction; //车位朝向：0-东西，1-南北
	private Integer model_row; 
	private Integer model_col;
	
	private ParkAdmin parkAdmin;
	private Street street;
	private Node node;
	
	private String serviceIpStr; // 字符串形式的ip地址，数据库中只有Long型的
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition_desc() {
		return position_desc;
	}
	public void setPosition_desc(String position_desc) {
		this.position_desc = position_desc;
	}
	
	/**
	 * 停车场类型:0-室外,1-室内,2-立体
	 */
	public Byte getPark_type() {
		return park_type;
	}
	
	/**
	 * 停车场类型:0-室外,1-室内,2-立体
	 */
	public void setPark_type(Byte park_type) {
		this.park_type = park_type;
	}
	
	public Integer getFree_time() {
		return free_time;
	}
	public void setFree_time(Integer free_time) {
		this.free_time = free_time;
	}
	/**
	 * 停车场状态:0-废弃,1-使用中,2-维护中,3-开发中
	 */
	public Byte getState() {
		return state;
	}
	
	/**
	 * 停车场状态:0-废弃,1-使用中,2-维护中,3-开发中
	 */
	public void setState(Byte state) {
		this.state = state;
	}
	public Integer getEast_longitude() {
		return east_longitude;
	}
	public void setEast_longitude(Integer east_longitude) {
		this.east_longitude = east_longitude;
	}
	public Integer getNorth_latitude() {
		return north_latitude;
	}
	public void setNorth_latitude(Integer north_latitude) {
		this.north_latitude = north_latitude;
	}
	
	/**
	 * 分/小时
	 */
	public Integer getPrice() {
		return price;
	}

	/**
	 * 分/小时
	 */
	public void setPrice(Integer price) {
		this.price = price;
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
	public ParkAdmin getParkAdmin() {
		return parkAdmin;
	}
	public void setParkAdmin(ParkAdmin parkAdmin) {
		this.parkAdmin = parkAdmin;
	}
	public Integer getDesign_count() {
		return design_count;
	}
	public void setDesign_count(Integer design_count) {
		this.design_count = design_count;
	}
	public Integer getWorking_count() {
		return working_count;
	}
	public void setWorking_count(Integer working_count) {
		this.working_count = working_count;
	}
	public Integer getUsing_count() {
		return using_count;
	}
	public void setUsing_count(Integer using_count) {
		this.using_count = using_count;
	}
	public Street getStreet() {
		return street;
	}
	public void setStreet(Street street) {
		this.street = street;
	}
	public String getDistrict_id() {
		return district_id;
	}
	public void setDistrict_id(String district_id) {
		this.district_id = district_id;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getProvince_id() {
		return province_id;
	}
	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}
	public Long getService_ip() {
		return service_ip;
	}
	public void setService_ip(Long service_ip) {
		this.service_ip = service_ip;
	}
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	
	public Byte getDirection() {
		return direction;
	}
	public void setDirection(Byte direction) {
		this.direction = direction;
	}
	
	
	
	public Integer getModel_row() {
		return model_row;
	}
	public void setModel_row(Integer model_row) {
		this.model_row = model_row;
	}
	public Integer getModel_col() {
		return model_col;
	}
	public void setModel_col(Integer model_col) {
		this.model_col = model_col;
	}
	
	
	public String getServiceIpStr() {
		return serviceIpStr;
	}
	public void setServiceIpStr(String serviceIpStr) {
		this.serviceIpStr = serviceIpStr;
	}
	
	@Override
	public String toString() {
		return "Park [id=" + id + ", name=" + name + ", position_desc=" + position_desc + ", park_type=" + park_type
				+ ", free_time=" + free_time + ", state=" + state + ", east_longitude=" + east_longitude
				+ ", north_latitude=" + north_latitude + ", price=" + price + ", created=" + created + ", updated="
				+ updated + ", design_count=" + design_count + ", working_count=" + working_count + ", using_count="
				+ using_count + ", district_id=" + district_id + ", city_id=" + city_id + ", province_id=" + province_id
				+ ", service_ip=" + service_ip + ", direction=" + direction + ", model_row=" + model_row
				+ ", model_col=" + model_col + ", parkAdmin=" + parkAdmin + ", street=" + street + ", node=" + node
				+ "]";
	}
	
	
}
