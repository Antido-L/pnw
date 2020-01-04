package cn.antido.admin.service.thread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

import cn.antido.admin.mapper.NodeAddressMapper;
import cn.antido.admin.mapper.NodeMapper;
import cn.antido.admin.pojo.NodeAddress;
import cn.antido.common.redis.JedisClient;

/**
 * @Description 向目标控制器发送消息,确认连接情况<br>
 * 当连接出现异常信息或者响应状态码不等于200时,判定目标为断线<br>
 * 由PulseOutTimeListener启动该线程
 * @author Antido
 * @date 2018年3月27日 上午11:08:14
 */
public class PulseComfirm implements Runnable{
	
	private final NodeAddressMapper nodeAddressMapper;
	private final CloseableHttpClient client;
	private final JedisClient jedisClient;
	private final NodeMapper nodeMapper;
	
	private String confirmPath;
	private Set<String> ids; //需要确认的协调ID集合
	private String key;
	
	public PulseComfirm(NodeAddressMapper nodeAddressMapper, CloseableHttpClient client,  
		JedisClient jedisClient, NodeMapper nodeMapper) {
		this.nodeAddressMapper = nodeAddressMapper;
		this.client = client;
		this.jedisClient = jedisClient;
		this.nodeMapper = nodeMapper;
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		//遍历所有ID
		for (String id : ids) {
			NodeAddress nodeAddress = nodeAddressMapper.selectById(id);
			if(nodeAddress == null) continue; //正常不应该==null
			//发送post请求到目标服务器
			System.out.println("PulseComfirm:"+"http://"+nodeAddress.getAddress()+confirmPath);
			
			HttpPost httpPost = new HttpPost("http://"+nodeAddress.getAddress()+confirmPath);
			
			List<NameValuePair> paramPairs = new ArrayList<NameValuePair>(1);
			paramPairs.add(new BasicNameValuePair("msg", "are you alive?"));
			//发送请求 处理异常
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramPairs, "UTF-8");
				httpPost.setEntity(entity);
				CloseableHttpResponse resp = client.execute(httpPost);
				
				int statusCode = resp.getStatusLine().getStatusCode();
				//取出响应,释放连接
				resp.getEntity();
				
				if(statusCode != 200) { //同样当做异常处理
					connExceptionHandle(nodeAddress);
				}
				
			} catch (Exception e) {
				System.out.println(e);
				connExceptionHandle(nodeAddress);
			}
		}
		
	}
	
	/**
	 * 处理节点离线异常<br>
	 * TODO: 突然间与节点控制器端失去心跳连接是一件很严重的事,应该有自我保护与协调功能<br>
	 * 节点的下线应为可控的
	 * @param nodeAddress
	 */
	private void connExceptionHandle(NodeAddress nodeAddress) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String data = formatter.format(new Date()); 
		System.out.println(data + nodeAddress.getNode_id() + "离线异常");
		
		//清除在redis中的心跳数据
		jedisClient.zrem(key, nodeAddress.getNode_id());
		//将地址对照表改为异常状态
		nodeAddress.setState((byte)2);
		nodeAddressMapper.updateState(nodeAddress);
		//下线当前所有节点,下线所有该协调器下的节点
		nodeMapper.outlineByParent(nodeAddress.getNode_id());
		//TODO: 停车场异常下线后的处理
		
		//TODO:给管理员发送消息
		
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}


	/**
	 * @param confirmPath the confirmPath to set
	 */
	public void setConfirmPath(String confirmPath) {
		this.confirmPath = confirmPath;
	}


	/**
	 * @param ids the ids to set
	 */
	public void setIds(Set<String> ids) {
		this.ids = ids;
	}

	
}
