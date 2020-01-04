package cn.antido.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 页面控制器
 * @author Antido
 * @date 2017年12月17日 下午3:36:29
 */
@Controller
public class PageController {
	
	/**
	 * 登录首页 转发至WEB-INF/jsp/index
	 */
	@RequestMapping("/")
	public String showIndex(Model model) {
		return "index";
	}
	
	/**
	 * 根据url转发至对应jsp
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}

}
