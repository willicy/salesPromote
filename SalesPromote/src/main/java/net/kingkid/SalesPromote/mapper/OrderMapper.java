package net.kingkid.SalesPromote.mapper;

import java.util.List;

import net.kingkid.SalesPromote.entity.Order;
import net.kingkid.SalesPromote.entity.OrderItem;

/**
 * 处理订单数据的持久层 
 */   
 
public interface OrderMapper {

	
	/**
	 * 找出订单数据
	 */
	List<Order> getAllOrder(Integer salesId);
	/**
	 * 找出订单数据
	 */
	List<Order> getAllFilterOrder(Integer salesId,String filter);
	
	/**
	 * 找出订单内的商品数据
	 */  
	List<OrderItem> getAllOrderItem(Integer orderId);
	/**
	 * 找出订单内的商品数据 
	 */
	OrderItem getOrderItem(Integer id);
	
	/**
	 * 更改订单状态
	 */
	Integer updateOrderState(Integer id,boolean state);
	/**
	 * 删除订单透过客户ids
	 */
	Integer deleteOrderByCustomerIds(List<Integer>customerIds);
	/**
	 * 删除订单商品透过客户ids
	 */
	Integer deleteOrderItemByCustomerIds(List<Integer>customerIds);
}



