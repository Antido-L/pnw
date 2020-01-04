package cn.antido.node.service.thread;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import cn.antido.node.service.thread.sender.MsgSender;

/**
 * @Description 与服务端的心跳连接线程<br>
 * 定时向服务器发送消息
 * @author Antido
 * @date 2018年3月26日 下午5:15:55
 */
public class Pulse extends MsgSender {
	
	private final CloseableHttpClient client;
	private final String targetURL;
	private final String msg;
	private final Long time = 60000L;

	public Pulse(CloseableHttpClient client, String targetURL, String msg) {
		this.client = client;
		this.targetURL = targetURL;
		this.msg = msg;
	}
	
	/**
	 * 每隔一端时间向服务端发送心跳
	 */
	@Override
	public void run() {
		System.out.println("Pulse线程已启动");
		for(;;) {
			try {
				System.out.println("Pulse发送");
				HttpPost httpPost = new HttpPost(targetURL+"pulse");
				List<NameValuePair> paramPairs = new ArrayList<NameValuePair>(1);
				paramPairs.add(new BasicNameValuePair("id", msg));
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramPairs, "UTF-8");
				httpPost.setEntity(entity);
				
				CloseableHttpResponse resp = client.execute(httpPost);
				
				//调用父类方法打印响应
				responseLogger(resp);
				
				Thread.sleep(time);
				
			} catch (Exception e) {
				//e.printStackTrace();
				System.out.println(e);
				System.out.println("Pulse线程异常");
			} 
		}
		
	}

}
