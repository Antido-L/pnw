package cn.antido.node.service.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HTTPClientTest {
	public static void main(String[] args) {
		
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost("https://www.baidu.com");
			List<NameValuePair> paramPairs = new ArrayList<NameValuePair>(1);
			paramPairs.add(new BasicNameValuePair("id", "123"));
			UrlEncodedFormEntity entity;
			entity = new UrlEncodedFormEntity(paramPairs, "UTF-8");
			httpPost.setEntity(entity);
			CloseableHttpResponse resp = client.execute(httpPost);
			//时间
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			String data = formatter.format(new Date());  
			System.out.println(data);
			//状态码
			StatusLine statusLine = resp.getStatusLine();
			System.out.println(statusLine);
			//响应体
			HttpEntity respEntity = resp.getEntity();
			
			System.out.println(data+"-->"+statusLine+" callback:"+EntityUtils.toString(respEntity));
			
		} catch (Exception e) {
			System.out.println(e);
			
		}
		System.out.println("finish");
		
	}

	private static void threadTest(CloseableHttpClient client) {
		for (int i = 0; i < 3; i++) {
			//http(client);
			if(i%2 == 0) {
				Th th = new Th(client);
				Thread thread = new Thread(th);
				thread.start();
			}
		}
	}

	private static void http(CloseableHttpClient client)
			throws UnsupportedEncodingException, IOException, ClientProtocolException {
		HttpPost httpPost = new HttpPost("http://127.0.0.1:8084/print");
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>(1);
		paramPairs.add(new BasicNameValuePair("id", "123"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramPairs, "UTF-8");
		httpPost.setEntity(entity);
		CloseableHttpResponse resp = client.execute(httpPost);
		
		//时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String data = formatter.format(new Date());  
		//状态码
		StatusLine statusLine = resp.getStatusLine();
		
		//响应体
		HttpEntity respEntity = resp.getEntity();
		
		System.out.println(data+"-->"+statusLine+" callback:"+EntityUtils.toString(respEntity));
	}
	
	static class Th implements Runnable {
		
		private CloseableHttpClient client;
		
		public Th(CloseableHttpClient client) {
			this.client = client;
		}

		@Override
		public void run() {
			try {
				System.out.println("Th-->"+Thread.currentThread().getName());
				
				HttpPost httpPost = new HttpPost("http://127.0.0.1:8084/print");
				List<NameValuePair> paramPairs = new ArrayList<NameValuePair>(1);
				paramPairs.add(new BasicNameValuePair("id", "123"));
				UrlEncodedFormEntity entity;
				entity = new UrlEncodedFormEntity(paramPairs, "UTF-8");
				httpPost.setEntity(entity);
				CloseableHttpResponse resp = client.execute(httpPost);
				
				//时间
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				String data = formatter.format(new Date());  
				//状态码
				StatusLine statusLine = resp.getStatusLine();
				//响应体
				HttpEntity respEntity = resp.getEntity();
				/*
				System.out.println(data+"-->"+statusLine+" callback:"+EntityUtils.toString(respEntity));
				*/
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	static class Th1 implements Runnable {
		
		private CloseableHttpClient client;
		
		public Th1(CloseableHttpClient client) {
			this.client = client;
		}

		@Override
		public void run() {
			try {
				System.out.println("Th1-->"+Thread.currentThread().getName());
				
				HttpPost httpPost = new HttpPost("http://127.0.0.1:8084/print");
				List<NameValuePair> paramPairs = new ArrayList<NameValuePair>(1);
				paramPairs.add(new BasicNameValuePair("id", "123"));
				UrlEncodedFormEntity entity;
				entity = new UrlEncodedFormEntity(paramPairs, "UTF-8");
				httpPost.setEntity(entity);
				CloseableHttpResponse resp = client.execute(httpPost);
				
				//时间
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				String data = formatter.format(new Date());  
				//状态码
				StatusLine statusLine = resp.getStatusLine();
				
				//响应体
				HttpEntity respEntity = resp.getEntity();
				
				System.out.println(data+"-->"+statusLine+" callback:"+EntityUtils.toString(respEntity));
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
