package cn.antido.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import cn.antido.admin.pojo.District;
import cn.antido.admin.pojo.Street;
import cn.antido.admin.pojo.dto.ParkSelectEchoDTO;
import cn.antido.admin.pojo.dto.SpaceSelectEchoDTO;
import cn.antido.admin.service.RegionService;
/**
 * @Description RegionController<br>
 * 区域数据控制器
 * @author Antido
 * @date 2017年12月23日 下午7:59:38
 */
@Controller
public class RegionController {
	
	@Autowired
	private RegionService regionService;
	
	/**
	 * 直接获取所有省/直辖市列表json格式数据
	 */
	@RequestMapping(value="/region/provinceJson",method=RequestMethod.POST)
	@ResponseBody
	public List<District> getProvince(){
		List<District> list = regionService.getProvice();
		return list;
	}
	
	/**
	 * 根据父id获取子区域
	 */
	@RequestMapping(value="/region/getJsonByParentId",method=RequestMethod.POST)
	@ResponseBody
	public List<District> getRegionByParentId(Integer parent_id){
		List<District> list = regionService.getRegionByParentId(parent_id);
		return list;
	}
	
	/**
	 * 根据行政区域id获取街道数据列表<br>
	 * 返回json格式数据类型
	 */
	@RequestMapping(value="/region/StreetJson",method=RequestMethod.POST)
	@ResponseBody
	public List<Street> getStreetByDistinctId(Integer district_id){
		List<Street> list = regionService.getStreetByDistrictId(district_id);
		return list;
	}
	
	/**
	 * 当车位页面选定完需要显示的停车场时,根据当前停车场id回显下拉选内的数据<br>
	 * 返回json格式数据类型<br>
	 * 数据内容包含 所有需要回显的列表数据 和被选中的数据
	 */
	@RequestMapping(value="/region/backJsonByParkId",method=RequestMethod.POST)
	@ResponseBody
	public SpaceSelectEchoDTO getBackJsonByParkId(Long park_id){
		if(park_id == null || park_id == 0L) {
			return null;
		}
		SpaceSelectEchoDTO SpaceSelectEchoDTO = regionService.getSpaceSelectEchoDTO(park_id);
		return SpaceSelectEchoDTO;
	}
	
	
	/**
	 * 根据当期选择的停车场搜索范围,回显下拉选数据<br>
	 * 获取参数值所属范围未知<br>
	 * 返回json格式数据类型<br>
	 * 数据内容包含 所有需要回显的列表数据 和被选中的数据
	 */
	@RequestMapping(value="/region/backJsonByRegionId",method=RequestMethod.POST)
	@ResponseBody
	public ParkSelectEchoDTO getBackJsonByRegionId(Integer regionId) {
		ParkSelectEchoDTO parkSelectEchoDTO = regionService.getParkSelectEchoDTO(regionId);
		return parkSelectEchoDTO;
	}
}




