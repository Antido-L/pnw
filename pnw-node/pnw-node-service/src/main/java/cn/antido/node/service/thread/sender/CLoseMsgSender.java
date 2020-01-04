package cn.antido.node.service.thread.sender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * @Description 节点靠近消息发送者
 * @author Antido
 * @date 2018年3月20日 下午9:16:55
 */
public class CLoseMsgSender extends MsgSender {
	
	private final CloseableHttpClient client;
	private final String URL;
	private final String controllerId;
	private final String nodeId;
	
	
	public CLoseMsgSender(CloseableHttpClient client, String URL, String controllerId, String nodeId) {
		this.client = client;
		this.URL = URL;
		this.nodeId = nodeId;
		this.controllerId = controllerId; 
	}
	
	/**
	 * 将节点ID以POST方式发送给服务器
	 */
	@Override
	public void run() {
		try {
			HttpPost httpPost = new HttpPost(URL+"closed");
			List<NameValuePair> paramPairs = new ArrayList<NameValuePair>(2);
			paramPairs.add(new BasicNameValuePair("id", nodeId));
			paramPairs.add(new BasicNameValuePair("parent_id", controllerId));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramPairs, "UTF-8");
			httpPost.setEntity(entity);
			
			CloseableHttpResponse response = client.execute(httpPost);
			
			//调用父类方法打印响应
			responseLogger(response);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
