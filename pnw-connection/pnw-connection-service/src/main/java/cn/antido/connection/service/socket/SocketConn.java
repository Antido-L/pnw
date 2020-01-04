package cn.antido.connection.service.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


import cn.antido.common.CallBackResult;
import cn.antido.common.pojo.ConnCommand;
import cn.antido.common.utils.JsonUtils;
import cn.antido.connection.pojo.ConnResult;
import cn.antido.connection.pojo.OrderInfoDTO;

/**
 * @Description 与控制器socket通讯工具类 
 * @author Antido
 * @date 2018年5月25日 下午7:47:38
 */
public class SocketConn {
	
	/**
	 * 新建socketClient向目标发送消息 <br>
	 * 将指令对象转换为json字符串发送至service
	 */
	public CallBackResult client(String address, int port, ConnCommand command) {
		try {
			Socket socket = new Socket(address, port);
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			byte[] dataLen = new byte[2]; //数据长度
			byte[] data = new byte[65535]; //数据缓冲区
			
			String json = JsonUtils.obj2Json(command);
			
			byte[] msg = (json).getBytes("UTF-8");
			//将数据长度转换为byte型
			intToByteArr(msg.length, dataLen); 
			//System.out.println("json:" + json);
			//System.out.println("json.lenght:" + msg.length);
			//先发送数据长度 , 在发送序列化之后的Order对象
			out.write(dataLen);
			out.flush(); //将数据从缓存区内刷出去
			out.write(msg);
			out.flush();
			
			//获取控制器响应的数据长度
			in.read(dataLen);
			int len = byteArrayToInt(dataLen);
			in.read(data);
			String rev = new String(data, 0, len, "UTF-8");
			CallBackResult result = JsonUtils.json2Obj(rev, CallBackResult.class);
			
			in.close();
			out.close();
			socket.close();
			return result;
			
		} catch (Exception e) {
			return CallBackResult.build(ConnResult.CODE_EXCEPTION, "", e);
		}
		
		//return ConnResult.OK();
	}
	
	/**
	 * int型转byte数组
	 * @param length
	 * @param dataLen
	 */
	public static void intToByteArr(int length, byte[] dataLen) {
		dataLen[0] = (byte) (length & 0xFF);
		dataLen[1] = (byte) ((length >> 8) & 0xFF);
	}
	
	/**
	 * byte数组转int值
	 * @param revLen
	 * @return
	 */
	public static int byteArrayToInt(byte[] revLen){
	    return revLen[1] << 8 | revLen[0];
	}
}
