package cn.antido.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import cn.antido.admin.pojo.Park;
import cn.antido.admin.pojo.Space;
import cn.antido.admin.pojo.dto.SpaceDTO;
import cn.antido.admin.pojo.dto.SpaceModelDTO;
import cn.antido.admin.service.ParkService;
import cn.antido.admin.service.SpaceService;
import cn.antido.common.pojo.PageBean;
/**
 * @Description SpaceController
 * @author Antido
 * @date 2017年12月19日 下午4:21:22
 */
@Controller
public class SpaceController {
	
	@Autowired
	private SpaceService spaceService;
	
	@Autowired
	private ParkService parkService;
	
	@Value("${defalut.PAGE_SIZE}")
	private Integer PAGE_SIZE;
	
	/**
	 * 根据get请求的参数获取车位分页对象,用于展示车位信息表<br>
	 * request域中放置PageBean 和当前路径(用于页面拼接URL)
	 */
	@RequestMapping("/space/list")
	public String showPage(
			Model model, @RequestParam(value="page", defaultValue="1") Integer currentPage, Long park_id, SpaceDTO spaceDTO) {
		
		//PageBean<Space> pageBean = spaceService.getSpacePageBeanByParkId(PAGE_SIZE, currentPage, space);
		Park park = new Park();
		park.setId(park_id);
		spaceDTO.setPark(park);
		PageBean<Space> pageBean = spaceService.getPageBeanBySpaceDTO(PAGE_SIZE, currentPage, spaceDTO);
		model.addAttribute("pageBean",pageBean);
		model.addAttribute("pagePath", "/space/list");
		
		return "space/list";
	}
	
	/**
	 * 根据当前车位id异步加载车位信息模态框数据<br>
	 * 返回封装好的json格式的DTO对象
	 */
	@RequestMapping(value="/space/modelJsonBySpaceId")
	@ResponseBody
	public SpaceModelDTO getModelJsonBySpaceId(Long spaceId) {
		SpaceModelDTO spaceModelDTO = spaceService.getSpaceModelDTO(spaceId);
		return spaceModelDTO;
	}
	
	/**
	 * 根据parkId获取所有停车位<br>
	 * 返回JSON格式数据
	 */
	@RequestMapping(value="/space/allSpaceByParkId")
	@ResponseBody
	public List<Space> getAllSpaceByParkId(Long parkId) {
		if(parkId == null) {
			return null;
		}
		List<Space> list = spaceService.getSpaceListByParkID(parkId);
		return list;
	}
	
	/**
	 * ajax提交新增Space<br>
	 * 返回新的的Space对象 JSON格式数据
	 */
	@RequestMapping(value="/space/add", method=RequestMethod.POST)
	@ResponseBody
	public Space addSpace(Space space) {
		if(space == null) return null;
		//XSS过滤
		String code = HtmlUtils.htmlEscape(space.getCode());
		if(code.length() > 16) return null;
		space.setCode(code);
		
		//XSS过滤
		String remark = HtmlUtils.htmlEscape(space.getRemark());
		if(remark.length() > 32) return null;
		space.setRemark(remark);
		
		if(space.getSpace_type() == null) return null;
		if(space.getX_axis() == null) return null;
		if(space.getY_axis() == null) return null;
		
		Space addSpace = spaceService.addSpace(space);
		
		return addSpace;
	}
	
	
	/**
	 * 删除一个车位
	 */
	@RequestMapping(value="/space/delete", method=RequestMethod.POST)
	@ResponseBody
	public Integer deleteSpace(Long spaceId) {
		if(spaceId == null) {
			return null;
		}
		
		return spaceService.deleteSpace(spaceId);
	}
	
	
	/**
	 *	根据ID获取space对象<br>
	 *	返回JSON格式对象 
	 */
	@RequestMapping(value="/space/getSpaceById", method=RequestMethod.POST)
	@ResponseBody
	public Space getSpace(Long spaceId) {
		if(spaceId == null) {
			return null;
		}
		Space space = spaceService.getSpaceById(spaceId);
		return space;
	}
	
	
	/**
	 *	根据表单提交的数据更新space
	 */
	@RequestMapping(value="/space/update", method=RequestMethod.POST)
	@ResponseBody
	public Space updateSpace(Space space) {
		if(space == null) return null;
		//XSS过滤
		String code = HtmlUtils.htmlEscape(space.getCode());
		if(code.length() > 16) return null;
		space.setCode(code);
		
		//XSS过滤
		String remark = HtmlUtils.htmlEscape(space.getRemark());
		if(remark.length() > 32) return null;
		space.setRemark(remark);
		
		if(space.getSpace_type() == null) return null;
		if(space.getX_axis() == null) return null;
		if(space.getY_axis() == null) return null;
		
		Space updateSpace = spaceService.updateSpace(space);
		return updateSpace;
	}
	
	/**
	 * 展示mod页面<br>
	 * 将相关park数据放入request域中
	 */
	/*@RequestMapping(value="/space/mod")
	public String showSpaceEditPage(Model model, Long parkId) {
		Park park = parkService.getParkById(parkId);
		model.addAttribute("park",park);
		return "/space/mod";
	}*/
	
	
	
	
}
