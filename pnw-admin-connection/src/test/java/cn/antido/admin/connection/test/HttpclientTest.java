package cn.antido.admin.connection.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpclientTest {
	public static void main(String[] args){
		//getTest();
		//method1();
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet("http://192.168.161.128:90");
		CloseableHttpResponse res = null;
		try {
			res = client.execute(get);
			StatusLine line = res.getStatusLine();
			int statusCode = line.getStatusCode();
			System.out.println(statusCode);
			System.out.println(line);
			System.out.println(EntityUtils.toString(res.getEntity()));
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	private static void method1() throws UnsupportedEncodingException, IOException, ClientProtocolException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://127.0.0.1:8084/print");
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
	}
	private static void getTest() throws IOException, ClientProtocolException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://www.baidu.com");
		CloseableHttpResponse res = client.execute(httpGet);
		String s = EntityUtils.toString(res.getEntity());
		System.out.println(s);
		System.out.println(s.length());
		StatusLine statusLine = res.getStatusLine();
		System.out.println(statusLine);
	}
}
