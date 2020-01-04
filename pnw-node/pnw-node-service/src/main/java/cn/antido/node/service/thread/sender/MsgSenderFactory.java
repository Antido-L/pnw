package cn.antido.node.service.thread.sender;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import cn.antido.node.service.thread.Pulse;


/**
 * @Description 与服务器发送http消息的对象制造工厂<br>
 * 通过类初始化CloseableHttpClient,通过构造函数分发给需要的sender,此对象应由spring管理保持单例<br>
 * @author Antido
 * @date 2018年3月21日 下午2:24:25
 */
public class MsgSenderFactory {
	
	/**
	 * HTTP客户端
	 */
	protected final CloseableHttpClient client;
	protected final String URL;
	protected final String controllerId;
	
	public MsgSenderFactory(String address, String controllerId) {
		System.out.println("MsgSenderFactory被实例化");
		this.client = HttpClients.createDefault();
		this.URL = address;
		this.controllerId = controllerId;
	}
	
	/**
	 * 新建新增节点消息发送者
	 */
	public MsgSender newNodeMsgSender(String nodeId, String desc) {
		return new NewNodeMsgSender(client, URL, controllerId, nodeId, desc);
	}
	
	/**
	 * 新建节点接近检测消息发送者
	 */
	public MsgSender closeMsgSender(String nodeId) {
		return new CLoseMsgSender(client, URL, controllerId, nodeId);
	}
	
	/**
	 * 新建节点离开消息发送者
	 */
	public MsgSender leftMsgSender(String nodeId) {
		return new LeftMsgSender(client, URL, controllerId, nodeId);
	}
	
	/**
	 * 新建节点上线消息发送者
	 */
	public MsgSender onlineMsgSender(String nodeId) {
		return new OnlineMsgSender(client, URL, controllerId, nodeId);
	}
	
	/**
	 * 新建节点离线消息发送者
	 */
	public MsgSender outlineMsgSender(String nodeId) {
		return new OutlineMsgSender(client, URL, controllerId, nodeId);
	}
	
	/**
	 * 新建注册消息发送者
	 */
	public MsgSender regMsgSender(String address) {
		return new RegMsgSender(client, URL, address, controllerId);
	}
	
	/**
	 * 心跳发送线程
	 * @return
	 */
	public MsgSender pulse() {
		return new Pulse(client, URL, controllerId);
	}
	
	public void test(String[] args) {
		//CloseableHttpClient client = HttpClients.createDefault();
		/*
		HttpPost httpPost = new HttpPost(URL+"print");
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
		paramPairs.add(new BasicNameValuePair("username", "admin"));
		paramPairs.add(new BasicNameValuePair("password", "123456"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramPairs, "UTF-8");
		httpPost.setEntity(entity);
		CloseableHttpResponse resp = client.execute(httpPost);
		System.out.println(resp.getStatusLine());
		HttpEntity respEntity = resp.getEntity();
		System.out.println(respEntity.getContentLength());
		System.out.println(EntityUtils.toString(respEntity));
		*/
	}
}
