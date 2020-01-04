package cn.antido.node.service.thread.sender;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

/**
 * @Description 发送者接口 先放着 以后再说
 * @author Antido
 * @date 2018年3月21日 下午2:35:35
 */
public abstract class MsgSender implements Runnable {
	
	/**
	 * 打印日志
	 */
	protected void responseLogger(CloseableHttpResponse resp){
		try {
			//时间
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			String data = formatter.format(new Date());  
			//状态码
			StatusLine statusLine = resp.getStatusLine();
			//响应体
			HttpEntity respEntity = resp.getEntity();
			
			System.out.println(data+"-->"+statusLine+" callback:"+EntityUtils.toString(respEntity));
			
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	};
	
}
