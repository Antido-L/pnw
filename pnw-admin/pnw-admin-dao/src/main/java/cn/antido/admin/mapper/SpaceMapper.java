package cn.antido.admin.mapper;

import java.util.List;

import cn.antido.admin.pojo.Space;
import cn.antido.admin.pojo.filter.DaoFilter;


/**
 * @Description 车位服务接口
 * @author Antido
 * @date 2017年12月19日 下午4:38:24
 */
public interface SpaceMapper {
	/**
	 * 根据主键查询
	 */
	Space selectByPrimaryKey(Long id);

	/**
	 * 根据停车场id查询 车位列表
	 */
	List<Space> selectByParkId(Long parkId);
	
	/**
	 * 根据过滤条件查询
	 */
	List<Space> selectByFilter(DaoFilter filter);
	
	/**
	 * 插入一条新数据
	 */
	Integer insertSpace(Space space);
	
	/**
	 * 根据id更新一条数据
	 */
	Integer updateBySpace(Space space);
	
	/**
	 * 获取当前park下的最大id值
	 */
	Long selectMaxId(Long parkId);
	
	/**
	 * 删除一个车位
	 */
	Integer deleteSpaceById(Long spaceId);
	
	/**
	 * 根据park_id删除数据
	 */
	void deleteSpaceByParkId(Long parkId);

	/**
	 * 根据ID将 reverse_time leaving_time parked_time 设置为NULL
	 * @param id
	 */
	void removeTimeItem(Long id);
}
