package cn.antido.search.service.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import cn.antido.search.solr.ParkSolrDao;


/**
 * @Description 将指定park的剩余车位数减1
 * @author Antido
 * @date 2018年4月8日 下午7:08:32
 */
public class SubRemainListener implements MessageListener {
	
	@Autowired
	private ParkSolrDao parkSolrDao;

	@Override
	public void onMessage(Message message) {
		try {
			//取出消息
			TextMessage textMessage = (TextMessage) message;
			String msg = textMessage.getText();
			Long parkId = new Long(msg);
			
			parkSolrDao.subRemain(parkId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
