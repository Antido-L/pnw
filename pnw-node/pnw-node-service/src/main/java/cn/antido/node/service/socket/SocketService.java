package cn.antido.node.service.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.antido.node.service.impl.NodeServiceImpl;

/**
 * @Description socket服务端<br>
 * 由spring创建,加载时自动执行init方法<br>
 * @author Antido
 * @date 2018年5月25日 下午3:52:10
 */
public class SocketService {
	
	//@Value("${socket.service.port}")
	private int port;
	
	//@Value("${threadPool.corePoolSize}")
	private int corePoolSize;
	
	//@Value("${threadPool.maximumPoolSize}")
	private int maximumPoolSize;
	
	//@Value("${threadPool.keepAliveTime}")
	private int keepAliveTime;
	
	private String commandKey;
	
	/**
	 * 请求处理线程中需要调用此方法
	 */
	//@Autowired
	private NodeServiceImpl nodeServiceImpl;
	
	public SocketService() {
		
	}
	
	/**
	 * spring加载完毕后会自动执行此方法<br>
	 * 建立连接处理线程池,该socket service 会一直执行<br>
	 */
	//@PostConstruct
	public void init() throws IOException {
		//监听指定的端口
	    ServerSocket server = new ServerSocket(port);
	    System.out.println("server已经启动,监听端口号:" + port);
	    
	    //初始化与服务端接受消息线程池
	    //当使用线程数超过最大线程数会抛出异常
	    ThreadPoolExecutor executor = new ThreadPoolExecutor(
    		corePoolSize, 						//corePoolSize线程池中核心线程数
    		maximumPoolSize, 					//maximumPoolSize 线程池中最大线程数 
    		keepAliveTime, 						//keepAliveTime线程池中线程的最大空闲时间
			TimeUnit.MINUTES, 					//时间单位  
			new SynchronousQueue<Runnable>()    //阻塞队列
		);
	    
	    //等待连接
	    while(true){
		    Socket socket = server.accept();
		    ConnHandler handler = new ConnHandler(socket, nodeServiceImpl,this.commandKey);
		    //使用线程池中的线程处理请求
		    executor.execute(handler);
	    }
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the corePoolSize
	 */
	public int getCorePoolSize() {
		return corePoolSize;
	}

	/**
	 * @param corePoolSize the corePoolSize to set
	 */
	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	/**
	 * @return the maximumPoolSize
	 */
	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	/**
	 * @param maximumPoolSize the maximumPoolSize to set
	 */
	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	/**
	 * @return the keepAliveTime
	 */
	public int getKeepAliveTime() {
		return keepAliveTime;
	}

	/**
	 * @param keepAliveTime the keepAliveTime to set
	 */
	public void setKeepAliveTime(int keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	/**
	 * @return the nodeServiceImpl
	 */
	public NodeServiceImpl getNodeServiceImpl() {
		return nodeServiceImpl;
	}

	/**
	 * @param nodeServiceImpl the nodeServiceImpl to set
	 */
	public void setNodeServiceImpl(NodeServiceImpl nodeServiceImpl) {
		this.nodeServiceImpl = nodeServiceImpl;
	}

	/**
	 * @return the commandKey
	 */
	public String getCommandKey() {
		return commandKey;
	}

	/**
	 * @param commandKey the commandKey to set
	 */
	public void setCommandKey(String commandKey) {
		this.commandKey = commandKey;
	}
	
	
	
	
}
