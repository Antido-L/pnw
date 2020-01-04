package cn.antido.node.service.thread;

import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;

import cn.antido.node.mapper.NodeMapper;
import cn.antido.node.pojo.State;
import cn.antido.node.service.thread.sender.MsgSender;
import cn.antido.node.service.thread.sender.MsgSenderFactory;


/**
 * @Description 节点上线状态监听器<br>
 * 在启动线程时将STATE对照表的索引加入成员变量中,该线程可一直访问STATE中的内容<br>
 * 监听器每过一段时间运行一次遍历STATE将alive置为false<br>
 * 当串口监听器获取活跃节点后将STATE中的alive置为true<br>
 * 监听器在遍历过程中如果发现alive为false,说明该节点在规定时间内没有消息,可以视为下线<br>
 * @author Antido
 * @date 2018年3月16日 下午7:22:20
 */
public class OutLineListener implements Runnable{
	
	private NodeMapper nodeMapper;
	
	private ConcurrentHashMap<String, State> STATE;
	private ConcurrentHashMap<String, String> NAME2ID;
	private Stack<String> removeName;
	private long millis;
	
	private cn.antido.admin.service.NodeService adminNodeService;
	
	/**
	 * TODO: 这个类实现的实在是有点恶心 有时间看能不能改改
	 */
	public OutLineListener(NodeMapper nodeMapper, ConcurrentHashMap<String, State> STATE, 
			ConcurrentHashMap<String, String> NAME2ID, long millis, 
			cn.antido.admin.service.NodeService adminNodeService) {
	
		System.out.println("串口数据监听器实例化");
		this.nodeMapper = nodeMapper;
		this.STATE = STATE;
		this.NAME2ID = NAME2ID;
		this.millis = millis;
		this.removeName = new Stack<String>();
		this.adminNodeService = adminNodeService;
	}
	

	@Override
	public void run() {
		while(true) {
			try {
				System.out.println("outLineListener"+STATE+":"+STATE.size());
				for (String name : STATE.keySet()) {
					System.out.println(name+":"+STATE.get(name).isAlive());
					if(STATE.get(name).isAlive()) STATE.get(name).setAlive(false); //如果是活跃节点,重置标记位
					else { //该节点已经失活
						//先把需要删除的节点名放入栈中, 等迭代完成后移除,防止发生并发修改异常
						removeName.push(name);
						
						try {
							nodeMapper.outLineNode(name);
						} catch (Exception e) {
							System.out.println("*************************************");
							System.out.println("卧槽~~~OutLineListener写数据库的时候出异常了!");
							System.out.println("*************************************");
							//TODO:处理数据库操作可能发生的异常
						}
						System.out.println("outLineMsg"+NAME2ID.get(name));
						//向服务器发送消息
						/*MsgSender outMsg = senderFactory.outlineMsgSender(NAME2ID.get(name));
						executor.execute(outMsg);*/
						
						cn.antido.admin.pojo.Node outLineNode = new cn.antido.admin.pojo.Node();
						outLineNode.setId(NAME2ID.get(name));
						//离线节点
						adminNodeService.outlineNode(outLineNode);
					}
					
				}
				//移除已下线的节点
				while(!removeName.isEmpty()) {
					STATE.remove(removeName.pop());
				}
				Thread.sleep(millis); //检测间隔
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
		
	}

}
