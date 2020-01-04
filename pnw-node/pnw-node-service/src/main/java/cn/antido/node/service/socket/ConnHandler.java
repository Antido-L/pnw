package cn.antido.node.service.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.antido.common.CallBackResult;
import cn.antido.common.pojo.ConnCommand;
import cn.antido.common.utils.CodeUtils;
import cn.antido.common.utils.JsonUtils;
import cn.antido.connection.pojo.OrderInfoDTO;
import cn.antido.node.pojo.Result;
import cn.antido.node.service.impl.NodeServiceImpl;

/**
 * @Description socket连接处理器
 * @author Antido
 * @date 2018年5月25日 下午4:03:07
 */
public class ConnHandler implements Runnable {
	
	private Socket socket;
	private NodeServiceImpl nodeServiceImpl;
	private String commandKey;
	
	public ConnHandler(Socket socket ,NodeServiceImpl nodeServiceImpl, String commandKey) {
		this.socket = socket;
		this.nodeServiceImpl = nodeServiceImpl;
		this.commandKey = commandKey;
	}
	
	/**
	 * 处理一条连接
	 */
	@Override
	public void run() {
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:MM:ss");
		String date = format.format(new Date());
		
		String address = socket.getLocalAddress().toString();
		int port = socket.getPort();
		System.out.println(date + address+ ":" + port + "建立连接");
		
		byte[] dataLen = new byte[2]; //数据长度 最大65535位
		byte[] data = new byte[65535]; //数据缓冲区
		
		try {
			InputStream in= socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			
			//获取数据长度
			in.read(dataLen);
			int len = byteArrayToInt(dataLen);
			
			//获取数据
			in.read(data);
			String revJson = new String(data, 0, len, "UTF-8");
			
			ConnCommand command = JsonUtils.json2Obj(revJson, ConnCommand.class);
			
			CallBackResult result = null;
			//验证校验码
			String[] codes = CodeUtils.getConnCode(commandKey);
			if(command.getCode().equals(codes[0]) || command.getCode().equals(codes[1])) {
				//执行操作
				if(command.getOrder() == ConnCommand.ORDER_LOCK_ON) {
					result = nodeServiceImpl.lockOn(command.getNodeId());	
				} 
				else if(command.getOrder() == ConnCommand.ORDER_LOCK_OFF) {
					result = nodeServiceImpl.lockOff(command.getNodeId());
				} 
				else if(command.getOrder() == ConnCommand.ORDER_ALIVE) {
					result = CallBackResult.ok("I am alive!");
				}
			} else {
				result = CallBackResult.error("校验不正确");
			}
			
			//返回执行结果
			String json = JsonUtils.obj2Json(result);
			byte[] msg = json.getBytes("UTF-8");
			intToByteArr(msg.length , dataLen);
			
			out.write(dataLen);
			out.flush();
			
			out.write(msg);
			out.flush();
			
			//关闭资源
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		date = format.format(new Date());
		System.out.println(date + address+ ":" + port + "已经断开连接");
	}
	
	public static int byteArrayToInt(byte[] revLen){
	    return revLen[1] << 8 | revLen[0];
	}
	
	public static void intToByteArr(int length, byte[] dataLen) {
		dataLen[0] = (byte) (length & 0xFF);
		dataLen[1] = (byte) ((length >> 8) & 0xFF);
	}

}
