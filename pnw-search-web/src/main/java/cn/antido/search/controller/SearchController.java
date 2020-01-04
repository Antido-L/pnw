package cn.antido.search.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.antido.admin.pojo.dto.ParkAdminSearchDTO;
import cn.antido.admin.pojo.dto.ParkSearchDTO;
import cn.antido.common.pojo.PageBean;
import cn.antido.common.pojo.search.ParkQueryDTO;
import cn.antido.common.pojo.search.SolrQueryResult;
import cn.antido.search.service.SearchService;

/**
 * @Description SearchController<br>
 * 搜索服务页面控制器
 * @author Antido
 * @date 2018年3月29日 下午12:34:58
 */
@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@Value("${defalut.PAGE_SIZE}")
	private Integer PAGE_SIZE;
	
	@Value("${defalut.PAGE_SIZE_APP}")
	private Integer PAGE_SIZE_APP;
	
	@Value("${defalut.CITY_ID}")
	private String CITY_ID;
	
	@Value("${defalut.CITY_NAME}")
	private String CITY_NAME;
	
	//位置信息的cookie名
	@Value("${cookie.PNW_POSITION}")
	private String PNW_POSITION;
	
	/**
	 * 处理404
	 */
	@RequestMapping(value="*")
	public String showNotFount() {
		return "notFound";
	}
	
	/**
	 * 展示默认页面<br>
	 * 页面中会以GET请求发来各种查询过滤条件,封装在ParkQueryDTO中<br>
	 * 查询索引库获取数据放入request域中,页面加载时生成数据
	 */
	@RequestMapping(value="/")
	public String showPage(ParkQueryDTO parkQuery, Model model, 
			HttpServletRequest request, HttpServletResponse response) {
		//System.out.println(parkQuery);
		//设置默认搜索个数
		parkQuery.setPageSize(PAGE_SIZE);
		//没有参数默认第一页
		if (parkQuery.getCurrentPage() == null) { 
			parkQuery.setCurrentPage(1);
		}
		
		//从cookie中获取cityID作为索引条件
		Cookie[] cookies = request.getCookies();
		String position = CITY_ID;
		//TODO:如果没有有效的位置默认设置为沈阳
		/*
		if(cookies == null) position = CITY_ID;
		else {
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals(PNW_POSITION)) {
					if("".equals(cookie.getValue())) { //默认搜索的城市范围
						position = CITY_ID;
					} else {
						position = cookie.getValue().split("-")[0];
					}
				}
			}
		}
		*/
		
		parkQuery.setCityCode(position);
		
		PageBean<SolrQueryResult> pageBean = searchService.parkQueryByDTO(parkQuery);
		model.addAttribute(pageBean);
		return "index";
	}
	
	/**
	 * 针对手机端的列表查询
	 * 返回JSON格式数据
	 * 可能会包含各类限制信息 
	 */
	@RequestMapping(value="/m/getList")
	@ResponseBody
	public Object listQuery(ParkQueryDTO parkQuery, String callback, 
			HttpServletRequest request, HttpServletResponse response) {
		//System.out.println(parkQuery);
		//设置默认搜索个数
		parkQuery.setPageSize(PAGE_SIZE_APP);
		//没有参数默认第一页
		if (parkQuery.getCurrentPage() == null) { 
			parkQuery.setCurrentPage(1);
		}
		
		//从cookie中获取cityID作为索引条件
		//Cookie[] cookies = request.getCookies();
		String position = CITY_ID;
		//TODO:如果没有有效的位置默认设置为沈阳
		
		parkQuery.setCityCode(position);
		
		PageBean<SolrQueryResult> pageBean = searchService.parkQueryByDTO(parkQuery);
		
		//判断是否为jsonp请求
		if(callback != null && !"".equals(callback)) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(pageBean);
			mappingJacksonValue.setJsonpFunction(callback);
			
			return mappingJacksonValue;
		}
		
		return pageBean;
	}
	
	
	/**
	 * 调用searchService导进行关键字搜索<br>
	 * 可返回jsonp数据请求<br>
	 * 单次最大检索数量:8<br>
	 * 按照匹配度排序
	 */
	@RequestMapping(value="/search/keywordQuery")
	@ResponseBody
	public Object keywordsQuery(String keyword, Integer cityId, String callback, 
			HttpServletRequest request, HttpServletResponse response) {
		if("".equals(keyword) || keyword == null) {
			return null;
		}
		
		keyword = keyword.replaceAll("[^0-9|A-Za-z|\u4e00-\u9fa5|_]","");
		if("".equals(keyword)) return null;
		
		//判断非法字符

		//TODO:获取用户城市ID
		//目前默认沈阳
		cityId  = 210100;
		System.out.println("keyword:" + keyword);
		if(request.getMethod().equals("GET")) {
			try {
				keyword = URLDecoder.decode(keyword, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		List<ParkSearchDTO> parkList = searchService.keywordQuery(keyword,cityId);
		
		//判断是否为jsonp请求
		if(callback != null && !"".equals(callback)) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(parkList);
			mappingJacksonValue.setJsonpFunction(callback);
			
			return mappingJacksonValue;
		}
		
		return parkList;
	}
	
	/**
	 * 根据地图坐标点获取目标周围的停车场数据
	 */
	@RequestMapping(value="/nearTarget")
	@ResponseBody
	public List<ParkSearchDTO> getNearTarget(Double x, Double y) {
		
		
		
		return null;
	}
	
	@RequestMapping(value="/delayPrint")
	@ResponseBody
	public String delayPrint() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return "print";
	}
	
	
}
