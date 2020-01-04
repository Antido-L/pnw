package cn.antido.admin.service;

import java.util.List;

import cn.antido.admin.pojo.ConnCallBack;
import cn.antido.admin.pojo.Node;
import cn.antido.admin.pojo.NodeAddress;

/**
 * @Description 节点服务接口 
 * @author Antido
 * @date:2018年1月20日 下午7:15:41
 */
public interface NodeService {
	
	/**
	 * 根据父id获取所有没有被绑定的节点
	 */
	List<Node> getFreeNodeByParentId(String parentId);
	
	/**
	 * 新增一个节点
	 */
	void insert(Node node);
	
	/**
	 * 将一个节点更新为上线状态
	 */
	void onlineNode(Node node);
	
	/**
	 * 将一个节点更新为下线状态 
	 */
	void outlineNode(Node node);

	/**
	 * 一个节点检测到接近信息
	 */
	void nodeClosed(Node node);
	
	/**
	 * 一个节点检测到离开信息 
	 */
	void nodeLeft(Node node);
	
	/**
	 * 收到控制器注册信息
	 * @param parent_id
	 * @param address
	 */
	void regis(NodeAddress nodeAddress);
	
	/**
	 * 收到协调器心跳
	 */
	void pulse(String id);
	
	/**
	 * 根据目标地址 获取控制器连接状态
	 * @return 
	 */
	ConnCallBack conn(String address);
	
}
