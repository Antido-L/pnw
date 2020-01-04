package cn.antido.node.service.thread;

import java.io.IOException;

import cn.antido.node.service.socket.SocketService;

/**
 * @Description socket指令消息接收线程
 * @author Antido
 * @date 2018年6月19日 上午10:37:47
 */
public class SocketServiceThread implements Runnable{
	
	private final SocketService socketService;
	
	public SocketServiceThread(SocketService socketService) {
		this.socketService = socketService;
	}

	/**
	 * 初始化,启动socket服务
	 */
	@Override
	public void run() {
		try {
			socketService.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
