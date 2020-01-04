package cn.antido.admin.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.antido.admin.pojo.Node;
import cn.antido.admin.service.NodeService;

/**
 * @Description NodeController
 * @author Antido
 * @date:2018年1月20日 下午7:08:11
 */
@Controller
public class NodeController {
	
	@Autowired
	private NodeService nodeService;
	
	/**
	 * 加载当前停车场内空闲的节点数据<br>
	 * 返回JSON数据
	 */
	@RequestMapping(value="/node/getNodeByParentId", method=RequestMethod.POST)
	@ResponseBody
	public List<Node> getNodeByParentId(String parentId) {
		List<Node> list = nodeService.getFreeNodeByParentId(parentId);
		return list;
	}
	
	
	
}
