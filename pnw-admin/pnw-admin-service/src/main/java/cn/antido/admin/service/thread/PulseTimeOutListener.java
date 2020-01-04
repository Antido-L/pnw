package cn.antido.admin.service.thread;

import java.util.Set;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import cn.antido.admin.mapper.NodeAddressMapper;
import cn.antido.admin.mapper.NodeMapper;
import cn.antido.common.redis.JedisClient;

/**
 * @Description 心跳超时监听器<br>
 * 定时查询redis中的心跳有序队列,获取可能已经超时的成员<br>
 * 对超时的成员执行心跳确认程序<br>
 * @author Antido
 * @date 2018年3月26日 下午9:23:54
 */
public class PulseTimeOutListener implements Runnable{
	
	private final JedisClient jedisClient;
	private final String key;	
	private final Long timeOut;
	private final Long sleepTime;
	
	private NodeAddressMapper nodeAddressMapper;
	private NodeMapper nodeMapper;
	private String confirmPath;
	
	public PulseTimeOutListener(JedisClient jedisClient, String key, Long timeOut, Long sleepTime) {
		this.jedisClient = jedisClient;
		this.key = key;
		this.timeOut = timeOut;
		this.sleepTime = sleepTime;
	}
	
	/**
	 * 获取当前时间戳,时间戳 - 超时时间<br>
	 * 时间戳 - 超时时间 范围内的成员可能已经断线
	 */
	@Override
	public void run() {
		//创建一个http客户端,将该客户端传给离线确认线程
		//CloseableHttpClient client = HttpClients.createDefault();
		System.out.println("PulseTimeOutListener启动");
		while(true) {
			try {
				//System.out.println("PulseTimeOutListener...");
				//根据当前时间戳与超时时间做差 获取在超时时间范围的内的成员 可认为是已超时
				long currentTime = System.currentTimeMillis();
				Set<String> ids = jedisClient.zrangeByScore(key, 0L, currentTime - timeOut);
				
				if(!ids.isEmpty()) { //发现有可能已经断线的连接
					/*
					PulseComfirm pulseComfirm = new PulseComfirm(nodeAddressMapper, client, jedisClient, nodeMapper);
					pulseComfirm.setConfirmPath(confirmPath);
					pulseComfirm.setKey(key);
					pulseComfirm.setIds(ids);
					
					//执行确认线程
					Thread thread = new Thread(pulseComfirm);
					thread.start();
					*/
					//创建离线确认线程 
					AliveComfirm aliveComfirm = new AliveComfirm(nodeAddressMapper,nodeMapper,jedisClient);
					aliveComfirm.setIds(ids);
					aliveComfirm.setKey(key);
					new Thread(aliveComfirm).start();;
					
				}
				Thread.sleep(sleepTime); //监听间隔时间
			} catch (InterruptedException e) {
				//e.printStackTrace();
				System.out.println("******************************");
				System.out.println("PulseTimeOutListener出现异常");
				System.out.println("******************************");
			}
		}
		
	}

	/**
	 * @return the nodeAddressMapper
	 */
	public NodeAddressMapper getNodeAddressMapper() {
		return nodeAddressMapper;
	}

	/**
	 * @param nodeAddressMapper the nodeAddressMapper to set
	 */
	public void setNodeAddressMapper(NodeAddressMapper nodeAddressMapper) {
		this.nodeAddressMapper = nodeAddressMapper;
	}

	/**
	 * @return the confirmPath
	 */
	public String getConfirmPath() {
		return confirmPath;
	}

	/**
	 * @param confirmPath the confirmPath to set
	 */
	public void setConfirmPath(String confirmPath) {
		this.confirmPath = confirmPath;
	}

	/**
	 * @return the nodeMapper
	 */
	public NodeMapper getNodeMapper() {
		return nodeMapper;
	}

	/**
	 * @param nodeMapper the nodeMapper to set
	 */
	public void setNodeMapper(NodeMapper nodeMapper) {
		this.nodeMapper = nodeMapper;
	}
	
	

}
