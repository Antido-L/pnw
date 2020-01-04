package cn.antido.admin.mapper;

import java.util.List;

import cn.antido.admin.pojo.CarModel;


/**
 * @Description 品牌车型DAO
 * @author Antido
 * @date 2017年12月26日 下午4:50:55
 */
public interface CarModelMapper {
	
	/**
	 * 获取所有该品牌的车型
	 * @return List
	 */
	List<CarModel> selectAllByBrand(Integer brandId);
	
}
