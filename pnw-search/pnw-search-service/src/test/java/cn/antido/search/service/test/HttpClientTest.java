package cn.antido.search.service.test;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class HttpClientTest {
	public static void main(String[] args) throws Exception, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		
		for (int i = 0; i < 10; i++) {
			T1 t = new T1(client);
			new Thread(t).start();
		}
		/*for (int i = 0; i < 10; i++) {
			T2 t = new T2();
			new Thread(t).start();
		}*/
		
	}
}
class T1 implements Runnable {
	
	private CloseableHttpClient client ;
	public T1(CloseableHttpClient client) {
		this.client = client;
	}
	
	@Override
	public void run() {
		try {
			//this.client = client;
			CloseableHttpClient client = HttpClients.createDefault();
			System.out.println(client);
			//HttpGet httpGet = new HttpGet("http://localhost:8083/delayPrint");
			HttpGet httpGet = new HttpGet("http://localhost:8083/delayPrint");
			CloseableHttpResponse resp = client.execute(httpGet);
			System.out.println(resp.getStatusLine());
			//System.out.println(EntityUtils.toString(resp.getEntity()));
			//resp.close();
		} 
			
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

class T2 implements Runnable {
	
	@Override
	public void run() {
		try {
			//this.client = client;
			CloseableHttpClient client = HttpClients.createDefault();
			System.out.println(client);
			//HttpGet httpGet = new HttpGet("http://localhost:8083/delayPrint");
			HttpGet httpGet = new HttpGet("http://localhost:8083");
			CloseableHttpResponse resp = client.execute(httpGet);
			System.out.println(resp.getStatusLine());
			System.out.println(EntityUtils.toString(resp.getEntity()));
			resp.close();
		} 
			
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
