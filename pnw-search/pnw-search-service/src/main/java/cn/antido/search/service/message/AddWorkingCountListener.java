package cn.antido.search.service.message;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import cn.antido.search.solr.ParkSolrDao;


/**
 * @Description 将指定park的正常工作车位数加1
 * @author Antido
 * @date 2018年7月25日 下午2:34:38
 */
public class AddWorkingCountListener implements MessageListener {
	
	@Autowired
	private ParkSolrDao parkSolrDao;

	@Override
	public void onMessage(Message message) {
		try {
			//取出消息
			TextMessage textMessage = (TextMessage) message;
			String msg = textMessage.getText();
			Long parkId = new Long(msg);
			
			parkSolrDao.addWorkingCount(parkId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
