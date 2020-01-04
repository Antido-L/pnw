package cn.antido.admin.mapper;

import java.util.List;

import cn.antido.admin.pojo.Park;

import cn.antido.admin.pojo.filter.DaoFilter;
/**
 * @Description :ParkMapper
 * @author Antido 
 * @date 2017年12月14日  下午7:46:21
 */
public interface ParkMapper {
	/**
	 * 根据主键获取Park对象
	 */
	Park selectByPrimaryKey(Long id);
	
	/**
	 * 插入一条数据
	 */
	void insertPark(Park park);
	
	/**
	 * 根据id更新一条记录
	 */
	Integer updateByPark(Park park);
	
	/**
	 * 获取所有park对象<br>
	 * 不包含过滤条件<br>
	 * 由PageHelper补全limit语句
	 */
	List<Park> selectAll();
	
	/**
	 * 根据过滤条件查询
	 */
	List<Park> selectByFilter(DaoFilter filter);

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

	/**
	 * 根据street_id 获取该区域内id值最大的,用于新增id
	 */
	Long selectMaxIdByStreetId(Integer streetId);
	
	/**
	 * 根据ID获取停车场模拟图相关数据<br>
	 * direction, model_row, model_col 
	 */
	Park selectMapInfoById(Long parkId);
	
	/**
	 * 根据ID删除一条数据
	 */
	void deleteById(Long id);
	
	/**
	 * 根据id将停车场的正在使用数加1
	 * @param id
	 */
	void addUsingCount(Long id);
	
	/**
	 * 根据id将停车场的正在使用数减1
	 * @param id
	 */
	void subUsingCount(Long id);
	
	/**
	 * 根据ID将指定停车场的workingCount字段加一
	 * @param id
	 */
	void addWorkingCount(Long id);
	
	/**
	 * 根据ID将指定停车场的workingCount字段减一
	 * @param id
	 */
	void subWorkingCount(Long id);
	
}
