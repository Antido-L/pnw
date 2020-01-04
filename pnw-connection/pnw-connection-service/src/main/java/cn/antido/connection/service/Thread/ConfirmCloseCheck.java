package cn.antido.connection.service.Thread;

import java.util.Iterator;

import cn.antido.admin.mapper.NodeMapper;
import cn.antido.common.CallBackResult;
import cn.antido.connection.pojo.ConfirmTarget;
import cn.antido.connection.service.list.CheckAndRemoveList;

/**
 * @Description 用于监听停车确认已经过期后但车位接近未释放的目标<br>
 * 在ConfirmLimit线程启动后加载,并启动线程<br>
 * list中维护着需要监听的目标<br>
 * list中的元素由ConfirmLimitListener添加<br>
 * @author Antido
 * @date 2018年6月29日 下午4:22:30
 */
public class ConfirmCloseCheck implements Runnable {
	
	/**
	 * 需要使用他的一些方法
	 */
	private ConfirmLimitListener listener;
	
	private NodeMapper nodeMapper;
	
	/**
	 * 需要重复确认的未释放目标<br>
	 */
	private CheckAndRemoveList<ConfirmTarget> list; //监听队列

	public ConfirmCloseCheck(ConfirmLimitListener listener) {
		this.listener = listener;
		this.list = listener.getCloseCheckList();
		this.nodeMapper = listener.getNodeMapper();
	}
	
	/**
	 * 当list不为空时遍历队列<br>
	 * 当目标已经检测到接近时在监听队列中移除   
	 * 当ConfirmTarget中的confirmTimes(接近确认次数)达到限制时处理异常情况
	 */
	@Override
	public void run() {
		System.out.println("ConfirmCloseCheck已启动...");
		while(true) {
			Iterator<ConfirmTarget> it = list.iterator();
			while(it.hasNext()) {
				ConfirmTarget target = it.next();
				System.out.println(target.getSpaceId() + "在过期时间监听队列中");
				Boolean isClose = nodeMapper.isClose(target.getNode_id());
				if(isClose) {
					Integer confirmTimes = target.getConfirmTimes();
					//仍然处于接近状态时增加确认次数
					target.setConfirmTimes(confirmTimes + 1);
					
					if(confirmTimes >= listener.getCLOSE_TIMES_LIMIT()) {
						//当检测次数达到上限时处理此接近异常
						it.remove();
						listener.closeCheckExceptHandle(target);
					}
				} else { //接近状态已经解除
					//在监听队列中移除
					it.remove();
					
					//上锁目标车位
					CallBackResult lockSpace = listener.lockSpace(target);
					if(lockSpace.getCode() != CallBackResult.CODE_OK) {
						lockSpace = listener.lockSpace(target);
						if(lockSpace.getCode() != CallBackResult.CODE_OK) {
							//TODO:这问题可严重了...
						}
					}
					
					//同步处理车位离开后的数据
					listener.changeAllData(target);
				}
				
			}
			
			//接近检测间隔时间
			try {
				Thread.sleep(listener.getCLOSE_CHECK_INTERVAL());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}
