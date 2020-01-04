package cn.antido.search.mapper;

import java.util.List;

import cn.antido.admin.pojo.District;
/**
 * @Description 行政区域DAO 没有插入和更新操作
 * @author Antido
 * @date 2017年12月24日 上午11:38:29
 */
public interface DistrictMapper {
	/**
	 * 根据主键查询区域信息
	 */
	District selectByPrimaryKey(Integer id);

	/**
	 * 根据父ID查询所包含的行政区域 
	 */
	List<District> selectByParentId(Integer parent_id);
	
	/**
	 * 查询所有parent_id 为null的
	 */
	List<District> selectByParentIdIsNull();
}
