package net.kingkid.SalesPromote.mapper;

import java.util.List;

import net.kingkid.SalesPromote.entity.Cart;
import net.kingkid.SalesPromote.entity.CartItem;
import net.kingkid.SalesPromote.entity.Order;
import net.kingkid.SalesPromote.entity.OrderItem;

/**
 * 处理商店数据的持久层 
 */   
 
public interface ShopMapper {

	/**
	 * 插入购物车数据
	 * @return 受影响的行数
	 */
	Integer addCart(Cart cart);
	/**
	 * 更新购物车数据
	 * @return 受影响的行数
	 */
	Integer updateCartNumber(Cart cart);
	/**
	 * 透过customerId和itemId找出购物车内的数据
	 */
	List<Cart> findCartItemByCustomerIdAndItemId(Integer customerId,Integer itemId);
	/**
	 * 找出购物车内的数据
	 */
	List<CartItem> getAllCartItem(Integer customerId);
	/**
	 * 找出购物车内的数据
	 */
	List<CartItem> getCartItemByItemIds(Integer customerId,List<Integer> itemIds);
	/**
	 * 透过customerId和itemIds删除购物车内的数据
	 */
	Integer deleteCartItemByCustomerIdAndItemIds(Integer customerId,List<Integer> itemIds);
	/**
	 * 透过itemIds删除购物车内的数据
	 */
	Integer deleteCartItemByItemIds(List<Integer> itemIds);
	/**
	 * 透过customerIds删除购物车内的数据
	 */
	Integer deleteCartItemByCustomerIds(List<Integer> customerIds);
	/**
	 * 插入订单数据
	 * @return 受影响的行数
	 */
	Integer addOrder(Order order);
	/**
	 * 插入订单内商品数据
	 * @return 受影响的行数
	 */
	Integer addOrderItem(List<OrderItem> orderItems);
	/**
	 * 找出订单数据
	 */
	List<Order> getAllOrder(Integer customerId);
	
	/**
	 * 找出订单内的商品数据
	 */
	List<OrderItem> getAllOrderItem(Integer orderId);
	/**
	 * 找出订单内的商品数据
	 */
	OrderItem getOrderItem(Integer id);
}



