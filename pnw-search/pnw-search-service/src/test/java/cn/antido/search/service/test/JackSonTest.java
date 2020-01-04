package cn.antido.search.service.test;

import java.io.IOException;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.antido.common.pojo.BaiduPosition;

public class JackSonTest {
	public static void main(String[] args) throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://api.map.baidu.com/location/ip?ip=61.189.48.164&ak=yI43GzysGKGcADFGUo6etBG6kMbmbO7I&coor=bd09ll");
		//HttpGet httpGet = new HttpGet("https://www.baidu.com/");
        //设置期望服务端返回的编码
        //httpGet.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
        
        CloseableHttpResponse resp = client.execute(httpGet);
        StatusLine responseCode = resp.getStatusLine();
        
        System.out.println(responseCode.getStatusCode());
        
        
        System.out.println(responseCode);
        String result = EntityUtils.toString(resp.getEntity());
        System.out.println(result);
        client.close();
        
        ObjectMapper mapper = new ObjectMapper();
        BaiduPosition baiduPosition = mapper.readValue(result, BaiduPosition.class);
        System.out.println(baiduPosition);
        System.out.println(baiduPosition.getContent());
        System.out.println(baiduPosition.getContent().getAddress_detail());
        
	}
}
