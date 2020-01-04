package cn.antido.search.service.message;


import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import cn.antido.admin.pojo.Park;
import cn.antido.search.mapper.ParkMapper;
import cn.antido.search.solr.ParkSolrDao;

/**
 * @Description 停车场停车场基本信息变更消息监听器
 * @author Antido
 * @date 2018年3月8日 下午3:37:28
 */
public class ParkUpdateListener implements MessageListener {
	
	@Autowired
	private ParkSolrDao parkSolrDao;
	
	@Autowired
	private ParkMapper parkMapper;

	/**
	 * 消息事件<br>
	 * message：需要更新的park的Id<br>
	 * 查询数据库,获取最新的park用于更新索引库
	 */
	@Override
	public void onMessage(Message message) {
		try {
			//取出消息
			TextMessage textMessage = (TextMessage) message;
			String msg = textMessage.getText();
			Long parkId = new Long(msg);
			
			Thread.sleep(1000); //等待数据库新增park事物提交
			
			Park park = parkMapper.selectByParkId(parkId);
			if(park != null) {
				parkSolrDao.updatePark(park);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
