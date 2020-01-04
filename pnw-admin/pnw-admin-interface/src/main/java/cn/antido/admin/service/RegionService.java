package cn.antido.admin.service;

import java.util.List;

import cn.antido.admin.pojo.District;
import cn.antido.admin.pojo.Street;
import cn.antido.admin.pojo.dto.ParkSelectEchoDTO;
import cn.antido.admin.pojo.dto.SpaceSelectEchoDTO;

/**
 * @Description 区域数据服务接口
 * @author Antido
 * @date 2017年12月23日 下午8:03:33
 */
public interface RegionService {
	/**
	 * 获取所有省/直辖市数据
	 */
	List<District> getProvice();
	
	/**
	 * 根据parent_id获取区域数据
	 */
	List<District> getRegionByParentId(Integer parent_id);
	
	/**
	 * 根据行政区域id获取街道数据列表
	 */
	List<Street> getStreetByDistrictId(Integer district_id);

	/**
	 * 根据停车场id获取下拉选回显区域数据<br>
	 * 返回封装好的数据传输对象
	 */
	SpaceSelectEchoDTO getSpaceSelectEchoDTO(Long park_id);

	/**
	 * 根据区域id获取下拉选回显区域数据<br>
	 * 不需要回显的区域级别list等于null<br>
	 * 返回封装好的数据传输对象
	 */
	ParkSelectEchoDTO getParkSelectEchoDTO(Integer regionId);
	
}
