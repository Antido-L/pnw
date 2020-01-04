package cn.antido.admin.mapper;

import java.util.List;


import cn.antido.admin.pojo.ParkModel;

/**
 * @Description 停车场模拟图数据DAO
 * @author Antido
 * @date:2018年1月18日 下午11:35:15
 */
public interface ParkModelMapper {
	
	/**
	 * 根据停车场获取所用model数据<br>
	 * 左模糊
	 */
	List<ParkModel> selectAllModelByParkId(Long parkId);
	
	/**
	 * 插入数据<br>
	 */
	Integer insertParkModel(ParkModel model);
	
	/**
	 * 删除model数据
	 */
	Integer deleteParkModelById(String modelId);
	
	/**
	 * 根据停车场删除model数据<br>
	 * 左模糊查询
	 */
	Integer deleteParkModelByParkId(Long parkId);
	
	/**
	 * 坐标更新一个model数据
	 */
	Integer updateParkModel(ParkModel model);
	
}
