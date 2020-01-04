package cn.antido.node.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.antido.common.CallBackResult;
import cn.antido.node.pojo.Result;
import cn.antido.node.service.NodeService;
import cn.antido.node.service.thread.sender.MsgSenderFactory;

/**
 * @Description 控制指令处理
 * @author Antido
 * @date 2018年3月19日 下午4:54:48
 */
@Controller
public class OrderController {
	
	@Autowired
	private NodeService nodeService;
	
	/**
	 * Say hello!
	 * @return
	 */
	@RequestMapping("/")
	@ResponseBody
	public String showIndex() {
		System.out.println("hello");
		return "Hello!";
	}
	
	/**
	 * 初始化串口 
	 */
	@RequestMapping("/init")
	@ResponseBody
	public Result initSerial() {
		Result res = nodeService.init();
		System.out.println(res.getCode());
		if(res.getCode() == 0) {
			//初始化成功后发送注册信息给服务端
			//数据包含自身地址,协调器节点ID,系统状态码,data
			nodeService.regis();
			//启动与服务端的心跳连接
			nodeService.pulse();
		}
		
		return res;
	}
	
	/**
	 * 根据nodeId打开目标节点车位锁
	 * @param nodeId
	 */
	@RequestMapping("/order/lockOn")
	@ResponseBody
	public CallBackResult lockOn(String nodeId) {
		//nodeService.sendMsg(msg);
		CallBackResult res = nodeService.lockOn(nodeId);
		return res;
	}
	
	
	/**
	 * 根据nodeId关闭目标节点车位锁
	 * @param nodeId
	 */
	@RequestMapping("/order/lockOff")
	@ResponseBody
	public CallBackResult lockOff(String nodeId) {
		CallBackResult res = nodeService.lockOff(nodeId);
		return res;
	}
	
	/**
	 * 我还活着!
	 */
	@RequestMapping("/alive")
	@ResponseBody
	public CallBackResult alive(String msg) {
		System.out.println(msg);
		CallBackResult ok = CallBackResult.ok();
		return ok;
	}
	
}
