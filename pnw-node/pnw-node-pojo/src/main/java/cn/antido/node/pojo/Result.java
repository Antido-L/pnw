package cn.antido.node.pojo;

import java.io.Serializable;

/**
 * @Description 节点指令执行返回对象,用于向服务器反馈操作结果<br>
 * 0-正常,1-出现异常,2-错误
 * @author Antido
 * @date 2018年3月19日 下午4:55:54
 */
public class Result implements Serializable {
	private Integer code; //状态码
	private String msg; //消息
	private Object data; //数据
	
	public Result() {
		
	}
	
	public Result(Integer code) {
		this.code = code;
	}
	
	public static Result build(Integer code, String msg, Object data) {
		Result res = new Result();
		res.setCode(code);
		res.setMsg(msg);
		res.setData(data);
		return res;
	}

	public static Result OK() {
		return new Result(0);
	}
	
	public static Result ERROR(String msg, Object data) {
		Result res = new Result(2);
		res.setMsg(msg);
		res.setData(data);
		return res;
	}
	
	public static Result ERROR(String msg) {
		Result res = new Result(2);
		res.setMsg(msg);
		return res;
	}
	
	public static Result EXCEPTION(String msg, Object data) {
		Result res = new Result(2);
		res.setMsg(msg);
		res.setData(data);
		return res;
	}
	
	public static Result EXCEPTION(String msg) {
		Result res = new Result(2);
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
