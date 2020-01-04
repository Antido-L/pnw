package cn.antido.search.service.message;


import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import cn.antido.admin.pojo.Park;
import cn.antido.search.mapper.ParkMapper;
import cn.antido.search.solr.ParkSolrDao;

/**
 * @Description 删除停车场消息监听器，用于更新索引库信息
 * @author Antido
 * @date 2018年3月7日 下午8:04:00
 */
public class ParkDelListener implements MessageListener {
	
	@Autowired
	private ParkSolrDao parkSolrDao;

	/**
	 * 消息事件<br>
	 * message：需要删除的park的Id<br>
	 * 根据ID获取删除索引库中的Park
	 */
	@Override
	public void onMessage(Message message) {
		try {
			//取出消息
			TextMessage textMessage = (TextMessage) message;
			String msg = textMessage.getText();
			Long parkId = new Long(msg);
			
			if(parkId != null) {
				parkSolrDao.delPark(parkId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
