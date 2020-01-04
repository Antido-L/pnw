package cn.antido.admin.service.sender;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import cn.antido.admin.pojo.ConnCallBack;

/**
 * @Description 消息发送对象工具类,用于向直接向控制端发送消息,调用时会发生阻塞.
 * @author Antido
 * @date 2018年4月8日 下午4:52:59
 */
public class MsgSender {
	private final String LOCK_ON;
	private final String LOCK_OFF;
	
	private final CloseableHttpClient client;
	
	public MsgSender(String LOCK_ON, String LOCK_OFF) {
		this.LOCK_ON = LOCK_ON;
		this.LOCK_OFF = LOCK_OFF;
		//创建HttpClient
		this.client = HttpClients.createDefault();
	}
	
	/**
	 * 向控制端发送上锁消息,如有异常响应返回ConnCallBack.EXCEPTION
	 */
	public ConnCallBack lockOn(String address, String nodeId) {
		System.out.println(client);
		HttpPost httpPost = new HttpPost("http://"+address+LOCK_ON);
		
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>(1);
		paramPairs.add(new BasicNameValuePair("nodeId", nodeId));
		
		//发送请求 处理异常
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramPairs, "UTF-8");
			httpPost.setEntity(entity);
			CloseableHttpResponse resp = client.execute(httpPost);
			
			int statusCode = resp.getStatusLine().getStatusCode();
			//取出响应,释放连接
			resp.getEntity();
			resp.close();
			if(statusCode != 200) { //同样当做异常处理
				return ConnCallBack.EXCEPTION("控制端异常");
			}
		} catch (Exception e) {
			System.out.println("******************************");
			System.out.println("MsgSenderFactory-->lockOn出现异常");
			System.out.println("******************************");
			System.err.println(e);
			return ConnCallBack.EXCEPTION("控制端异常");
		}
		return ConnCallBack.OK();
	}
	
	/**
	 * 向控制端发送解锁锁消息,如有异常响应返回ConnCallBack.EXCEPTION
	 */
	public ConnCallBack lockOff(String address, String nodeId) {
		System.out.println(client);
		HttpPost httpPost = new HttpPost("http://"+address+LOCK_OFF);
		
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>(1);
		paramPairs.add(new BasicNameValuePair("nodeId", nodeId));
		
		//发送请求 处理异常
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramPairs, "UTF-8");
			httpPost.setEntity(entity);
			CloseableHttpResponse resp = client.execute(httpPost);
			
			int statusCode = resp.getStatusLine().getStatusCode();
			//取出响应,释放连接
			resp.getEntity();
			resp.close();
			if(statusCode != 200) { //同样当做异常处理
				return ConnCallBack.EXCEPTION("控制端异常");
			}
		} catch (Exception e) {
			System.out.println("******************************");
			System.out.println("MsgSenderFactory-->lockOff出现异常");
			System.out.println("******************************");
			System.err.println(e );
			return ConnCallBack.EXCEPTION("控制端异常");
		}
		return ConnCallBack.OK();
	}
	
}
