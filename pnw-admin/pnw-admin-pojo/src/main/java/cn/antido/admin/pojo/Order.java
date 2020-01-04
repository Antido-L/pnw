package cn.antido.admin.pojo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * @Description 订单实体类<br>
 * @author Antido
 * @date 2017年12月26日 下午2:31:13
 */
public class Order implements Serializable{
	/*
	CREATE TABLE `tb_order` (
	  `id` char(32) NOT NULL,
	  `user_id` char(32) DEFAULT NULL COMMENT '用户',
	  `created` datetime DEFAULT NULL COMMENT '创建时间',
	  `space_id` bigint(20) DEFAULT NULL COMMENT '车位',
	  `free_time` int(11) DEFAULT NULL COMMENT '免费时间(秒)',
	  `using_time` datetime DEFAULT NULL COMMENT '开始使用时间',
	  `reserve_time` datetime DEFAULT NULL COMMENT '开始预约时间',
	  `price` int(11) DEFAULT NULL COMMENT '价格(分/小时)',
	  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
	  `pay_state` tinyint(1) DEFAULT NULL COMMENT '支付状态 0-未支付 1-已经支付',
	  `pay_type` tinyint(4) DEFAULT NULL COMMENT '支付方式 0-钱包缴费 1-在线支付 2-线下支付',
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8
	*/
	
	public static final byte PAY_TYPE_WALLET = 0; //钱包支付
	public static final byte PAY_TYPE_ONLINE = 1; //线上支付
	public static final byte PAY_TYPE_OUTLINE = 2; //线下支付
	
	public static final byte PAY_STATE_NOT_PAY = 0; //未支付
	public static final byte PAY_STATE_HAVE_PAY = 1; //已支付
	public static final byte PAY_STATE_TIME_OUT = 2; //停车超时
	
	private String id;
	private Integer free_time;  //单位:秒
	private Integer price;		//单价:分/小时
	private Byte pay_state; //0-未支付 1-已经支付
	private Byte pay_type; // 0-钱包缴费 1-在线支付 2-线下支付
	private Integer subtotal;  //小计
	private Date created;
	private Date reserve_time;
	private Date using_time;
	private Date end_time;
	
	private User user;
	private Space space;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getFree_time() {
		return free_time;
	}
	public void setFree_time(Integer free_time) {
		this.free_time = free_time;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	/**
	 * @return the pay_state
	 */
	public Byte getPay_state() {
		return pay_state;
	}
	/**
	 * @param pay_state the pay_state to set
	 */
	public void setPay_state(Byte pay_state) {
		this.pay_state = pay_state;
	}
	public Byte getPay_type() {
		return pay_type;
	}
	public void setPay_type(Byte pay_type) {
		this.pay_type = pay_type;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUsing_time() {
		return using_time;
	}
	public void setUsing_time(Date using_time) {
		this.using_time = using_time;
	}
	public Date getReserve_time() {
		return reserve_time;
	}
	public void setReserve_time(Date reserve_time) {
		this.reserve_time = reserve_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Space getSpace() {
		return space;
	}
	public void setSpace(Space space) {
		this.space = space;
	}
	public Integer getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Integer subtotal) {
		this.subtotal = subtotal;
	}
	
	
}
