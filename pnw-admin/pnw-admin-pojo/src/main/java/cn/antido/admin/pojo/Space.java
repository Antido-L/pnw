package cn.antido.admin.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 停车位<br>
 * id(行政区域编码6位+区域编码4位+停车场编码4位+车位编码4位,共18位)<br>
 * @author Antido
 * @date 2017年12月19日 下午4:29:40
 */
public class Space implements Serializable {
	
	public final static Byte USING_STATE_FREE = 0;
	public final static Byte USING_STATE_USED = 1;
	public final static Byte USING_STATE_RESERVED = 2;
	
	public final static Byte RUNNING_STATE_ABANDON = 0;
	public final static Byte RUNNING_STATE_INUSE = 1;
	public final static Byte RUNNING_STATE_FIX = 2;
	
	/*
	CREATE TABLE `tb_space` (
	  `id` bigint(20) NOT NULL,
	  `code` varchar(16) DEFAULT NULL COMMENT '车位编码',
	  `space_type` tinyint(4) DEFAULT '0' COMMENT '车位类型:0-任意车型,1-仅小型车,2-仅中型车与小型车,3-仅大型车',
	  `running_state` tinyint(4) DEFAULT '1' COMMENT '运行状态:0-废弃,1-正常使用,2-维护中',
	  `useing_state` tinyint(4) DEFAULT '0' COMMENT '使用状态:0-空闲,1-正在使用,2-预约中',
	  `direction` tinyint(4) DEFAULT '0' COMMENT '车位方向:0-北,1-东,2-南,3-西',
	  `reserve_time` datetime DEFAULT NULL COMMENT '被预定的时间',
	  `parked_time` datetime DEFAULT NULL COMMENT '被使用的时间',
	  `leaving_time` datetime DEFAULT NULL COMMENT '约定离开的时间',
	  `created` date DEFAULT NULL COMMENT '创建时间',
	  `updated` date DEFAULT NULL COMMENT '更新时间',
	  `park_id` bigint(20) NOT NULL COMMENT '所属停车场',
	  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
	  `user_id` char(32) DEFAULT NULL COMMENT '当前用户',
	  `node_Id` char(32) DEFAULT NULL COMMENT '当前使用的节点',
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='停车位表,关联停车场'
 	*/
	private Long id;
	private String idStr;
	
	private String code;
	private Byte space_type; //车位类型:0-任意车型,1-仅小型车,2-仅中型车与小型车,3-仅大型车
	private Byte running_state; //运行状态:0-废弃,1-正常使用,2-维护中
	private Byte using_state; //使用状态:0-空闲,1-正在使用,2-预约中
	private Byte direction; //车位方向:0-北,1-东,2-南,3-西
	private Date reserve_time;
	private Date parked_time;
	private Date leaving_time;
	private Date created;
	private Date updated;
	private String remark;
	private Integer x_axis;
	private Integer y_axis;
	
	private Park park;
	private Node node; 
	private String user_id; //当前正在使用的用户 
	private String order_id;//当前订单
	
	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Long getId() {
		return id;
	}
	
	/**
	 * 在封装id的时候，自动生成一个String类型的ID<br>
	 * js 只能处理2^53大小的数字，所以前端需要String类型的ID
	 */
	public void setId(Long id) {
		this.id = id;
		if(id != null) {
			this.idStr = id.toString();
		}
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Byte getSpace_type() {
		return space_type;
	}
	public void setSpace_type(Byte space_type) {
		this.space_type = space_type;
	}
	public Byte getRunning_state() {
		return running_state;
	}
	public void setRunning_state(Byte running_state) {
		this.running_state = running_state;
	}
	public Byte getUsing_state() {
		return using_state;
	}
	public void setUsing_state(Byte using_state) {
		this.using_state = using_state;
	}
	public Byte getDirection() {
		return direction;
	}
	public void setDirection(Byte direction) {
		this.direction = direction;
	}
	public Date getReserve_time() {
		return reserve_time;
	}
	public void setReserve_time(Date reserve_time) {
		this.reserve_time = reserve_time;
	}
	public Date getParked_time() {
		return parked_time;
	}
	public void setParked_time(Date parked_time) {
		this.parked_time = parked_time;
	}
	public Date getLeaving_time() {
		return leaving_time;
	}
	public void setLeaving_time(Date leaving_time) {
		this.leaving_time = leaving_time;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Park getPark() {
		return park;
	}
	public void setPark(Park park) {
		this.park = park;
	}
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	
	
	
	public Integer getX_axis() {
		return x_axis;
	}
	public void setX_axis(Integer x_axis) {
		this.x_axis = x_axis;
	}
	
	
	public Integer getY_axis() {
		return y_axis;
	}
	public void setY_axis(Integer y_axis) {
		this.y_axis = y_axis;
	}
	
	public String getIdStr() {
		return idStr;
	}
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	

	/**
	 * @return the order_id
	 */
	public String getOrder_id() {
		return order_id;
	}

	/**
	 * @param order_id the order_id to set
	 */
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Space [id=" + id + ", idStr=" + idStr + ", code=" + code + ", space_type=" + space_type
				+ ", running_state=" + running_state + ", using_state=" + using_state + ", direction=" + direction
				+ ", reserve_time=" + reserve_time + ", parked_time=" + parked_time + ", leaving_time=" + leaving_time
				+ ", created=" + created + ", updated=" + updated + ", remark=" + remark + ", x_axis=" + x_axis
				+ ", y_axis=" + y_axis + ", park=" + park + ", node=" + node + ", user_id=" + user_id + ", order_id="
				+ order_id + "]";
	}

	
	
}
