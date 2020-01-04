package cn.antido.search.service.message;


import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import cn.antido.admin.pojo.Park;
import cn.antido.search.mapper.ParkMapper;
import cn.antido.search.solr.ParkSolrDao;

/**
 * @Description 新增停车场消息监听器，用于更新索引库信息
 * @author Antido
 * @date:2018年2月7日 下午5:31:16
 */
public class ParkAddListener implements MessageListener {
	
	@Autowired
	private ParkMapper parkMapper;
	
	@Autowired
	private ParkSolrDao parkSolrDao;

	/**
	 * 消息事件<br>
	 * message：新增park的Id<br>
	 * 根据ID获取park数据更新库
	 * 索引库的新增和更新操作相同，该方法同时处理后台对停车场信息的新增和更新
	 * 
	 */
	@Override
	public void onMessage(Message message) {
		try {
			//取出消息
			TextMessage textMessage = (TextMessage) message;
			String msg = textMessage.getText();
			Long parkId = new Long(msg);
			//获取新增的park
			
			Thread.sleep(1000); //等待数据库新增park事物提交
			
			Park park = parkMapper.selectByParkId(parkId);
			if(park != null) {
				parkSolrDao.addPark(park);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
