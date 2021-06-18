package net.kingkid.SalesPromote.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kingkid.SalesPromote.entity.Cart;
import net.kingkid.SalesPromote.entity.CartItem;
import net.kingkid.SalesPromote.entity.Item;
import net.kingkid.SalesPromote.entity.ItemDropdown;
import net.kingkid.SalesPromote.entity.Order;
import net.kingkid.SalesPromote.entity.OrderItem;
import net.kingkid.SalesPromote.entity.TablesName;
import net.kingkid.SalesPromote.mapper.CustomerMapper;
import net.kingkid.SalesPromote.mapper.ItemMapper;
import net.kingkid.SalesPromote.mapper.ShopMapper;
import net.kingkid.SalesPromote.service.IIdentifierService;
import net.kingkid.SalesPromote.service.IShopService;
import net.kingkid.SalesPromote.service.exception.ConcurentException;
import net.kingkid.SalesPromote.service.exception.ServiceException;

/**
 * 处理用户数据的业务层实现类
 */
@Service
public class ShopServiceImpl  extends BaseService 
	implements IShopService {
	@Autowired
	IIdentifierService identifierService;
	@Autowired
	ShopMapper shopMapper;
	@Autowired
	ItemMapper itemMapper;
	@Autowired
	CustomerMapper customerMapper;
	
	
	@Override
	public void addCart(Cart cart) {
		if(cart.getNumber()<1) {
			throw new ServiceException("数量不得小于1！");
		}
		List<Cart> dbCarts=shopMapper.findCartItemByCustomerIdAndItemId(cart.getCustomerId(), cart.getItemId());
		if(dbCarts.size()>0) {
			if(dbCarts.size()<2) {
				cart.setNumber(cart.getNumber()+dbCarts.get(0).getNumber());
				shopMapper.updateCartNumber(cart);
			}else {
				throw new ServiceException("未知错误！");
			}
		} else {
			shopMapper.addCart(cart);
			
		}
		
	
		
	}




	@Override
	public List<CartItem> getAllCartItem(Integer customerId) {
		 List<CartItem>items=shopMapper.getAllCartItem(customerId);

		for (CartItem item : items) {
			item.setPhotoLocation(photoPrefix+item.getPhotoLocation());
			 
		} 
		return items;
	}   


    
 
	@Override
	public void deleteCartItem(Integer customerId, Integer itemId) {
	
		if(customerId==null||itemId==null||customerId==0||itemId==0) {
			System.out.println("error:"+"删除购物车商品未获取到cusomerId("+customerId+") or itemId ("+itemId+")");
			throw new ServiceException("未知错误请刷新页面"); 
			
		}
		List<Integer> itemIds = new ArrayList<Integer>();
		itemIds.add(itemId);
		shopMapper.deleteCartItemByCustomerIdAndItemIds(customerId, itemIds);
	}
      

 

	@Override
	public void updateCartItemNumber(Cart cart, Date updateTime) {
		if(cart.getNumber()<1) { 
			throw new ServiceException("数量不得小于1！");
		}
			if(!itemMapper.findItemByItemId(cart.getItemId()).getUpdateTime().equals(updateTime)) {
			throw new ConcurentException("此商品内容已更改，请刷新页面重试！");
		}
		cart.setNumber(cart.getNumber());     
		shopMapper.updateCartNumber(cart);
	}

	
	@Override
	public void addOrder(Integer customerId, List<Integer> itemIds) {
		
		List<CartItem> selectCartitems=shopMapper.getCartItemByItemIds(customerId,itemIds);
	
		Integer oid=identifierService.getIdForCreate(TablesName.getOrders());
		
		int totalPrice = 0;
		List<OrderItem> orderItems=new ArrayList<OrderItem>() ;
		for(CartItem cartitem:selectCartitems) {  
			OrderItem orderItem=new OrderItem();
			orderItem.setId(identifierService.getIdForCreate(TablesName.getOrderitem())); 
			orderItem.setOrderId(oid);
			
			orderItem.setOrderAmount(cartitem.getNumber());
			if(cartitem.getNumber()<3) {
				totalPrice+=cartitem.getPrice1()*cartitem.getSizeNum()*cartitem.getNumber();
				orderItem.setOrderPrice(cartitem.getPrice1());
				orderItem.setOrderTotal(cartitem.getPrice1()*cartitem.getSizeNum()*cartitem.getNumber());
			}else if(cartitem.getNumber()<6) {
				totalPrice+=cartitem.getPrice2()*cartitem.getSizeNum()*cartitem.getNumber();
				orderItem.setOrderPrice(cartitem.getPrice2());
				orderItem.setOrderTotal(cartitem.getPrice2()*cartitem.getSizeNum()*cartitem.getNumber());
			}else if(cartitem.getNumber()<10) {
				totalPrice+=cartitem.getPrice3()*cartitem.getSizeNum()*cartitem.getNumber();
				orderItem.setOrderPrice(cartitem.getPrice3());
				orderItem.setOrderTotal(cartitem.getPrice3()*cartitem.getSizeNum()*cartitem.getNumber());
			}else {
				totalPrice+=cartitem.getPrice4()*cartitem.getSizeNum()*cartitem.getNumber();
				orderItem.setOrderPrice(cartitem.getPrice4());
				orderItem.setOrderTotal(cartitem.getPrice4()*cartitem.getSizeNum()*cartitem.getNumber());
			}
			
			        
			  
			orderItem.setPhotoLocation(cartitem.getPhotoLocation());
			orderItem.setName(cartitem.getName());
			orderItem.setType(cartitem.getType());
			orderItem.setColor(cartitem.getColor());
			orderItem.setSize(cartitem.getSize());
			orderItem.setPrice1(cartitem.getPrice1());
			orderItem.setPrice2(cartitem.getPrice2());
			orderItem.setPrice3(cartitem.getPrice3());
			orderItem.setPrice4(cartitem.getPrice4());
			orderItem.setState(cartitem.getState());
			orderItem.setRemark(cartitem.getRemark());
			orderItems.add(orderItem);
			
	 
		}
		Order order=new Order();
		order.setCustomerId(customerId);
		order.setId(oid);
		order.setSalesId(customerMapper.findById(order.getCustomerId()).getSalesId());
		order.setState(false);
		order.setPrices(totalPrice);
		
		//当前时间 
				TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
				TimeZone.setDefault(timeZone);
				Timestamp time = new Timestamp(new Date().getTime());
				
		order.setCreateTime(time);
		shopMapper.addOrder(order); 
		shopMapper.addOrderItem(orderItems);
		shopMapper.deleteCartItemByCustomerIdAndItemIds(customerId, itemIds);
	}
   
 
	@Override   
	public List<Order> getAllOrder(Integer customerId) {
		 
		return shopMapper.getAllOrder(customerId);
	}

   

 
	@Override 
	public List<OrderItem> getAllOrderItem(Integer orderId) {
		 
		return shopMapper.getAllOrderItem(orderId); 
	} 
    
     
    
    
	@Override   
	public List<List<Object>> getAllOrderAndOrderItem(Integer customerId) {
		
		
		
		List<List<Object>> orders = new ArrayList<List<Object>>();
		
		 
		for(Order order:shopMapper.getAllOrder(customerId)) {
			List<Object> map = new ArrayList<Object>();
			List<OrderItem> ois=new ArrayList<OrderItem>();
			for(OrderItem orderItem:shopMapper.getAllOrderItem(order.getId())) {
			
				orderItem.setPhotoLocation(photoPrefix+orderItem.getPhotoLocation());
				
				ois.add(orderItem);
			}  
			map.add(order);
			map.add(ois);
			orders.add(map);
		}  
		
	
		
		return orders;
	}

	@Override   
	public OrderItem getOrderItem(Integer oderItemId) {
		OrderItem orderItem =shopMapper.getOrderItem(oderItemId);
		orderItem.setPhotoLocation(photoPrefix+orderItem.getPhotoLocation());
		 
		return orderItem; 
	}




	@Override
	public List<ItemDropdown> getColorDropdown(String itemName,Integer customerId) {
		if(itemName.equals("")||customerId==null) {
			
			throw new ServiceException("未检测到款号或客户信息，请刷新重试！");
		}
		return itemMapper.findColorsByItemName(itemName,customerId);
		 
	}


	 



	
}