package cn.antido.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.antido.admin.pojo.Node;
import cn.antido.admin.pojo.filter.DaoFilter;

/**
 * @Description 节点设备DAO
 * @author Antido
 * @date 2017年12月26日 下午4:40:34
 */
public interface NodeMapper {
	/**
	 * 根据主键获取节点对象 
	 */
	Node selectByPrimaryKey(String node_id);
	
	/**
	 * 根据父id获取节点列表
	 */
	List<Node> selectByParnetId(String parentId);
	
	/**
	 * 使用过滤器查询
	 */
	List<Node> selectByFilter(DaoFilter filter);
 	
	/**
	 * 插入一个新的节点对象 
	 */
	Integer insertByNode(Node node);
	
	/**
	 * 更新一个节点的上线信息 
	 */
	Integer updateIsOnline(Node node);
	
	/**
	 * 更新一个节点的车位锁信息  
	 */
	Integer updateIsLock(Node node);
	
	/**
	 * 更新一个节点接近开关信息 
	 */
	Integer updateIsClose(Node node);
	
	/**
	 * 更新一个节点信息 
	 */
	Integer updateByNode(Node node);
	
	/**
	 * 将一个节点的space_id清空
	 */
	void removeSpaceId(String id);
	
	/**
	 * 根据parentId将节点的space_id清空
	 */
	void removeSpaceIdByparentId(String parentId);

	/**
	 * 将所有该父节点下的子节点都改为离线状态
	 */
	void outlineByParent(String parentId);

	/**
	 * 查看目标节点的接近状态
	 * @param node_id
	 */
	Boolean isClose(String id);
}
