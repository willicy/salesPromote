package net.kingkid.SalesPromote.service;


import java.util.Date;
import java.util.List;
import net.kingkid.SalesPromote.entity.Cart;
import net.kingkid.SalesPromote.entity.Order;
import net.kingkid.SalesPromote.entity.OrderItem;

/**
 * 处理客户端的业务层接口
 */
public interface IOrderService {

	
	
	
	/**
	 * 删除购物车内数据
	 */
	void deleteCartItem(Integer salesId,Integer itemId) ;
	/**
	 * 更改购物车内数据数量
	 */
	void updateCartItemNumber(Cart cart,Date updateTime) ;

	/**
	 * 获取订单数据
	 */
	List<Order> getAllOrder(Date startTime,Date endTime,String customer,Boolean payment,Integer salesId) ;
	
	/**
	 * 获取订单内商品数据
	 */
	List<OrderItem> getAllOrderItem(Integer orderId) ;
	/**
	 * 获取订单和订单内商品数据
	 */
	List<List<Object>> getAllOrderAndOrderItem(Date startTime,Date endTime,String customer,Boolean payment,Integer salesId) ;
	/**
	 * 获取订单和订单内商品数据
	 */
	OrderItem getOrderItem(Integer oderItemId);
	/**
	 * 更改订单状态
	 */
	void updateOrderState(Integer id,Boolean state);
}
