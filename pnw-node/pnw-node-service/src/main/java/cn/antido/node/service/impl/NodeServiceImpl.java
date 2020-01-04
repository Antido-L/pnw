package cn.antido.node.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.antido.admin.pojo.NodeAddress;
import cn.antido.common.CallBackResult;
import cn.antido.node.mapper.CloseRecordMapper;
import cn.antido.node.mapper.LockRecordMapper;
import cn.antido.node.mapper.NodeMapper;
import cn.antido.node.pojo.CloseRecord;
import cn.antido.node.pojo.LockRecord;
import cn.antido.node.pojo.Node;
import cn.antido.node.pojo.Result;
import cn.antido.node.pojo.State;
import cn.antido.node.service.NodeService;
import cn.antido.node.service.servial.SerialTools;
import cn.antido.node.service.socket.SocketService;
import cn.antido.node.service.thread.OutLineListener;
import cn.antido.node.service.thread.Pulse;
import cn.antido.node.service.thread.RPCPulse;
import cn.antido.node.service.thread.ReSendMsg;
import cn.antido.node.service.thread.SocketServiceThread;
import cn.antido.node.service.thread.sender.MsgSender;
import cn.antido.node.service.thread.sender.MsgSenderFactory;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

/**
 * @Description NodeServiceImpl
 * @author Antido
 * @date 2018年3月19日 下午4:55:33
 */
@Service
@Transactional
public class NodeServiceImpl implements NodeService {
	/**
	 * 节点名与状态对照表<br>
	 * 
	 * key:子节点名,value:true-检测到接近,false-未检测到接近<br>
	 * 只存放活跃的节点
	 */
	private ConcurrentHashMap<String, State> STATE; //
	
	/**
	 * 通过ID获取节点名<br>
	 * 
	 * key:与服务器相同的NodeID,value:子节点名<br>
	 * 一个协调器下节点的数量并不多,因为要实时获取节点的状态,节点数据都在内存中查询,数据库主要维护历史记录消息和与服务器之间的ID,name对照表<br>
	 * 对于每拥有相同任务的协调器来说子节点因拥有相同的命名规则,对于服务器来说使用UUID(32)来标识任意节点<br>
	 * 这份对照表由节点控制器维护与创造
	 */
	private ConcurrentHashMap<String, String> ID2NAME;
	
	/**
	 * 通过NAME获取ID,相当于ID2NAME的反向索引<br>
	 * key:节点名,value:NodeID<br>
	 */
	private ConcurrentHashMap<String, String> NAME2ID;
	
	/**
	 * 服务器消息发送线程连接池,通过调用executor.execute(Runnable)启动消息发送线程<br>
	 * 
	 */
	//private ThreadPoolExecutor executor;
	
	
	/**
	 * 注入配置文件中的数据
	 */
	@Value("${serial.node.outLineTime}")
	private Long outLineTime;
	
	@Value("${threadPool.corePoolSize}")
	private int corePoolSize;
	
	@Value("${threadPool.maximumPoolSize}")
	private int maximumPoolSize;
	
	@Value("${threadPool.keepAliveTime}")
	private int keepAliveTime;
	
	@Value("${node.description}")
	private String description;
	
	@Value("${socket.service.address}")
	private String selfAddress;
	
	@Value("${socket.service.port}")
	private Integer selfPort;
	
	@Value("${node.controllerId}")
	private String controllerId;
	
	@Value("${node.closeLimit}")
	private Integer closeLimit;
	
	@Value("${socket.service.port}")
	private int port;
	
	@Value("${socket.command.key}")
	private String COMMAND_KEY;
	
	
	/**
	 * spring织入
	 */
	@Autowired
	private NodeMapper nodeMapper;
	
	@Autowired
	private LockRecordMapper lockRecordMapper;
	
	@Autowired
	private CloseRecordMapper closeRecordMapper;
	
	/**
	 * RPC 远程调用PNW-admin-service接口
	 */
	@Autowired
	private cn.antido.admin.service.NodeService adminNodeService;
	
	/**
	 * 消息发送者工厂对象
	 */
	/*@Autowired
	private MsgSenderFactory senderFactory;*/
	
	/**
	 * 根据id织入串口操作工具
	 */
	@Resource
	private SerialTools serialTools;
	
	/**
	 * 初始化串口连接,新建串口监听器,新建节点离线监听器,通过查询数据库建立NAME2ID和ID2NAME<br>
	 * 如果在初始化过程中没有发生异常,此方法只会被执行一次<br>
	 * 在spring加载完毕后执行该方法
	 */
	@Override
	@PostConstruct
	public synchronized Result init() {
		
		if(this.STATE != null) return Result.EXCEPTION("系统已经被初始化"); //只能初始化一次
		
		try {
			serialTools.Init(); //初始化串口
			
			//初始化HASH表
			this.NAME2ID = new ConcurrentHashMap<String,String>();
			this.ID2NAME = new ConcurrentHashMap<String,String>();
			this.STATE = new ConcurrentHashMap<String,State>();
			//将数据库中所有节点的上线状态置0 后续通过接受节点心跳消息来确认节点上线状态
			nodeMapper.outLineAll();
			
			//查询数据库初始化对照表
			List<Node> list = nodeMapper.selectAll();
			for (Node node : list) {
				NAME2ID.put(node.getName(), node.getGlobal_id());
				ID2NAME.put(node.getGlobal_id(), node.getName());
				
				//State s = new State();
				//s.setAlive(false);
				//s.setClose(node.getIs_close());
				//STATE.put(node.getName(), s);
			}
			
			//创建串口事件监听器
			serialTools.addListener(new SerialPortEventListener() {
				
				@Override
				public void serialEvent(SerialPortEvent event) {
					switch (event.getEventType()) {

		            case SerialPortEvent.BI: // 10 通讯中断
		                
		            case SerialPortEvent.OE: // 7 溢位（溢出）错误

		            case SerialPortEvent.FE: // 9 帧错误

		            case SerialPortEvent.PE: // 8 奇偶校验错误

		            case SerialPortEvent.CD: // 6 载波检测

		            case SerialPortEvent.CTS: // 3 清除待发送数据

		            case SerialPortEvent.DSR: // 4 待发送数据准备好了

		            case SerialPortEvent.RI: // 5 振铃指示

		            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
		                break;
		            
		            case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
		            	InputStream in;
		            	//获取串口数据
						try {
							in = serialTools.getSerialPort().getInputStream(); 
							StringBuilder sb = new StringBuilder();
							byte buff[] =  new byte[255];
							int len ;
							while((len = in.read(buff)) > 0) {
								sb.append(new String(buff,0,len));
							}
							//处理一条串口发来的字符串数据
							if(sb.toString().trim().length() == 8) {
								portDataHandle(sb.toString().trim());
							}
							sb.delete(0, sb.length()); //清空缓冲区
						} catch (IOException e) {
							e.printStackTrace();
						}
		            	break;
					}
				}

			});
			
			/**
			//初始化与服务端发送消息线程池
			this.executor = new ThreadPoolExecutor(
				corePoolSize, 								//corePoolSize线程池中核心线程数
				maximumPoolSize, 							//maximumPoolSize 线程池中最大线程数 
				keepAliveTime, 								//keepAliveTime线程池中线程的最大空闲时间
				TimeUnit.MINUTES, 							//时间单位  
				new LinkedBlockingQueue<Runnable>(corePoolSize), //阻塞队列
				new ThreadPoolExecutor.DiscardOldestPolicy()//丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
			);
			*/
			//新建节点离线离线监听
			OutLineListener outLineListener = 
					new OutLineListener(nodeMapper, STATE, NAME2ID, outLineTime, adminNodeService);
			
			Thread thread = new Thread(outLineListener);
			//启动监听器
			thread.start(); 
			
			//启动指令接受服务线程
			
			SocketService socketService = new SocketService();
			socketService.setKeepAliveTime(keepAliveTime);
			socketService.setCorePoolSize(corePoolSize);
			socketService.setMaximumPoolSize(maximumPoolSize);
			socketService.setPort(port);
			socketService.setCommandKey(COMMAND_KEY);
			socketService.setNodeServiceImpl(this);
			SocketServiceThread socketServiceThread = new SocketServiceThread(socketService);
			new Thread(socketServiceThread).start();
			
			//发送注册消息
			regis();
			//启动与服务端的心跳连接
			pulse();
			
		} catch (Exception e) {
			this.NAME2ID = null;
			this.ID2NAME = null;
			this.STATE = null;
			e.printStackTrace();
		}
		return Result.OK();
	}
	
	/**
	 * 串口接受数据处理<br>
	 * @param data
	 */
	private void portDataHandle(String data) {
		System.out.println("serviceImpl"+STATE+":"+STATE.size());
		System.out.println(data);
		
		String info[] = data.split("-");
		String nodeName = info[0];
		
		//获取传感器距离值
		int numIndex = 0;
		while(info[1].charAt(numIndex) == '*') {
			numIndex++;
		}
		String echoState = info[1].substring(numIndex, info[1].length());
		int echoTime = Integer.parseInt(echoState);
		System.out.println("echoTime : " + echoTime);
		
		//判断接近状态
		boolean isClose = false;
		if(echoTime < closeLimit) {
			isClose = true;
		}
		
		// = !nodeState.equals("1"); //数据中开关未按下时为高电平
		
		//如果STATE中不包含该节点名,是一个新加入的活跃节点
		if(!STATE.containsKey(nodeName)) { 
			//将节点加入状态对照表
			STATE.put(nodeName, new State(true,isClose));
			//查询ID对照表中是否包含该节点信息
			if(!NAME2ID.containsKey(nodeName)) { //如果ID对照表中没有此节点,证明这是一个新加入的节点(服务端不知道)
				//更新数据库中的对照表
				Node node = new Node();
				node.setName(nodeName);
				node.setGlobal_id(UUID.randomUUID().toString().replace("-", ""));
				node.setCreated(new Date());
				node.setIs_online(true);
				node.setIs_close(isClose); 
				node.setNode_desc(description);
				
				nodeMapper.insertByNode(node);
				
				//更新ID对照表
				NAME2ID.put(node.getName(), node.getGlobal_id());
				ID2NAME.put(node.getGlobal_id(), node.getName());
				
				//向服务端发送节点新增消息
				/**
				 * MsgSender newMsg = senderFactory.newNodeMsgSender(node.getGlobal_id(), description);
				 * executor.execute(newMsg);
				 */
				cn.antido.admin.pojo.Node newNode = new cn.antido.admin.pojo.Node();
				newNode.setId(node.getGlobal_id());
				newNode.setNode_desc(description);
				newNode.setParent_id(controllerId);
				//新增节点
				adminNodeService.insert(newNode);
				
				
			} else { //在对照表有此节点,证明是一个重新活跃的节点
				nodeMapper.onLineNode(nodeName,isClose); //重新上线的节点会默认将is_lock置0
				//向服务端发送节点上线信息
				/*MsgSender onlineMsg = senderFactory.onlineMsgSender(NAME2ID.get(nodeName));
				executor.execute(onlineMsg);*/
				
				cn.antido.admin.pojo.Node onlineNode = new cn.antido.admin.pojo.Node();
				onlineNode.setId(NAME2ID.get(nodeName));
				onlineNode.setParent_id(controllerId);
				adminNodeService.onlineNode(onlineNode);
			}
				
		} else { 
			if(STATE.get(nodeName).isClose() != isClose) { //发现节点状态发生了变化
				//将节点状态消息发送给服务器
				if(isClose) {
					/*MsgSender closeMsg = senderFactory.closeMsgSender(NAME2ID.get(nodeName));
					executor.execute(closeMsg);*/
					
					Node node = new Node();
					node.setName(nodeName);
					node.setIs_close(true);
					nodeMapper.updateIsClose(node);
					
					
					cn.antido.admin.pojo.Node closeNode = new cn.antido.admin.pojo.Node();
					closeNode.setId(NAME2ID.get(nodeName));
					closeNode.setParent_id(controllerId);
					adminNodeService.nodeClosed(closeNode);
					
					
				} else {
					/*MsgSender leaveMsg = senderFactory.leftMsgSender(NAME2ID.get(nodeName));
					executor.execute(leaveMsg);*/
					
					Node node = new Node();
					node.setName(nodeName);
					node.setIs_close(false);
					nodeMapper.updateIsClose(node);
					
					cn.antido.admin.pojo.Node leftNode = new cn.antido.admin.pojo.Node();
					leftNode.setId(NAME2ID.get(nodeName));
					leftNode.setParent_id(controllerId);
					adminNodeService.nodeLeft(leftNode);
				}
				
				//更新节点状态对照表
				//STATE.put(nodeName, isClose);
				STATE.get(nodeName).setClose(isClose);
				//将状态信息记录到数据库
				CloseRecord record = new CloseRecord();
				record.setName(nodeName);
				record.setGlobal_id(NAME2ID.get(nodeName));
				record.setIs_close(isClose);
				record.setBegin(new Date());
				
				closeRecordMapper.insert(record);
			}
		}
		STATE.get(nodeName).setAlive(true); //更新活跃状态
	}
	
	/**
	 * 向串口发送一条数据
	 * @throws IOException 
	 */
	private void sendMsg(String msg) throws IOException {
		//实时打印发送的消息
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    String data = formatter.format(new Date());  
		System.out.println(data+"send msg:"+msg);
		//调用串口发送
		serialTools.sendMsg(msg);
		
		//重发两次相同的消息
		ReSendMsg reSendMsg = new ReSendMsg(serialTools,msg);
		reSendMsg.start();
	}
	
	/**
	 * 对一个节点发送上锁指令
	 */
	@Override
	public CallBackResult lockOn(String nodeId) {
		String nodeName = ID2NAME.get(nodeId);
		if(nodeName == null) return CallBackResult.exception("此节点不存在");
		
		State state = STATE.get(nodeName);
		if(state == null) return CallBackResult.exception("此节点连接异常，可能已经下线");
		
		//发送指令
		try {
			sendMsg(nodeName+"on");
		} catch (IOException e) {
			return CallBackResult.exception("串口发送信息失败");
		}
		
		LockRecord record = new LockRecord(nodeName, nodeId, true, new Date());
		lockRecordMapper.insert(record);

		return CallBackResult.ok();
	}

	/**
	 * 对一个节点发送开锁指令
	 */
	@Override
	public CallBackResult lockOff(String nodeId) {
		String nodeName = ID2NAME.get(nodeId);
		if(nodeName == null) return CallBackResult.exception("此节点不存在");
		
		State state = STATE.get(nodeName);
		if(state == null) return CallBackResult.exception("此节点连接异常，可能已经下线");
		
		//发送指令
		try {
			sendMsg(nodeName+"off");
		} catch (IOException e) {
			return CallBackResult.exception("串口发送信息失败");
		}
		
		LockRecord record = new LockRecord(nodeName, nodeId, false, new Date());
		lockRecordMapper.insert(record);

		return CallBackResult.ok();
	}
	
	
	/**
	 * 向服务器发送注册信息<br>
	 * 数据包括自身地址,协调器ID,与通讯密钥
	 */
	@Override
	public void regis() {
		/*MsgSender leaveMsg = senderFactory.regMsgSender(selfAddress);
		executor.execute(leaveMsg);*/
		System.out.println("regis");
		
		NodeAddress nodeAddress = new NodeAddress();
		nodeAddress.setAddress(selfAddress);
		nodeAddress.setPort(selfPort);
		nodeAddress.setNode_id(controllerId);
		nodeAddress.setConn_key(COMMAND_KEY);
		
		
		adminNodeService.regis(nodeAddress);
	}
	
	/**
	 * 启动与服务端的心跳连接线程<br>
	 */
	@Override
	public void pulse() {
		RPCPulse rpcPulse = new RPCPulse(60000L, controllerId, adminNodeService);
		//该心跳线程在启动后会一直执行
		Thread thread = new Thread(rpcPulse);
		thread.start();
	}
	
	
}
