package cn.antido.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import cn.antido.admin.pojo.Park;
import cn.antido.admin.pojo.dto.ParkAddFormDTO;
import cn.antido.admin.pojo.dto.ParkInfoEditEchoDTO;
import cn.antido.admin.pojo.dto.ParkMapDTO;
import cn.antido.admin.pojo.dto.ParkModelDTO;
import cn.antido.admin.service.ParkService;
import cn.antido.common.pojo.PageBean;
/**
 * @Description:ParkController
 * @author antido
 * @date 2017年12月15日 下午2:19:57
 */
@Controller
public class ParkController {
	
	@Autowired
	private ParkService parkService;
	
	@Value("${defalut.PAGE_SIZE}")
	private Integer PAGE_SIZE;
	
	
	/**
	 * 获取停车场列表页分页对象<br>
	 * 将PageBean放入request域中,数据中只包含正在使用中的停车场
	 */
	@RequestMapping(value="/park/list")
	public String showListPage(
			Model model, @RequestParam(value="page", defaultValue="1") Integer currentPage, Integer regionId) {
		PageBean<Park> pageBean = parkService.getPageBean(PAGE_SIZE,currentPage,regionId);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("pagePath", "/park/list");
		return "park/list";
	}
	
	/**
	 * 根据街道获取当前停车场列表<br>
	 * 返回JSON格式数据<br>
	 * 数据中只包含name,id
	 */
	@RequestMapping("/park/parkJsonByStreetId")
	@ResponseBody
	public List<Park> getParkByStreetId(Integer street_id) {
		List<Park> list = parkService.getParkByStreetId(street_id);
		return list;
	}
	
	/**
	 * 根据parkId获取单击事件模态框数据传输对象<br>
	 * 返回JSON格式数据
	 */
	@RequestMapping(value="/park/modelJsonByParkId",method=RequestMethod.POST)
	@ResponseBody
	public ParkModelDTO getParkModelDTO(Long parkId) {
		if(parkId == null) {
			return null;
		}
		ParkModelDTO parkModelDTO = parkService.getParkModelDTOByParkId(parkId);
		return parkModelDTO;
	}
	
	/**
	 * 展示停车场添加页面 
	 */
	/*@RequestMapping("/park/add")
	public String showParkAdd() {
		return "park/add";
	}*/
	
	/**
	 * 停车场添加表单提交<br>
	 * ParkAddFormDTO:表单数据封装对象<br>
	 * 展示添加情况反馈页面<br>
	 */
	@RequestMapping(value="/park/addPark",method=RequestMethod.POST)
	public String addPark(ParkAddFormDTO parkAddFormDTO) {
		//验证表单数据合法性
		//如果前端提交的数据类型与实体类成员变量不匹配则springMVC会返回400(The request sent by the client was syntactically incorrect)
		
		if(parkAddFormDTO == null) {
			return "redirect:/park/addCallBack_error";
		}
		
		boolean isError = false;
		
		if(parkAddFormDTO.getCityId() == null) isError = true;
		
		if(parkAddFormDTO.getModDirect() == null) isError = true;
		
		if(parkAddFormDTO.getModCol() == null) isError = true;
		
		if(parkAddFormDTO.getModRow() == null) isError = true;
		
		if(parkAddFormDTO.getModInfo() == null) isError = true;
	
		if(parkAddFormDTO.getProvinceId() == null) isError = true;
		 
		if(parkAddFormDTO.getDistrictId() == null) isError = true;
		
		if(parkAddFormDTO.getStreetId() == null) isError = true;
	
		if(parkAddFormDTO.getDesignCount() == null) isError = true;
		
		//经度为0-180
		if(parkAddFormDTO.getEast() == null ||  parkAddFormDTO.getEast() < 0 || parkAddFormDTO.getEast() > 180) isError = true;
		
		//纬度0-90
		if(parkAddFormDTO.getNorth() == null || parkAddFormDTO.getNorth() < 0 || parkAddFormDTO.getNorth() > 90) isError = true;	
		
		if(parkAddFormDTO.getParkType() == null) isError = true;
		
		
		if(parkAddFormDTO.getParkPositionDesc() == null || parkAddFormDTO.getParkPositionDesc().length() > 120) {
			isError = true;
		} else {
			//XSS过滤
			String safeStr = HtmlUtils.htmlEscape(parkAddFormDTO.getParkPositionDesc());
			parkAddFormDTO.setParkPositionDesc(safeStr);
		}
		
		if(parkAddFormDTO.getName() == null || parkAddFormDTO.getName().length() > 30) {
			isError = true;
		} else {
			//XSS过滤
			String safeStr = HtmlUtils.htmlEscape(parkAddFormDTO.getName());
			parkAddFormDTO.setName(safeStr);
		}
		
		if(parkAddFormDTO.getRadioAdmin() == null) {
			isError = true;
		} else {
			if(parkAddFormDTO.getRadioAdmin() == 0) {
				if(parkAddFormDTO.getDefaultParkAdmin() == null) isError = true;
			}
			if(parkAddFormDTO.getRadioAdmin() == 2) {
				if(parkAddFormDTO.getSelectParkAdmin() == null) isError = true;
			}
		}
		
		if(parkAddFormDTO.getIsFree() == null) {
			isError = true;
		} else {
			if(parkAddFormDTO.getIsFree() == 0) {
				if(parkAddFormDTO.getPrice() == null) isError = true;
				if(parkAddFormDTO.getFreeTime() == null) isError = true;
			}
		}
		
		if(parkAddFormDTO.getServiceIp() == null) {
			isError = true;
		} else {
			//正则匹配IP格式
			String pattern = "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)($|(?!\\.$)\\.)){4}$";
			boolean isIp = Pattern.matches(pattern, parkAddFormDTO.getServiceIp());
			if(!isIp) isError = true;
		}
		
		//判断校验结果
		if(isError) {
			//校验失败 直接返回错误信息
			return "redirect:/park/addCallBack_error";
		} else { 
			//DTO校验通过则传给service做进一步处理
			Map<String,String> callBack = parkService.addParkByDTO(parkAddFormDTO);
			if("success".equals(callBack.get("state"))) {
				return "redirect:/park/addCallBack_success";
			} else {
				return "redirect:/park/addCallBack_error";
			}
		}
	}
	
	/**
	 * 停车场修改表单提交<br>
	 * ParkAddFormDTO:表单数据封装对象<br>
	 * 展示修改情况反馈页面<br>
	 */
	@RequestMapping(value="/park/updatePark", method=RequestMethod.POST)
	public String updatePark(ParkAddFormDTO parkAddFormDTO) {
		//验证表单数据合法性
		//如果前端提交的数据类型与实体类成员变量不匹配则springMVC会返回400(The request sent by the client was syntactically incorrect)
		
		if(parkAddFormDTO == null) {
			return "redirect:/park/updateCallBack_error";
		}
		
		boolean isError = false;
		
		if(parkAddFormDTO.getParkId() == null) isError = true;
		
		if(parkAddFormDTO.getState() == null) isError = true;
		
		if(parkAddFormDTO.getCityId() == null) isError = true;
		
		if(parkAddFormDTO.getModDirect() == null) isError = true;
		
		if(parkAddFormDTO.getModCol() == null) isError = true;
		
		if(parkAddFormDTO.getModRow() == null) isError = true;
	
		if(parkAddFormDTO.getProvinceId() == null) isError = true;
		 
		if(parkAddFormDTO.getDistrictId() == null) isError = true;
		
		if(parkAddFormDTO.getStreetId() == null) isError = true;
	
		if(parkAddFormDTO.getDesignCount() == null) isError = true;
		
		//经度为0-180
		if(parkAddFormDTO.getEast() == null ||  parkAddFormDTO.getEast() < 0 || parkAddFormDTO.getEast() > 180) isError = true;
		
		//纬度0-90
		if(parkAddFormDTO.getNorth() == null || parkAddFormDTO.getNorth() < 0 || parkAddFormDTO.getNorth() > 90) isError = true;	
		
		if(parkAddFormDTO.getParkType() == null) isError = true;
		
		
		if(parkAddFormDTO.getParkPositionDesc() == null || parkAddFormDTO.getParkPositionDesc().length() > 120) {
			isError = true;
		} else {
			//XSS过滤
			String safeStr = HtmlUtils.htmlEscape(parkAddFormDTO.getParkPositionDesc());
			parkAddFormDTO.setParkPositionDesc(safeStr);
		}
		
		if(parkAddFormDTO.getName() == null || parkAddFormDTO.getName().length() > 30) {
			isError = true;
		} else {
			//XSS过滤
			String safeStr = HtmlUtils.htmlEscape(parkAddFormDTO.getName());
			parkAddFormDTO.setName(safeStr);
		}
		
		if(parkAddFormDTO.getRadioAdmin() == null) {
			isError = true;
		} else {
			if(parkAddFormDTO.getRadioAdmin() == 0) {
				if(parkAddFormDTO.getDefaultParkAdmin() == null) isError = true;
			}
			if(parkAddFormDTO.getRadioAdmin() == 2) {
				if(parkAddFormDTO.getSelectParkAdmin() == null) isError = true;
			}
		}
		
		if(parkAddFormDTO.getIsFree() == null) {
			isError = true;
		} else {
			if(parkAddFormDTO.getIsFree() == 0) {
				if(parkAddFormDTO.getPrice() == null) isError = true;
				if(parkAddFormDTO.getFreeTime() == null) isError = true;
			}
		}
		
		if(parkAddFormDTO.getServiceIp() == null) {
			isError = true;
		} else {
			//正则匹配IP格式
			String pattern = "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)($|(?!\\.$)\\.)){4}$";
			boolean isIp = Pattern.matches(pattern, parkAddFormDTO.getServiceIp());
			if(!isIp) isError = true;
		}
		//判断校验结果
		if(isError) {
			//校验失败 直接返回错误信息
			return "redirect:/park/updateCallBack_error";
		} else { 
			//DTO校验通过则传给service做进一步处理
			HashMap<String,String> callBack = parkService.updateParkByDTO(parkAddFormDTO);
			if("success".equals(callBack.get("state"))) {
				return "redirect:/park/updateCallBack_success";
			} else {
				return "redirect:/park/updateCallBack_error";
			}
		}
	}
	
	/**
	 * 展示"park/developing"根据URL信息展示开发中的停车场分页显示对象<br>
	 * 默认展示无参第一页
	 */
	@RequestMapping("/park/developing")
	public String showDepPage(
			Model model, @RequestParam(value="page", defaultValue="1") Integer currentPage, Integer regionId) {
		PageBean<Park> pageBean = parkService.getDevPageBean(PAGE_SIZE, currentPage, regionId);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("pagePath", "/park/developing");
		return "park/developing";
	}
	
	/**
	 * 展示"/park/infoEdit"<BR>
	 * 根据parkId在request域中放置表单回显对象
	 */
	@RequestMapping("/park/infoEdit")
	public String showInfoEditPage(Model model, Long parkId) {
		if(parkId == null) {
			return "index";
		}
		ParkInfoEditEchoDTO parkInfoEditEchoDTO = parkService.getInfoEditEchoDTO(parkId);
		if(parkInfoEditEchoDTO == null) {
			return "index";
		}
		model.addAttribute("infoEcho", parkInfoEditEchoDTO);
		return "park/infoEdit";
	}
	
	
	/**
	 * 异步加载停车场模拟图数据<br>
	 * 返回JSON格式数据传输对象
	 */
	@RequestMapping(value="/park/getParkMapByParkId")
	@ResponseBody
	public ParkMapDTO getParkMap(Long parkId) {
		if(parkId == null) {
			return null;
		}
		
		ParkMapDTO parkMapDTO = parkService.getParkMapDTOByParkId(parkId);
		return parkMapDTO;
	}
	
	/**
	 * 展示spaceEdit页面<br>
	 * 加载相关park数据
	 */
	@RequestMapping(value="/park/spaceEdit")
	public String showSpaceEditPage(Model model, Long parkId) {
		Park park = parkService.getParkById(parkId);
		model.addAttribute("park",park);
		return "/park/spaceEdit";
	}
	
	/**
	 * 根据ID获取完整Park对象
	 * 返回JSON格式数据
	 */
	@RequestMapping(value="/park/getParkById")
	@ResponseBody
	public Park getParkById(Long parkId) {
		Park park = parkService.getParkById(parkId);
		return park;
	}
	
	/**
	 * 根据ID删除Park
	 */
	@RequestMapping(value="/park/deletePark")
	@ResponseBody
	public String deletePark(Long parkId) {
		//Park park = parkService.getParkById(parkId);
		parkService.deletePark(parkId);
		return null;
	}
	
	/**
	 * 根据ID将开发中的停车场发布上线
	 */
	@RequestMapping(value="/park/parkPublish")
	@ResponseBody
	public String publishPark(Long parkId) {
		parkService.publishPark(parkId);
		return null;
	}
	
	
	/*@RequestMapping(value="/park/spaceEditInfo", method=RequestMethod.POST)
	@ResponseBody
	public SpaceEidtInfoDTO getSpaceEidtInfoDTO(Long parkId) {
			
		return null;
	}*/
	
	/**
	 * 展示mod页面<br>
	 * 将相关park数据放入request域中
	 */
	@RequestMapping(value="/park/mod")
	public String showParkModPage(Model model, Long park_id) {
		Park park = parkService.getParkById(park_id);
		model.addAttribute("park",park);
		return "/park/mod";
	}
	
	/**
	 * 更改park的状态,
	 */
	@RequestMapping(value="/park/changeParkState")
	@ResponseBody
	public String changeParkState(Long parkId, Byte state) {
		Park park = parkService.getParkById(parkId);
		if(park == null) throw new RuntimeException("当前停车场不存在");
		//停车场需要下线
		if(state != '1') {
			//检测当前车位被使用情况
			if(park.getUsing_count() > 0) {
				return park.getUsing_count()+"";
			}
		}
		//更改状态
		if(state > 4 && state < -1) throw new RuntimeException("数据异常"+state);
		parkService.changeParkState(parkId, state);
		return null;
	}
	
	/**
	 * park内页面展示
	 */
	@RequestMapping(value="/park/{page}", method=RequestMethod.GET)
	public String showPage(@PathVariable String page) {
		return "park/"+page;
	}
	
	
}
