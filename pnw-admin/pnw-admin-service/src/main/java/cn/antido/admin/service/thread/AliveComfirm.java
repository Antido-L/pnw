package cn.antido.admin.service.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import cn.antido.admin.mapper.NodeAddressMapper;
import cn.antido.admin.mapper.NodeMapper;
import cn.antido.admin.pojo.NodeAddress;
import cn.antido.common.CallBackResult;
import cn.antido.common.pojo.ConnCommand;
import cn.antido.common.redis.JedisClient;
import cn.antido.common.utils.CodeUtils;
import cn.antido.common.utils.JsonUtils;

/**
 * @Description 目标存活确认线程<br>
 * @author Antido
 * @date 2018年6月19日 下午2:40:26
 */
public class AliveComfirm implements Runnable {
	
	private NodeAddressMapper nodeAddressMapper;
	private NodeMapper nodeMapper;
	private JedisClient jedisClient;
	
	private Set<String> ids; //需要确认的协调ID集合
	private String key; //在jedis中目标协调器存放的key
 
	public AliveComfirm(NodeAddressMapper nodeAddressMapper, NodeMapper nodeMapper, JedisClient jedisClient) {
		this.nodeAddressMapper = nodeAddressMapper;
		this.nodeMapper = nodeMapper;
		this.jedisClient = jedisClient;
	}

	/**
	 * 遍历所有可能已经离线的协调器id,从数据库中获取目标协调器消息<br>
	 * 向协调器发送socket存活确认消息
	 */
	@Override
	public void run() {
		for (String id : ids) {
			if(id == null) continue;
			NodeAddress nodeAddress = nodeAddressMapper.selectById(id);
			sockComfirm(nodeAddress);
		}
	}
	
	/**
	 * 新建socketClient向目标协调器发送存活确认消息
	 * @param address
	 * @param port
	 * @param conn_key
	 */
	private void sockComfirm(NodeAddress nodeAddress) {
		String address = nodeAddress.getAddress();
		Integer port = nodeAddress.getPort();
		String conn_key = nodeAddress.getConn_key();
		
		//新建socketClient
		try {
			Socket socket = new Socket(address, port);
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			byte[] dataLen = new byte[2]; //数据长度
			byte[] data = new byte[65535]; //数据缓冲区
			
			//创建心跳指令对象
			ConnCommand command = new ConnCommand();
			command.setOrder(ConnCommand.ORDER_ALIVE); //指令
			String connCode = CodeUtils.getConnCode(conn_key)[0];
			command.setCode(connCode); //校验码
			
			String json = JsonUtils.obj2Json(command);
			
			byte[] msg = (json).getBytes("UTF-8");
			//将数据长度转换为byte型
			intToByteArr(msg.length, dataLen); 
			
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
			
			//存活确认失败
			if(result.getCode() != CallBackResult.CODE_OK) {
				aliveExceptionHandler(nodeAddress);
			}
		} catch (IOException e) {
			//当出现异常时 认为目标协调器出现异常
			System.err.println(address + "存活确认异常:" + e);
			aliveExceptionHandler(nodeAddress);
		}
		
	}
	
	/**
	 * 目标失活处理<br>
	 * 这种事情一般不会发生
	 * @param nodeAddress
	 */
	private void aliveExceptionHandler(NodeAddress nodeAddress) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String data = formatter.format(new Date()); 
		System.out.println(data + nodeAddress.getNode_id() + "离线异常");
		
		//清除在redis中的心跳数据
		jedisClient.zrem(key, nodeAddress.getNode_id());
		//将地址对照表改为异常状态
		nodeAddress.setState((byte)2);
		nodeAddressMapper.updateState(nodeAddress);
		//下线当前所有节点,下线所有该协调器下的节点
		nodeMapper.outlineByParent(nodeAddress.getNode_id());
		//TODO: 停车场异常下线后的处理
		
		//TODO:给管理员发送消息
		
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

	

	/**
	 * @return the ids
	 */
	public Set<String> getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public void setIds(Set<String> ids) {
		this.ids = ids;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	

}
