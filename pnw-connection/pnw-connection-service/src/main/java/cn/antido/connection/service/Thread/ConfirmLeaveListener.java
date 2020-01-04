package cn.antido.connection.service.Thread;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.antido.admin.mapper.NodeMapper;
import cn.antido.common.CallBackResult;
import cn.antido.connection.pojo.ConfirmTarget;
import cn.antido.connection.service.impl.CommandServiceImpl;
import cn.antido.connection.service.list.CheckAndRemoveList;

/**
 * @Description 车辆离开情况监听线程<br>
 * 在用户选择释放车位后 定时查看车位上的接近状态<br>
 * 确认无接近后释放关闭车位,完成订单,同步各项数据
 * @author Antido
 * @date 2018年7月6日 下午3:58:15
 */
@Service
public class ConfirmLeaveListener extends Thread{
	
	private Thread selfThread;
	
	@Value("${confirm.leaveCheck.interval}")
	private Integer LEAVE_CHECK_INTERVAL;
	
	@Value("${confirm.leaveCheck.timesLimit}")
	private Integer LEAVE_TIMES_LIMIT;
	
	@Autowired
	private NodeMapper nodeMapper;
	
	/**
	 * 需要他的部分方法
	 */
	@Autowired
	private CommandServiceImpl commandServiceImpl;
	
	/**
	 * 目标离开监听队列
	 */
	@Resource
	private CheckAndRemoveList<ConfirmTarget> confirmLeaveList;
	
	/**
	 * spring加载时运行此方法,并启动该线程
	 */
	@Override
	@PostConstruct
	public synchronized void start() {
		if(selfThread == null) {
			selfThread = new Thread(this,"ConfirmLeaveListener");
			selfThread.start();
		}
	}
	
	
	/**
	 * 定时遍历confirmLeaveList查询数据库确认离开信息<br>
	 * 当确认离开后在监听列表中移除该元素
	 */
	@Override
	public void run() {
		System.out.println("ConfirmLeaveListener已启动");
		while(true) {
			//TODO:这里改成阻塞的
			while(!confirmLeaveList.isEmpty()) {
				Iterator<ConfirmTarget> it = confirmLeaveList.iterator();
				while(it.hasNext()) {
					ConfirmTarget target = it.next();
					//检测是否接近
					boolean isClose = isTargetClose(target);
					System.out.println("confirmLeaveList:" + target.getSpaceId());
					if(!isClose) { //已经检测到离开
						//上锁车位,完成车位离开各项业务
						try {
							CallBackResult res = commandServiceImpl.spaceLeaveHandler(target);
							//TODO: 给予用户反馈
							
						} catch (Exception e) {
							e.printStackTrace();
							leaveHandlerExpection();
						}
						//在监听链表中移除
						it.remove();
					} else {
						Integer confirmTimes = target.getConfirmLeaveTimes();
						target.setConfirmLeaveTimes(confirmTimes + 1); 
						//当在规定检测次数内 仍然未来检测到离开的车位
						if(confirmTimes > LEAVE_TIMES_LIMIT) {
							it.remove();
							spaceLeaveExceptionHandler(target);
						}
					}
					
				}
				//遍历的间隔时间
				try {
					Thread.sleep(LEAVE_CHECK_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			//遍历的间隔时间
			try {
				Thread.sleep(LEAVE_CHECK_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * 处理业务异常,防止线程挂掉
	 */
	private void leaveHandlerExpection() {
		// TODO Auto-generated method stub
		
	}


	/**
	 * 在提交离开请求后在规定时间内 仍然检测的接近 从而无法对车位上锁
	 * @param target
	 */
	private void spaceLeaveExceptionHandler(ConfirmTarget target) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * 检测当前节点的接近状态
	 * @param target
	 * @return
	 */
	private boolean isTargetClose(ConfirmTarget target) {
		Boolean close = nodeMapper.isClose(target.getNode_id());
		return close;
	}
	
}
