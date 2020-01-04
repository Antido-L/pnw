package cn.antido.search.service.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @Description 一个消息监听器，测试spring与activeMQ整合<br>
 * 队列名：queueDestination
 * @author Antido
 * @date:2018年2月7日 下午4:29:04
 */
public class MyMessageListener implements MessageListener {
	
	/**
	 * 消息事件
	 */
	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			//取出消息
			String msg = textMessage.getText();
			System.out.println(msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
