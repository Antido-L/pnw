package cn.antido.admin.pojo;

import java.io.Serializable;

/**
 * @Description 节点消息接收反馈实体类<br>
 * 0-正常,1-出现异常,2-错误
 * @author Antido
 * @date 2018年3月21日 下午7:07:28
 */
public class ConnCallBack implements Serializable {
	private Integer code; //状态码
	private String msg; //消息
	
	public ConnCallBack() {
		
	}
	
	public ConnCallBack(Integer code) {
		this.code = code;
	}
	
	public static ConnCallBack build(Integer code, String msg) {
		ConnCallBack callback = new ConnCallBack();
		callback.setCode(code);
		callback.setMsg(msg);
		return callback;
	}

	public static ConnCallBack OK() {
		return new ConnCallBack(0);
	}
	
	public static ConnCallBack ERROR(String msg) {
		ConnCallBack callback = new ConnCallBack(2);
		callback.setMsg(msg);
		return callback;
	}
	
	
	public static ConnCallBack EXCEPTION(String msg) {
		ConnCallBack callback = new ConnCallBack(2);
		callback.setMsg(msg);
		return callback;
	}

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
