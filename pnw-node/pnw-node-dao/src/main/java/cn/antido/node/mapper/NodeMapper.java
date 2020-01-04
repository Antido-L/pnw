package cn.antido.node.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.antido.node.pojo.Node;

/**
 * @Description Node数据库操作接口
 * @author Antido
 * @date 2018年3月15日 下午8:20:34
 */
public interface NodeMapper {
	
	/**
	 * 获取所有的节点列表 
	 */
	List<Node> selectAll();
	
	/**
	 * 新增一条节点数据 
	 */
	void insertByNode(Node node);

	/**
	 * 将一个节点改为上线状态
	 */
	void onLineNode(@Param("nodeName")String nodeName, @Param("isClose")boolean isClose);
	
	/**
	 * 将一个节点改为下线状态
	 */
	void outLineNode(String nodeName);
	
	/**
	 * 更新一个节点的接近情况
	 * @param node
	 */
	void updateIsClose(Node node);

	/**
	 * 将所有的节点上线状态更新为false
	 */
	void outLineAll();
	
}
