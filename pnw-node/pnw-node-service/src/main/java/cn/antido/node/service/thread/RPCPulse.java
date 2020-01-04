package cn.antido.node.service.thread;

/**
 * @Description RPC方式的心跳
 * @author Antido
 * @date 2018年5月24日 下午4:29:09
 */
public class RPCPulse implements Runnable{
	
	private Long time;
	private String id;
	private cn.antido.admin.service.NodeService adminNodeService;
	
	
	public RPCPulse(Long time, String id, cn.antido.admin.service.NodeService adminNodeService) {
		this.time = time;
		this.id = id;
		this.adminNodeService = adminNodeService;
	}
	

	@Override
	public void run() {
		for(;;) {
			adminNodeService.pulse(id);
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
