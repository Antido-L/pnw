package cn.antido.admin.mapper;

import java.util.List;


import cn.antido.admin.pojo.ParkAdmin;
import cn.antido.admin.pojo.filter.DaoFilter;
/**
 * @Description ParkAdminMapper
 * @author Antido
 * @date 2017年12月18日 下午9:36:35
 */
public interface ParkAdminMapper {
	/**
	 * 根据主键获取admin对象
	 */
	ParkAdmin selectByPrimaryKey(Integer id);
	
	/**
	 * 根据主键获取简单admin对象
	 */
	ParkAdmin selectSimpleByPrimaryKey(Integer id);
	
	/**
	 * 插入一条数据
	 */
	void insertParkAdmin(ParkAdmin parkAdmin);
	
	/**
	 * 获取所有ParkAdmin对象<br>
	 * 不包含过滤条件<br>
	 * 由PageHelper补全limit语句
	 */
	List<ParkAdmin> selectAll();
	
	/**
	 * 根据过滤器查找
	 */
	List<ParkAdmin> selectByDaoFilter(DaoFilter filter);
	
	/**
	 * 根据过滤器查找(封装简单数据对象)
	 */
	List<ParkAdmin> selectSimpleByDaoFilter(DaoFilter filter);
	
}
