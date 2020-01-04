package cn.antido.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.antido.admin.pojo.dto.ParkAdminByStreetDTO;
import cn.antido.admin.service.ParkAdminService;

/**
 * @Description 管理员相关前端控制器
 * @author Antido
 * @date 2018年1月4日 下午6:40:43
 */
@Controller
public class ParkAdminController {
	
	@Autowired
	private ParkAdminService parkAdminService;
	
	/**
	 * 根据streetId查找默认负责人和当前street中所有负责人列表<br>
	 * 返回JSON格式包装对象
	 */
	@RequestMapping(value="/parkAdmin/adminJsonByStreet", method=RequestMethod.POST)
	@ResponseBody
	public ParkAdminByStreetDTO getAdminDTOByStreet(Integer streetId) {
		if(streetId == null) {
			return null;
		}		
		ParkAdminByStreetDTO parkAdminByStreetDTO = parkAdminService.getAdminDTOByStreet(streetId);
		return parkAdminByStreetDTO;
	}
	
}
