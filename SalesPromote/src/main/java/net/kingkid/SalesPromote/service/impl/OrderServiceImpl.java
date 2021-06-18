package net.kingkid.SalesPromote.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kingkid.SalesPromote.entity.Cart;
import net.kingkid.SalesPromote.entity.Order;
import net.kingkid.SalesPromote.entity.OrderItem;
import net.kingkid.SalesPromote.mapper.CustomerMapper;
import net.kingkid.SalesPromote.mapper.ItemMapper;
import net.kingkid.SalesPromote.mapper.OrderMapper;
import net.kingkid.SalesPromote.service.IIdentifierService;
import net.kingkid.SalesPromote.service.IOrderService;
import net.kingkid.SalesPromote.service.exception.ConcurentException;
import net.kingkid.SalesPromote.service.exception.ServiceException;

/**
 * 处理用户数据的业务层实现类
 */
@Service
public class OrderServiceImpl extends BaseService  
	implements IOrderService {
	@Autowired
	IIdentifierService identifierService;
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	ItemMapper itemMapper;
	@Autowired
	CustomerMapper customerMapper;
	
	



	
 
	@Override
	public void deleteCartItem(Integer customerId, Integer itemId) {
	
		if(customerId==null||itemId==null||customerId==0||itemId==0) {
			System.out.println("error:"+"删除购物车商品未获取到cusomerId("+customerId+") or itemId ("+itemId+")");
			throw new ServiceException("未知错误请刷新页面"); 
			
		}
		List<Integer> itemIds = new ArrayList<Integer>();
		itemIds.add(itemId);
		//shopMapper.deleteCartItemByCustomerIdAndItemIds(customerId, itemIds);
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
	//	shopMapper.updateCartNumber(cart);
	}

	
	
 
	@Override   
	public List<Order> getAllOrder(Date startTime,Date endTime,String customer,Boolean payment,Integer salesId) {
		 String statement = "";
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 
		 if(customer!=null) {
			 statement +=" AND customer.name LIKE '%"+customer+"%'";    
		 }
		 if(payment!=null) {
			 statement +=" AND orders.state="+payment;
		 }
		 if(startTime!=null) {
			
                        
			
			 statement +=" AND orders.create_time>="+String.valueOf("'"+format.format(startTime)+"'") ;
		 }
		 if(endTime!=null) {
			 statement +=" AND orders.create_time<="+String.valueOf("'"+format.format(endTime)+"'");
		 }
	
		return statement.equals("")?orderMapper.getAllOrder(salesId):orderMapper.getAllFilterOrder(salesId,statement);
	} 

   

 
	@Override 
	public List<OrderItem> getAllOrderItem(Integer orderId) {
		 
		return orderMapper.getAllOrderItem(orderId); 
	} 
    
     
    
    
	@Override   
	public List<List<Object>> getAllOrderAndOrderItem(Date startTime,Date endTime,String customer,Boolean payment,Integer salesId) {
		
		
		
		List<List<Object>> orders = new ArrayList<List<Object>>();
		
		
		for(Order order:getAllOrder(startTime,endTime,customer,payment,salesId)) {
			List<Object> map = new ArrayList<Object>();
			List<OrderItem> ois=new ArrayList<OrderItem>();
			for(OrderItem orderItem:orderMapper.getAllOrderItem(order.getId())) {
			
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
	//	OrderItem orderItem =shopMapper.getOrderItem(oderItemId);
	//	orderItem.setPhotoLocation("/images/products/"+orderItem.getPhotoLocation());
		 
		return null;//orderItem; 
	}




	@Override
	public void updateOrderState(Integer id, Boolean state) {
		
		if(id==null||state==null) {
			throw new ServiceException("未知错误,请刷新重试!");
		}
		orderMapper.updateOrderState(id, state);
		
	}


	 



	
}