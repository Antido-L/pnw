package cn.antido.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	@RequestMapping(value="/")
	public String showIndex() {
		return "index";
	}
}
