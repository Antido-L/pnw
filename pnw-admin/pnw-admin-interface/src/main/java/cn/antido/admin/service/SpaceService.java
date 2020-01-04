package cn.antido.admin.service;

import java.util.List;

import cn.antido.admin.pojo.ConnCallBack;
import cn.antido.admin.pojo.Space;
import cn.antido.admin.pojo.dto.SpaceDTO;
import cn.antido.admin.pojo.dto.SpaceInfoDTO;
import cn.antido.admin.pojo.dto.SpaceModelDTO;
import cn.antido.common.pojo.PageBean;

/**
 * @Description 停车位服务接口
 * @author Antido
 * @date 2017年12月19日 下午4:58:57
 */
public interface SpaceService {
	/**
	 * 根据主键查询
	 */
	Space getSpaceById(Long spaceId);
	
	/**
	 * 根据停车场id查询车位所有车位列表分页对象
	 */
	PageBean<Space> getSpacePageBeanByParkId(Integer PAGE_SIZE, Integer currentPage, Long parkId);
	
	/**
	 * 根据页面条件查询车位列表分页对象
	 */
	PageBean<Space> getPageBeanBySpaceDTO(Integer PAGE_SIZE, Integer currentPage, SpaceDTO spaceDTO);
	
	/**
	 * 新增一个车位<br>
	 * 返回新增对象
	 */
	Space addSpace(Space space);
	
	/**
	 * 获取SpaceModelDTO
	 */
	SpaceModelDTO getSpaceModelDTO(Long spaceId);
	
	/**
	 * 获取停车场内所有车位对象
	 */
	List<Space> getSpaceListByParkID(Long parkId);
	
	/**
	 * 删除一个车位
	 */
	Integer deleteSpace(Long spaceId);
	
	/**
	 * 更新一个车位信息 
	 */
	Space updateSpace(Space space);
	
	/**
	 * 对一个车位上锁,自动更新车位信息,生成记录
	 */
	ConnCallBack lockOnSpace(Long spaceId, String nodeId, String parent_id); 
	
	/**
	 * 对一个车位解锁锁,自动更新车位信息,生成记录
	 */
	ConnCallBack lockOffSpace(Long spaceId, String nodeId, String parent_id);
	
	/**
	 * 根据车位ID获取车位信息,用于前端客户查看使用<br>
	 * 当车位被使用时将包含用户数据,用户数据具体封装情况由原用户决定<br>
	 * 根据userId判断车位是否属于当前用户
	 * @param spaceId
	 * @return
	 */
	SpaceInfoDTO getSpaceInfoDTO(Long spaceId); 
	
}
