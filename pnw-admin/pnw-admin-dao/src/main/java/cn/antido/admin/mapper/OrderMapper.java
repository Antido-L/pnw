package cn.antido.admin.mapper;

import java.util.List;

import cn.antido.admin.pojo.Order;
import cn.antido.admin.pojo.Space;
import cn.antido.admin.pojo.User;

/**
 * @Description 订单DAO
 * @author Antido
 * @date 2017年12月26日 下午4:50:55
 */
public interface OrderMapper {
	/**
	 * 根据主键获取订单对象 
	 */
	Order selectByPrimaryKey(String order_id);
	
	/**
	 * 根据用户获取订单列表 
	 */
	List<Order> selectByUser(User user);
	
	/**
	 * 根据车位获取订单列表 
	 */
	List<Order> selectBySpace(Space space);
	
	/**
	 * 插入一条新的订单
	 */
	Integer insertByOrder(Order order);
	
	/**
	 * 更新订单<br>
	 * 只更新pay_state,pay_type,subtotal,reserve_time,end_time,using_time
	 */
	Integer updateByOrder(Order order);
}
