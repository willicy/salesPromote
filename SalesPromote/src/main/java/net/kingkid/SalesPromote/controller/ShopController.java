package net.kingkid.SalesPromote.controller;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.kingkid.SalesPromote.controller.exception.GeneralException;
import net.kingkid.SalesPromote.entity.Cart;
import net.kingkid.SalesPromote.entity.CartItem;
import net.kingkid.SalesPromote.entity.Item;
import net.kingkid.SalesPromote.entity.ItemDropdown;
import net.kingkid.SalesPromote.entity.Order;
import net.kingkid.SalesPromote.entity.OrderItem;
import net.kingkid.SalesPromote.entity.ResponseResult;
import net.kingkid.SalesPromote.service.IAlbumService;
import net.kingkid.SalesPromote.service.ICustomerService;
import net.kingkid.SalesPromote.service.IShopService; 
 
@Controller  
@RequestMapping("/shop")  
public class ShopController extends BaseController{ 
	  
	@Autowired    
	private ICustomerService customerService;
	@Autowired 
	private IAlbumService albumService;
	@Autowired 
	
	private IShopService shopService;
	
	@GetMapping("")     
	public String toShop() { 
		return "shop";                
	        
	}  
	@GetMapping("/enshop")     
	public String toEnShop() { 
		return "enshop";               
	        
	}  
	@GetMapping("/allItem") 
	@ResponseBody                     
	public ResponseResult<List<Item>> getAllItem(HttpSession session) {     
		List<Item> items = customerService.findCustomerItemById((Integer)session.getAttribute("cid"),null);
		 
		return new ResponseResult<List<Item>>(SUCCESS,items);  
	    
	}     
	/**            
	 * 取得款           
	 */    
	@GetMapping("/item") 
	@ResponseBody   
	public ResponseResult<Item> getItem(@RequestParam("itemId")Integer itemId) {  
		Item item = albumService.findItem(itemId);
		return new ResponseResult<Item>(SUCCESS,item);     
		          
	}          
	@GetMapping("/itemColorsDropdown")      
	@ResponseBody  
	public ResponseResult<List<ItemDropdown>> getItemColorsDropdown(@RequestParam("itemName")String itemName,HttpSession session) {
		
		return new ResponseResult<List<ItemDropdown>>( SUCCESS,shopService.getColorDropdown(itemName,(Integer)session.getAttribute("cid")));
		    
	}   
	/**                                           
	 * 新增购物车数据           
	 *              
	 */             
	@RequestMapping(value = "/cart",method = RequestMethod.PUT)
	@ResponseBody    
	public ResponseResult<Integer> addCart(@RequestParam("itemId")Integer itemId,@RequestParam("number")Integer number,HttpSession session) {  
	 
		shopService.addCart(new Cart((Integer)session.getAttribute("cid"),itemId,number));
		return new ResponseResult<Integer>(SUCCESS);      
		      
	}  
	/**                       
	 * 删除购物车数据                      
	 *            
	 */           
	@RequestMapping(value = "/cart",method = RequestMethod.DELETE) 
	@ResponseBody    
	public ResponseResult<String> deleteCart(@RequestParam("itemId")Integer itemId,HttpSession session) {  
	
		shopService.deleteCartItem((Integer)session.getAttribute("cid"),itemId);
		return new ResponseResult<String>(SUCCESS);     
		      
	} 
	/**          
	 * 更新购物车数据数量            
	 *                 
	 */           
	@RequestMapping(value = "/cart",method = RequestMethod.PATCH) 
	@ResponseBody    
	public ResponseResult<String> updateCartItemNumber(@RequestParam("itemId")Integer itemId,@RequestParam("number")Integer number,@RequestParam("updateTime")@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date updateTime,HttpSession session) {  
		                        
		shopService.updateCartItemNumber(new Cart((Integer)session.getAttribute("cid"),itemId,number),updateTime);
		 
		return new ResponseResult<String>(SUCCESS);     
		                                                       
	}                             
	@GetMapping("/cart")         
	public String toCart() {           
		return "cart";                
	         
	}   
	@GetMapping("/encart")         
	public String toEnCart() {            
		return "encart";                
	         
	}   
	@GetMapping("/allCartItem")            
	@ResponseBody
	public ResponseResult<List<CartItem>> getAllCartItem(HttpSession session) { 
		return new ResponseResult<List<CartItem>>(SUCCESS,shopService.getAllCartItem((Integer)session.getAttribute("cid"))); 
		
	        
	}  
	@GetMapping("/order")     
	public String toOrder() {   
		return "order";                
	         
	}      
	@GetMapping("/enorder")     
	public String toEnOrder() {   
		return "enorder";                
	         
	}             
	@RequestMapping(value = "/order",method = RequestMethod.PUT) 
	@ResponseBody    
	public ResponseResult<Integer> addOrder(@RequestParam List<String> itemIds,HttpSession session) throws GeneralException {  
		List<Integer> intItemIds=new ArrayList<Integer>();   
	    
	    if(!itemIds.get(0).equals("[]")) {
			for (String string : itemIds) {
				intItemIds.add(Integer.valueOf(string.substring(string.indexOf('"')+1, string.lastIndexOf('"'))));
				  
			}  
		}else {
			throw new GeneralException();
		}
	    
		shopService.addOrder((Integer)session.getAttribute("cid"), intItemIds);
		return new ResponseResult<Integer>(SUCCESS);      
		      
	}  
	@GetMapping("/orders")      
	@ResponseBody 
	public ResponseResult<List<Order>> getOrders(HttpSession session) {
		
		return new ResponseResult<List<Order>>(SUCCESS,shopService.getAllOrder((Integer)session.getAttribute("cid"))); 
	         
	} 
	@GetMapping("/orderItems")     
	@ResponseBody 
	public ResponseResult<List<List<Object>>> getOrderItems(HttpSession session) {
		
		return new ResponseResult<List<List<Object>>>( SUCCESS,shopService.getAllOrderAndOrderItem((Integer)session.getAttribute("cid")));
		    
	} 
	@GetMapping("/orderItem")      
	@ResponseBody 
	public ResponseResult<OrderItem> getOrderItem(@RequestParam("orderItemId")Integer orderItemId) {
		
		return new ResponseResult<OrderItem>( SUCCESS,shopService.getOrderItem(orderItemId));
		    
	}  
 
} 
