package cn.antido.admin.mapper;

import cn.antido.admin.pojo.Car;

/**
 * @Description 车辆DAO
 * @author Antido
 * @date 2017年12月26日 下午4:50:55
 */
public interface CarMapper {
	/**
	 * 根据主键获取车辆对象 
	 */
	Car selectByPrimaryKey(String car_id);
	
	/**
	 * 插入一辆小汽车~ 
	 */
	Integer insertByCar(Car car);
	
	/**
	 * 更新一辆车 
	 */
	Integer updateByCar(Car car);
}
