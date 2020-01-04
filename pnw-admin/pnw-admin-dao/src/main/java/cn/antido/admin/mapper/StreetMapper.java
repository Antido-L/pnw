package cn.antido.admin.mapper;

import java.util.List;

import cn.antido.admin.pojo.District;
import cn.antido.admin.pojo.Street;

/**
 * @Description 街道DAO 没有插入和更新操作
 * @author Antido
 * @date 2017年12月24日 上午11:38:55
 */
public interface StreetMapper {
	/**
	 * 根据主键查询街道信息
	 */
	public Street selectByPrimaryKey(Integer id);

	/**
	 * 根据行政区域 查询所包含的街道
	 */
	public List<Street> selectByDistrict(District district);
	
}
