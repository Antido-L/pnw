package cn.antido.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.antido.admin.mapper.DistrictMapper;
import cn.antido.admin.mapper.ParkMapper;
import cn.antido.admin.mapper.StreetMapper;
import cn.antido.admin.pojo.District;
import cn.antido.admin.pojo.Park;
import cn.antido.admin.pojo.Street;
import cn.antido.admin.pojo.dto.ParkSelectEchoDTO;
import cn.antido.admin.pojo.dto.SpaceSelectEchoDTO;
import cn.antido.admin.service.RegionService;
/**
 * @Description 区域服务实现类
 * @author Antido
 * @date 2017年12月23日 下午8:06:41
 */
@Service
@Transactional
public class RegionServiceImpl implements RegionService {
	
	@Autowired
	private DistrictMapper districtMapper;
	@Autowired
	private StreetMapper streetMapper;
	@Autowired
	private ParkMapper parkMapper;
	
	/**
	 * 获取所有省份District对象列表
	 */
	@Override
	@Transactional(readOnly=true)
	public List<District> getProvice() {
		//所有parent_id = null的数据即为省数据
		List<District> list = districtMapper.selectByParentIdIsNull();
		return list;
	}
	
	/**
	 * 根据parent_id获取District对象列表
	 */
	@Override
	@Transactional(readOnly=true)
	public List<District> getRegionByParentId(Integer parent_id) {
		if(parent_id == null) {
			return null;
		}
		List<District> list = districtMapper.selectByParentId(parent_id);
		return list;
	}
	
	/**
	 * 根据行政区域id获取Street对象列表
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Street> getStreetByDistrictId(Integer district_id) {
		if(district_id == null) {
			return null;
		}
		District district = new District();
		district.setId(district_id);
		//如果district_id非法可能会抛异常
		/*try {
		} catch (Exception e) {
			return null;
		}*/
		List<Street> list = streetMapper.selectByDistrict(district);
		return list;
	}

	/**
	 * 根据根据停车场id获取区域下拉选回显对象
	 */
	@Override
	@Transactional(readOnly=true)
	public SpaceSelectEchoDTO getSpaceSelectEchoDTO(Long park_id) {
		//查询当前park_id对应的park对象 获取对象中包含的street_id
		Park park = parkMapper.selectByPrimaryKey(park_id);
		if(park == null) {
			return null;
		}
		Street street = park.getStreet(); //selectByPrimaryKey为单表查询,street中只包含id属性
		if(street == null) {
			return null;
		}
		Integer streetId = street.getId();
		if(streetId == null) {
			return null;
		}
		//获取当前street内所有停车场
		List<Park> parkEcho = parkMapper.selectByStreetId(streetId);
		//获取与当前street所属同一District
		Street streetObj = streetMapper.selectByPrimaryKey(streetId);
		if(streetObj == null) {
			return null;
		}
		//获取所属同一区县的街道数据回显对象
		List<Street> streetEcho = streetMapper.selectByDistrict(streetObj.getDistrict());
		
		//
		Integer districtId = streetObj.getDistrict().getId();
		if(districtId == null) {
			return null;
		}
		//区县一级被选对象
		District districtDistrict = districtMapper.selectByPrimaryKey(districtId);
		//获取和当前区县一级对象 parent_id相同的 即是区县一级回显列表
		Integer cityId = districtDistrict.getParent_id();
		List<District> districtEcho = districtMapper.selectByParentId(cityId);
		
		//通过区县一级父id获取市一级被选对象
		District cityDistrict = districtMapper.selectByPrimaryKey(cityId);
		//与当前city父id相同的 即是city一级回显对象
		Integer provincetId = cityDistrict.getParent_id();
		List<District> cityEcho = districtMapper.selectByParentId(provincetId);
		
		//省一级列表数据
		List<District> provinceEcho = districtMapper.selectByParentIdIsNull();
		
		//封装回显数据传输对象
		SpaceSelectEchoDTO spaceSelectEchoDTO = new SpaceSelectEchoDTO();
		spaceSelectEchoDTO.setParkEcho(parkEcho);
		spaceSelectEchoDTO.setStreetEcho(streetEcho);
		spaceSelectEchoDTO.setDistrictEcho(districtEcho);
		spaceSelectEchoDTO.setCityEcho(cityEcho);
		spaceSelectEchoDTO.setProvinceEcho(provinceEcho);
		
		Map<String, Number> selectedEcho = new HashMap<String,Number>();
		selectedEcho.put("parkSelected", park_id);
		selectedEcho.put("streetSelected", streetId);
		selectedEcho.put("districtSelected", districtId);
		selectedEcho.put("citySelected", cityId);
		selectedEcho.put("provinceSelected", provincetId);
		
		spaceSelectEchoDTO.setSelectedEcho(selectedEcho);
		
		return spaceSelectEchoDTO;
	}
	
	
	/**
	 * 根据根据区域id获取区域下拉选回显对象
	 */
	/**
	 * @param regionId
	 * @return
	 */
	@Override
	@Transactional(readOnly=true)
	public ParkSelectEchoDTO getParkSelectEchoDTO(Integer regionId) {
		if(regionId == null) {
			return null;
		}
		
		List<Street> streetEcho = null;
		List<District> districtEcho = null;
		List<District> cityEcho = null;
		List<District> provinceEcho = null;
		Map<String,Integer> selectedEcho = new HashMap<String,Integer>();
		
		//判断当前id所属级别
		if(regionId > 999999) {  //属于street级别
			Street streetSelected = streetMapper.selectByPrimaryKey(regionId);
			if(streetSelected == null) {
				return null;
			}
			//根据所属district获取回显street数据列表
			District district = streetSelected.getDistrict();
			streetEcho = streetMapper.selectByDistrict(district);
			
			
			District districtSelected = districtMapper.selectByPrimaryKey(district.getId());
			districtEcho = districtMapper.selectByParentId(districtSelected.getParent_id());
			
			District citySelected = districtMapper.selectByPrimaryKey(districtSelected.getParent_id());
			cityEcho = districtMapper.selectByParentId(citySelected.getParent_id());
			
			//获取省一级回显数据对象
			District provinceSelected = districtMapper.selectByPrimaryKey(citySelected.getParent_id());
			provinceEcho = districtMapper.selectByParentIdIsNull();
			
			//添加selected
			selectedEcho.put("streetSelected", streetSelected.getId());
			selectedEcho.put("districtSelected", districtSelected.getId());
			selectedEcho.put("citySelected", citySelected.getId());
			selectedEcho.put("provinceSelected", provinceSelected.getId());
		} else {  //属于street以上任意级别
			//如果父id==null 为省一级
			if(regionId % 100 != 0) { //如果 id % 100 =! 0 为district一级
				District districtSelected = districtMapper.selectByPrimaryKey(regionId);
				if(districtSelected == null) {
					return null;
				} 
				districtEcho = districtMapper.selectByParentId(districtSelected.getParent_id());
				
				District citySelected = districtMapper.selectByPrimaryKey(districtSelected.getParent_id());
				cityEcho = districtMapper.selectByParentId(citySelected.getParent_id());
				
				District provinceSelected = districtMapper.selectByPrimaryKey(citySelected.getParent_id());
				provinceEcho = districtMapper.selectByParentIdIsNull();
				
				selectedEcho.put("districtSelected", districtSelected.getId());
				selectedEcho.put("citySelected", citySelected.getId());
				selectedEcho.put("provinceSelected", provinceSelected.getId());				
			} else { //如果 id % 100 == 0 为city或者province一级
				if(regionId % 10000 == 0) { //id % 10000 == 0 为省一级
					District provinceSelected = districtMapper.selectByPrimaryKey(regionId);
					if(provinceSelected == null) {
						return null;
					}
					provinceEcho = districtMapper.selectByParentIdIsNull();
					
					selectedEcho.put("provinceSelected", provinceSelected.getId());
				} else { //id % 10000 == 0 为city一级
					District citySelected = districtMapper.selectByPrimaryKey(regionId);
					if(citySelected == null) {
						return null;
					}
					cityEcho = districtMapper.selectByParentId(citySelected.getParent_id());
					
					District provinceSelected = districtMapper.selectByPrimaryKey(citySelected.getParent_id());
					provinceEcho = districtMapper.selectByParentIdIsNull();
					
					
					selectedEcho.put("citySelected", citySelected.getId());
					selectedEcho.put("provinceSelected", provinceSelected.getId());
				}
				
			}
		}  //if(region > 99999) 结束
		
		//封装DTO
		ParkSelectEchoDTO parkSelectEchoDTO = new ParkSelectEchoDTO();
		parkSelectEchoDTO.setSelectedEcho(selectedEcho);
		parkSelectEchoDTO.setStreetEcho(streetEcho);
		parkSelectEchoDTO.setDistrictEcho(districtEcho);
		parkSelectEchoDTO.setCityEcho(cityEcho);
		parkSelectEchoDTO.setProvinceEcho(provinceEcho);
		
		return parkSelectEchoDTO;
	}

}








