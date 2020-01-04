package cn.antido.admin.pojo.dto;

import java.io.Serializable;
import java.util.Date;

import cn.antido.admin.pojo.Park;
import cn.antido.admin.pojo.User;

/**
 * @Description Space过滤对象,用于封装对space查询的过滤条件
 * @author Antido
 * @date 2017年12月20日 下午7:02:14
 */
public class SpaceDTO implements Serializable {
	
	private Byte space_type; //车位类型:0-任意车型,1-仅小型车,2-仅中型车与小型车,3-仅大型车
	private Byte running_state; //运行状态:0-废弃,1-正常使用,2-维护中
	private Byte using_state; //使用状态:0-空闲,1-正在使用,2-预约中
	private Byte reserve_time; //0-升序,1-降序
	private Byte parked_time; //0-升序,1-降序
	private Byte leaving_time; //0-升序,1-降序
	
	private Park park;
	private User user;
	
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
	public Byte getReserve_time() {
		return reserve_time;
	}
	public void setReserve_time(Byte reserve_time) {
		this.reserve_time = reserve_time;
	}
	public Byte getParked_time() {
		return parked_time;
	}
	public void setParked_time(Byte parked_time) {
		this.parked_time = parked_time;
	}
	public Byte getLeaving_time() {
		return leaving_time;
	}
	public void setLeaving_time(Byte leaving_time) {
		this.leaving_time = leaving_time;
	}
	public Park getPark() {
		return park;
	}
	public void setPark(Park park) {
		this.park = park;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
} 
