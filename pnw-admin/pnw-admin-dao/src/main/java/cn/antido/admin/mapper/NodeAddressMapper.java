package cn.antido.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.antido.admin.pojo.Node;
import cn.antido.admin.pojo.NodeAddress;
import cn.antido.admin.pojo.filter.DaoFilter;

/**
 * @Description 节点控制器地址对照表
 * @author Antido
 * @date 2017年12月26日 下午4:40:34
 */
public interface NodeAddressMapper {
	
	/**
	 * 插入一条数据
	 * @param nodeAddress
	 */
	void insert(NodeAddress nodeAddress);
	
	/**
	 * 根据对象获取一条数据 
	 */
	NodeAddress selectByNodeAddress(NodeAddress nodeAddress);

	/**
	 * 根据id获取一条数据 
	 */
	NodeAddress selectById(String id);
	
	/**
	 * 根据Id更新一条记录
	 */
	void updateState(NodeAddress nodeAddress);
	
	/**
	 * 根据地址获取一条数据
	 */
	NodeAddress selectByAddress(String address);
}
