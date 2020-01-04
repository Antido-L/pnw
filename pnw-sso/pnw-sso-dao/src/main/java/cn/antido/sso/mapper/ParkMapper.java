package cn.antido.sso.mapper;

import java.util.List;

import cn.antido.admin.pojo.Park;

public interface ParkMapper {
	/**
	 * 根据主键获取Park对象
	 */
	Park selectByPrimaryKey(Long id);
	
	/**
	 * 根据Street_id获取停车场id,name
	 */
	List<Park> selectByStreetId(Integer street_id);


	/**
	 * 根据Street_id获取停车场所有对象
	 */
	List<Park> selectAllByStreetId(Integer streetId);

	/**
	 * 根据区县一级获取所有停车场
	 */
	List<Park> selectAllByDistrictId(Integer districtId);
	
	/**
	 * 根据市一级获取所有停车场
	 */
	List<Park> selectAllByCityId(Integer cityId);
	
	/**
	 * 根据省一级获取所有停车场
	 */
	List<Park> selectAllByProvinceId(Integer provinceId);

	/**
	 * 根据street_id 获取去重后的park_admin_id列表
	 */
	List<Integer> selectAdminByStreetId(Integer id);
}
