package net.kingkid.SalesPromote.controller;



import java.util.ArrayList; 
import java.util.Date; 
import java.util.List;
   
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
import net.kingkid.SalesPromote.entity.OrderItem;
import net.kingkid.SalesPromote.entity.ResponseResult;   
import net.kingkid.SalesPromote.service.IOrderService;
import net.kingkid.SalesPromote.service.IShopService; 
 
@Controller  
@RequestMapping("/user/allorder")  
public class OrderController extends BaseController{ 
	  
	
	@Autowired 
	private IOrderService orderService;
	@Autowired 
	
	private IShopService shopService;
	
	@GetMapping("")      
	public String toAllorder() { 
		return "user/allorder";               
	        
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
	@GetMapping("/orderItems")        
	@ResponseBody 
	public ResponseResult<List<List<Object>>> getOrderItems(@RequestParam(value="startTime",required=false) @DateTimeFormat(pattern = "yyyy-MM-dd")Date startTime,@RequestParam(value="endTime",required=false)@DateTimeFormat(pattern = "yyyy-MM-dd")Date endTime,@RequestParam(value="state",required=false)Boolean state,@RequestParam(value="customer",required=false)String customer,HttpSession session) {
 
	if(customer=="") {
		customer=null;
	}
		return new ResponseResult<List<List<Object>>>( SUCCESS,orderService.getAllOrderAndOrderItem(startTime,endTime,customer,state,(Integer)session.getAttribute("uid")));
		       
	} 
	 
	@GetMapping("/orderItem")         
	@ResponseBody 
	public ResponseResult<OrderItem> getOrderItem(@RequestParam("orderItemId")Integer orderItemId) {
		 
		return new ResponseResult<OrderItem>( SUCCESS,shopService.getOrderItem(orderItemId));  
		       
	}  
	@RequestMapping(value = "/order",method = RequestMethod.PATCH)       
	@ResponseBody 
	public ResponseResult<String> updateOrderState(@RequestParam("orderId")Integer orderId,@RequestParam(value="state")Boolean state) {
		orderService.updateOrderState(orderId,state);
		return new ResponseResult<String>( SUCCESS);
		     
	} 
} 
 