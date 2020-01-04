package cn.antido.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.antido.admin.pojo.ConnCallBack;
import cn.antido.admin.service.NodeService;

/**
 * @Description 控制器连接
 * @author Antido
 * @date 2018年4月2日 下午3:18:01
 */
@Controller
public class ConnController {
	@Autowired
	private NodeService nodeService;
	
	@RequestMapping(value="/conn/connTest")
	@ResponseBody
	public ConnCallBack connTest(String address) {
		ConnCallBack conn = nodeService.conn(address);
		return conn;
	} 
	
}
