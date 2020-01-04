package cn.antido.common;

import java.io.Serializable;

/**
 * @Description 结果封装
 * @author Antido
 * @date 2018年5月31日 上午11:18:10
 */
public class CallBackResult implements Serializable {
	public final static int CODE_OK = 0;
	public final static int CODE_EXCEPTION = 1;
	public final static int CODE_ERROR = 2;
	public final static int CODE_EXPIRED = 3;
	
	private Integer code; //状态码
	private String msg; //消息
	private Object data; //数据
	
	public CallBackResult() {
		
	}
	
	/**
	 * 手动传参构造返回结果对象 
	 */
	public static CallBackResult build(Integer code, String msg, Object data) {
		CallBackResult res = new CallBackResult();
		res.setCode(code);
		res.setMsg(msg);
		res.setData(data);
		return res;
	}
	
	/**
	 * 结果正常<br>
	 * @return
	 */
	public static CallBackResult ok() {
		CallBackResult res = new CallBackResult();
		res.setCode(CODE_OK);
		return res;
	}
	
	/**
	 * 结果正常<br>
	 * 包含消息
	 * @param msg
	 * @return
	 */
	public static CallBackResult ok(String msg) {
		CallBackResult res = new CallBackResult();
		res.setCode(CODE_OK);
		res.setMsg(msg);
		return res;
	}
	
	/**
	 * 操作错误
	 * @return
	 */
	public static CallBackResult error() {
		CallBackResult res = new CallBackResult();
		res.setCode(CODE_ERROR);
		return res;
	}
	
	/**
	 * 操作错误<br>
	 * 包含消息
	 * @param msg
	 * @return
	 */
	public static CallBackResult error(String msg) {
		CallBackResult res = new CallBackResult();
		res.setMsg(msg);
		res.setCode(CODE_ERROR);
		return res;
	}
	
	/**
	 * 操作结果出现可预料的异常
	 * @return
	 */
	public static CallBackResult exception() {
		CallBackResult res = new CallBackResult();
		res.setCode(CODE_EXCEPTION);
		return res;
	}
	
	/**
	 * 操作结果出现可预料的异常
	 * @return
	 */
	public static CallBackResult exception(String msg) {
		CallBackResult res = new CallBackResult();
		res.setMsg(msg);
		res.setCode(CODE_EXCEPTION);
		return res;
	}
	
	/**
	 * 操作过期(登录已经过期)
	 * @return
	 */
	public static CallBackResult expired() {
		CallBackResult res = new CallBackResult();
		res.setCode(CODE_EXPIRED);
		return res;
	}
	
	/**
	 * 操作过期(登录已经过期)
	 * @return
	 */
	public static CallBackResult expired(String msg) {
		CallBackResult res = new CallBackResult();
		res.setMsg(msg);
		res.setCode(CODE_EXPIRED);
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
