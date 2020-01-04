package cn.antido.node.service.thread;

import java.io.IOException;

import cn.antido.node.service.servial.SerialTools;

/**
 * @Description 由于串口性能原因(具体解决需要从zigbee入手),在发送数据时不能保证节点能够100%正确收到<br>
 * 间隔1s重复发送信息,以保证节点接收正确
 * @author Antido
 * @date 2018年3月19日 下午5:42:23
 */
public class ReSendMsg extends Thread{
	private SerialTools serialTools;
	private String msg;
	
	public ReSendMsg(SerialTools serialTools, String msg) {
		this.serialTools = serialTools;
		this.msg = msg;
	}
	
	@Override
	public void run() {
		try {
			for (int i = 0; i < 2; i++) {
				Thread.sleep(1000);
				serialTools.sendMsg(msg);
			}
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
			
	}

}
