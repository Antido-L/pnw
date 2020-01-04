package cn.antido.sso.mapper;

import java.util.List;

import cn.antido.admin.pojo.CarBrand;


/**
 * @Description 车辆品牌DAO
 * @author Antido
 * @date 2017年12月26日 下午4:50:55
 */
public interface CarBrandMapper {
	
	/**
	 * 获取所有品牌列表
	 * @return List
	 */
	List<CarBrand> selectAll();
	
}
