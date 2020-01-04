package cn.antido.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.antido.admin.pojo.Park;
import cn.antido.admin.pojo.dto.ParkAdminSearchDTO;
import cn.antido.search.service.SearchService;


/**
 * @Description SearchController
 * @author Antido
 * @date:2018年2月2日 下午10:13:33
 */
@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	/**
	 * 页面跳转
	 */
	@RequestMapping(value="/search/{page}", method=RequestMethod.GET)
	public String showPage(@PathVariable String page) {
		return "search/"+page;
	}
	
	/**
	 * 远程调用searchService导入所有停车场数据<br>
	 * 成功返回success<br>
	 * 失败返回异常信息
	 */
	@RequestMapping(value="/search/importAll", method=RequestMethod.POST)
	@ResponseBody
	public String importAll() {
		String importState = searchService.importAll();
		return importState;
	}
	
	/**
	 * 调用searchService进行关键字搜索
	 */
	@RequestMapping(value="/search/keywordQuery", method=RequestMethod.POST)
	@ResponseBody
	public List<ParkAdminSearchDTO> keywordsQuery(String keyword) {
		if("".equals(keyword) || keyword == null) {
			return null;
		}
		List<ParkAdminSearchDTO> park = searchService.keywordQuery(keyword);
		return park;
	}
	
}
