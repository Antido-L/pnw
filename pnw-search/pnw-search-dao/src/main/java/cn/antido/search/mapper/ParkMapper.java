package cn.antido.search.mapper;

import java.util.List;

import cn.antido.admin.pojo.Park;

/**
 * @Description 搜索引擎数据库数据库操作 
 * @author Antido
 * @date:2018年2月2日 下午11:16:04
 */
public interface ParkMapper {
	
	/**
	 * 获取所有park数据 
	 */
	List<Park> selectParkInfo();
	
	/**
	 * 根据id获取park数据 
	 */
	Park selectByParkId(Long ParkId);
}
