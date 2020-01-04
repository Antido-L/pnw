package cn.antido.connection.pojo;

import java.io.Serializable;

public class ConnResult implements Serializable{
	
	public final static int CODE_OK = 0;
	public final static int CODE_EXCEPTION = 1;
	public final static int CODE_ERROR = 2;
	
	private Integer code; //状态码
	private String msg; //消息
	private Object data; //数据
	
	public ConnResult() {
		
	}
	
	public ConnResult(Integer code) {
		this.code = code;
	}
	

	public static ConnResult OK() {
		return new ConnResult(CODE_OK);
	}
	
	public static ConnResult ERROR(String msg, Object data) {
		ConnResult res = new ConnResult(CODE_ERROR);
		res.setMsg(msg);
		res.setData(data);
		return res;
	}
	
	public static ConnResult ERROR(String msg) {
		ConnResult res = new ConnResult(CODE_ERROR);
		res.setMsg(msg);
		return res;
	}
	
	public static ConnResult EXCEPTION(String msg, Object data) {
		ConnResult res = new ConnResult(CODE_EXCEPTION);
		res.setMsg(msg);
		res.setData(data);
		return res;
	}
	
	public static ConnResult EXCEPTION(String msg) {
		ConnResult res = new ConnResult(CODE_EXCEPTION);
		res.setMsg(msg);
		return res;
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

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
	

}
