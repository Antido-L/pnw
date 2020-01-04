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
 * @Description 注册消息发送
 * @author Antido
 * @date 2018年3月26日 上午10:16:08
 */
public class RegMsgSender extends MsgSender {
	
	private final CloseableHttpClient client;
	private final String URL;
	private final String address;
	private final String controllerId;

	public RegMsgSender(CloseableHttpClient client, String URL,String address, String controllerId) {
		this.client = client;
		this.URL = URL;
		this.address = address;
		this.controllerId = controllerId;
	}
	
	@Override
	public void run() {
		try {
			HttpPost httpPost = new HttpPost(URL+"regis");
			List<NameValuePair> paramPairs = new ArrayList<NameValuePair>(2);
			paramPairs.add(new BasicNameValuePair("address", address));
			paramPairs.add(new BasicNameValuePair("node_id", controllerId));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramPairs, "UTF-8");
			httpPost.setEntity(entity);
			
			responseLogger(client.execute(httpPost));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
