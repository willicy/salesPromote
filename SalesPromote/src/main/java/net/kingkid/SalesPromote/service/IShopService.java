package net.kingkid.SalesPromote.service;


import java.util.Date;
import java.util.List;
import net.kingkid.SalesPromote.entity.Cart;
import net.kingkid.SalesPromote.entity.CartItem;
import net.kingkid.SalesPromote.entity.ItemDropdown;
import net.kingkid.SalesPromote.entity.Order;
import net.kingkid.SalesPromote.entity.OrderItem;

/**
 * 处理客户端的业务层接口
 */
public interface IShopService {

	/**
	 * 创建购物车内数据
	 */
	void addCart(Cart cart) ;

	/**
	 * 获取购物车内数据
	 */
	List<CartItem> getAllCartItem(Integer customerId) ;
	/**
	 * 获取订单和订单内商品数据 
	 */
	List<ItemDropdown> getColorDropdown(String itemName,Integer customerId) ;
	/**
	 * 删除购物车内数据
	 */
	void deleteCartItem(Integer customerId,Integer itemId) ;
	/**
	 * 更改购物车内数据数量
	 */
	void updateCartItemNumber(Cart cart,Date updateTime) ;

	/**
	 * 创建订单数据
	 */
	void addOrder(Integer customerId, List<Integer> itemIds) ;
	/**
	 * 获取订单数据
	 */
	List<Order> getAllOrder(Integer customerId) ;
	/**
	 * 获取订单内商品数据
	 */
	List<OrderItem> getAllOrderItem(Integer orderId) ;
	/**
	 * 获取订单和订单内商品数据
	 */
	List<List<Object>> getAllOrderAndOrderItem(Integer customerId) ;
	/**
	 * 获取订单和订单内商品数据
	 */
	OrderItem getOrderItem(Integer oderItemId);
}
