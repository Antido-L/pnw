package cn.antido.connection.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.antido.connection.pojo.NodeAddress;

/**
 * @Description 节点控制器地址对照表
 * @author Antido
 * @date 2017年12月26日 下午4:40:34
 */
public interface NodeAddressMapper {
	
	/**
	 * 根据对象获取一条数据 
	 */
	NodeAddress selectByNodeAddress(NodeAddress nodeAddress);

	/**
	 * 根据id获取一条数据 
	 */
	NodeAddress selectById(String id);
	
	/**
	 * 根据地址获取一条数据
	 */
	NodeAddress selectByAddress(String address);
}
