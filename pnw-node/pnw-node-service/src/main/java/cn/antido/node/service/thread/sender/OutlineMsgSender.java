package cn.antido.node.service.thread.sender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * @Description 节点上线消息
 * @author Antido
 * @date 2018年3月20日 下午9:16:55
 */
public class OutlineMsgSender extends MsgSender {
	
	private final CloseableHttpClient client;
	private final String URL;
	private final String controllerId;
	private final String nodeId;
	
	public OutlineMsgSender(CloseableHttpClient client, String URL, String controllerId, String nodeId) {
		this.client = client;
		this.URL = URL;
		this.controllerId = controllerId;
		this.nodeId = nodeId;
	}
	
	@Override
	public void run() {
		try {
			HttpPost httpPost = new HttpPost(URL+"outline");
			List<NameValuePair> paramPairs = new ArrayList<NameValuePair>(2);
			paramPairs.add(new BasicNameValuePair("id", nodeId));
			paramPairs.add(new BasicNameValuePair("parnet_id", controllerId));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramPairs, "UTF-8");
			httpPost.setEntity(entity);
			
			responseLogger(client.execute(httpPost));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
