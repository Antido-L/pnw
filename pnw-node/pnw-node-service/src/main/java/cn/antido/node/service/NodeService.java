package cn.antido.node.service;

import cn.antido.common.CallBackResult;
import cn.antido.node.pojo.Result;

/**
 * @Description 节点操作服务接口
 * @author Antido
 * @date 2018年3月13日 下午9:07:32
 */
public interface NodeService {
	
	/**
	 * 初始化串口,尝试与协调通讯
	 */
	Result init();

	/**
	 * 向串口发送一条数据
	 * @param msg
	 */
	//void sendMsg(String msg);
	
	/**
	 * 对一个节点发送上锁指令
	 */
	CallBackResult lockOn(String nodeId);
	
	/**
	 * 对一个节点发送开锁指令
	 */
	CallBackResult lockOff(String nodeId);
	
	/**
	 * 向节点发送注册信息
	 */
	void regis();
	
	/**
	 * 与服务端的心跳连接
	 * 
	 */
	void pulse();
}
