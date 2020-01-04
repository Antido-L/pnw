package cn.antido.admin.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 短信平台调用记录
 * @author Antido
 * @date 2018年8月10日 下午5:01:25
 */
public class SMSItem implements Serializable {
	
	public static final Byte TYEP_REGIS = 0;
	public static final Byte TYEP_LOGIN = 1;
	public static final Byte TYEP_LEAVE_SUCCESS = 2;
	public static final Byte TYEP_RESERVE_TIMEOUT = 3;
	public static final Byte TYEP_PARK_TIMEOUT = 4;
	
	private Integer id;
	private String phone;
	private Date time; 
	private Byte type;
	private String biz_id;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the type
	 */
	public Byte getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Byte type) {
		this.type = type;
	}
	/**
	 * @return the biz_id
	 */
	public String getBiz_id() {
		return biz_id;
	}
	/**
	 * @param biz_id the biz_id to set
	 */
	public void setBiz_id(String biz_id) {
		this.biz_id = biz_id;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SMSItems [id=" + id + ", phone=" + phone + ", type=" + type + ", biz_id=" + biz_id + "]";
	}
	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	
	
}
