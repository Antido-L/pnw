package cn.antido.admin.mapper;

import java.util.List;

import cn.antido.admin.pojo.BaseDict;

/**
 * @Description 数据字典DAO<BR>
 * 所有查询默认只获取上线数据
 * @author Antido
 * @date 2018年1月4日 下午3:20:36
 */
public interface BaseDictMapper {
	
	/**
	 * 根据项目类型查询 
	 */
	List<BaseDict> selectByTypeCode(Integer typeCode); 
	
	/**
	 * 根据项目类型查询并正序排列
	 */
	List<BaseDict> selectAscByTypeCode(Integer typeCode);
	
	/**
	 * 根据项目类型查询并倒叙排列
	 */
	List<BaseDict> selectDescByTypeCode(Integer typeCode);
}
