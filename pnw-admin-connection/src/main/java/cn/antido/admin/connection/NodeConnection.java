package cn.antido.admin.connection;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.antido.admin.pojo.ConnCallBack;
import cn.antido.admin.pojo.Node;
import cn.antido.admin.pojo.NodeAddress;
import cn.antido.admin.service.NodeService;
/**
 * @Description 用于接受处理节点控制服务器发来的消息
 * @author Antido
 * @date 2018年3月21日 下午4:33:45
 */
@Controller
public class NodeConnection {
	
	@Autowired
	private NodeService nodeService;

	/**
	 * say hi~
	 * @return
	 */
	@RequestMapping("/")
	@ResponseBody
	public String index() {
		return "HELLO!";
	}
	
	/**
	 * print parameter 
	 */
	@RequestMapping("/print")
	@ResponseBody
	public String print(HttpServletRequest request) {
		Enumeration e = request.getParameterNames();
		while(e.hasMoreElements()) {
			String name = (String) e.nextElement();
			System.out.println(name+":"+request.getParameter(name));
		}
		return "print";
	}
	
	/**
	 * 控制器注册消息
	 */
	@RequestMapping("/regis")
	@ResponseBody
	public ConnCallBack regis(NodeAddress nodeAddress) {
		if(nodeAddress == null) {
			return ConnCallBack.EXCEPTION("数据格式不正确");
		}
		try {
			nodeService.regis(nodeAddress);
		} catch (Exception e) {
			System.out.println(e);
			return ConnCallBack.ERROR("service出现了异常");
		}
		return ConnCallBack.OK();
	}
	
	/**
	 * 接受控制端心跳<br>
	 * 数据中是zigbee协调器的全局ID
	 */
	@RequestMapping("/pulse")
	@ResponseBody
	public ConnCallBack pulse(String id) {
		nodeService.pulse(id);
		return ConnCallBack.OK();
	}
	
	/**
	 * 接收到有新增节点
	 */
	@RequestMapping("/newNode")
	@ResponseBody
	public ConnCallBack haveANewNode(Node node) {
		System.out.println("newNode");
		if(node == null) return ConnCallBack.EXCEPTION("数据格式不正确");
		try {
			nodeService.insert(node);
		} catch (Exception e) {
			return ConnCallBack.ERROR("service出现了异常");
		}
		return ConnCallBack.OK();
	}
	
	/**
	 * 接受到节点上线消息 
	 */
	@RequestMapping("/online")
	@ResponseBody
	public ConnCallBack nodeIsOnline(Node node) {
		System.out.println("online");
		if(node == null) return ConnCallBack.EXCEPTION("数据格式不正确");
		try {
			nodeService.onlineNode(node);
		} catch (Exception e) {
			return ConnCallBack.ERROR("service出现了异常");
		}
		return ConnCallBack.OK();
	}
	
	/**
	 * 接收到节点离线消息
	 */
	@RequestMapping("/outline")
	@ResponseBody
	public ConnCallBack nodeIsOutline(Node node) {
		System.out.println("outLine");
		if(node == null) return ConnCallBack.EXCEPTION("数据格式不正确");
		try {
			nodeService.outlineNode(node);
		} catch (Exception e) {
			return ConnCallBack.ERROR("service出现了异常");
		}
		return ConnCallBack.OK();
	}
	
	/**
	 * 接收到节点接近消息
	 */
	@RequestMapping("/closed")
	@ResponseBody
	public ConnCallBack nodeIsClosed(Node node) {
		System.out.println("closed");
		if(node == null) return ConnCallBack.EXCEPTION("数据格式不正确");
		try {
			nodeService.nodeClosed(node);
		} catch (Exception e) {
			return ConnCallBack.ERROR("service出现了异常");
		}
		return ConnCallBack.OK();
	}
	
	/**
	 * 接收到节点离开消息 
	 */
	@RequestMapping("/left")
	@ResponseBody
	public ConnCallBack nodeIsLeft(Node node) {
		System.out.println("left");
		if(node == null) return ConnCallBack.EXCEPTION("数据格式不正确");
		try {
			nodeService.nodeLeft(node);
		} catch (Exception e) {
			return ConnCallBack.ERROR("service出现了异常");
		}
		return ConnCallBack.OK();
	}
	
	
	
}
