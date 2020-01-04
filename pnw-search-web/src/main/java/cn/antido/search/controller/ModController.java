package cn.antido.search.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.antido.admin.pojo.Space;
import cn.antido.admin.pojo.dto.ParkMapDTO;
import cn.antido.admin.pojo.dto.SpaceModelDTO;
import cn.antido.admin.service.ParkService;
import cn.antido.admin.service.SpaceService;


/**
 * @Description 模拟图数据加载控制器
 * @author Antido
 * @date 2018年3月29日 下午4:46:25
 */
@Controller
public class ModController {
	
	@Autowired
	private ParkService parkService;
	
	@Autowired
	private SpaceService spaceService;
	/**
	 * 异步加载停车场模拟图数据<br>
	 * 返回JSON格式数据传输对象
	 */
	@RequestMapping(value="/mod/getParkMapByParkId")
	@ResponseBody
	public ParkMapDTO getParkMap(Long parkId) {
		if(parkId == null) {
			return null;
		}
		
		ParkMapDTO parkMapDTO = parkService.getParkMapDTOByParkId(parkId);
		return parkMapDTO;
	}
	
	/**
	 * 根据当前车位id异步加载车位信息模态框数据<br>
	 * 返回封装好的json格式的DTO对象
	 */
	@RequestMapping(value="/mod/modelJsonBySpaceId")
	@ResponseBody
	public SpaceModelDTO getModelJsonBySpaceId(Long spaceId) {
		SpaceModelDTO spaceModelDTO = spaceService.getSpaceModelDTO(spaceId);
		return spaceModelDTO;
	}
	
	/**
	 * 根据parkId获取所有停车位<br>
	 * 返回JSON格式数据
	 */
	@RequestMapping(value="/mod/allSpaceByParkId")
	@ResponseBody
	public List<Space> getAllSpaceByParkId(Long parkId) {
		if(parkId == null) {
			return null;
		}
		List<Space> list = spaceService.getSpaceListByParkID(parkId);
		return list;
	}
	
}
