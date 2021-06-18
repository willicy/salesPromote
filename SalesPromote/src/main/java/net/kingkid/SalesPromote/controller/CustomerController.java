package net.kingkid.SalesPromote.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.kingkid.SalesPromote.entity.Customer;
import net.kingkid.SalesPromote.entity.Group;
import net.kingkid.SalesPromote.entity.ResponseResult;
import net.kingkid.SalesPromote.service.ICustomerService;
import net.kingkid.SalesPromote.service.exception.DeleteException;

@Controller
@RequestMapping("/user/customer")  
public class CustomerController extends BaseController{
	   
	@Autowired
	private ICustomerService customerService;   
	
		/**     
		 * libary page请求   微信图片
		 */ 
		@GetMapping("") 
		public String toLibary() {
			return "user/customer"; 
			 
		} 
		
		/**
		 * 取所有Customer
		 */  
		 
		@GetMapping("/allcustomer") 
		@ResponseBody
		public ResponseResult<List<Customer>> getAllCustomer(@RequestParam(value="customer",required=false)String customer,HttpSession session) {
			List<Customer> customers = customerService.getAllCustomer(customer,(Integer)session.getAttribute("uid"));
			return new ResponseResult<List<Customer>>(SUCCESS,customers);
		 
		}  
		/**
		 * 取Customer
		 */    
		  
		@GetMapping("/customer")  
		@ResponseBody
		public ResponseResult<Customer> getCustomer(@RequestParam("customerId")Integer customerId,HttpSession session) {
			
			Customer customer = customerService.getCustomer(customerId);
			 
			return new ResponseResult<Customer>(SUCCESS,customer); 
		 
		}  
		/** 
		 * 新增customer 
		 */
		@RequestMapping(value = "/customer",method = RequestMethod.PUT)
		@ResponseBody 
	    public ResponseResult<String> addCustomer(
	    		Customer customer,  
	    		HttpSession session){ 
			
			customerService.addCustomer(customer, (Integer)session.getAttribute("uid"));
	        return new ResponseResult<>(SUCCESS); 
	    }  
		/** 
		 * 更新customer   
		 */ 
		@RequestMapping(value = "/customer",method = RequestMethod.PATCH)
		@ResponseBody
	    public ResponseResult<String> updateCustomer( 
	    		Customer customer,  
	    		HttpSession session){ 
			customerService.modifyCustomer(customer);
	         return new ResponseResult<>(SUCCESS);
	    }  
		/**         
		 * 删除Customer       
		 */  
		@RequestMapping(value = "/customer",method = RequestMethod.DELETE)
		@ResponseBody
	    public ResponseResult<String> deleteCustomer(
	    		@RequestParam("customerIds[]")String[] paramCustomerIds, 
	    		HttpSession session){
			if(paramCustomerIds.length==0) {
				throw new DeleteException("删除错误，请重试！");
			}
			
			List<Integer> customerIds = new ArrayList<Integer>();
			for (String id : paramCustomerIds) {
				customerIds.add(Integer.valueOf(id));
			}
			customerService.deleteCustomer(customerIds);
	        return new ResponseResult<>(SUCCESS);
	    }
		
		/** 
		 * 取所有Group
		 */  
		  
		@GetMapping("/allgroup")  
		@ResponseBody 
		public ResponseResult<List<Group>> getAllGroup(@RequestParam(value="group",required=false)String group,HttpSession session) {
			List<Group> groups = customerService.getAllGroup(group,(Integer)session.getAttribute("uid"));
			       
			return new ResponseResult<List<Group>>(SUCCESS,groups); 
		
		}   
		/**
		 * 取Group     
		 */    
		        
		@GetMapping("/group")  
		@ResponseBody
		public ResponseResult<Group> getGroup(@RequestParam("groupId")Integer groupId,HttpSession session) { 
			
			Group group = customerService.getGroup(groupId);
			  
			return new ResponseResult<Group>(SUCCESS,group); 
		 
		}
		/** 
		 * 新增Group  
		 */
		@RequestMapping(value = "/group",method = RequestMethod.PUT)
		@ResponseBody 
	    public ResponseResult<String> addGroup(
	    		String name,  
	    		HttpSession session){ 
			
			customerService.addGroup(name, (Integer)session.getAttribute("uid"));
	        return new ResponseResult<>(SUCCESS); 
	    }  
		/** 
		 * 更新group      
		 */ 
		@RequestMapping(value = "/group",method = RequestMethod.PATCH)
		@ResponseBody   
	    public ResponseResult<String> updateGroup( 
	    		Group group,  
	    		HttpSession session){ 
			customerService.modifyGroup(group);
	         return new ResponseResult<>(SUCCESS);
	    }  
		/**         
		 * 删除group       
		 */  
		@RequestMapping(value = "/group",method = RequestMethod.DELETE)
		@ResponseBody
	    public ResponseResult<String> deleteGroup(
	    		@RequestParam("groupIds[]")String[] paramGroupIds, 
	    		HttpSession session){ 
			if(paramGroupIds.length==0) {
				throw new DeleteException("删除错误，请重试！");
			}
			
			List<Integer> groupIds = new ArrayList<Integer>();
			for (String id : paramGroupIds) {
				groupIds.add(Integer.valueOf(id));
			}
			customerService.deleteGroup(groupIds); 
	        return new ResponseResult<>(SUCCESS);
	    }
		
		/**
		 * 取所有CustomerGroup
		 */  
		 
		@GetMapping("/allcustomergroup") 
		@ResponseBody
		public ResponseResult<List<Customer>> getAllCustomerGroup(@RequestParam("groupId")Integer groupId,HttpSession session) {
			List<Customer> customers = customerService.getAllCustomerGroup(groupId);
		
			return new ResponseResult<List<Customer>>(SUCCESS,customers);
		 
		}  
		
		/**
		 * 取所有CustomerGroup里没有的Customer
		 */  
		 
		@GetMapping("/allcustomergroupcustomer") 
		@ResponseBody
		public ResponseResult<List<Customer> > getAllCustomerGroupCustomer(@RequestParam(value="customer",required=false)String customer,@RequestParam("groupId")Integer groupId,HttpSession session) {
			 
			List<Customer> customers = customerService.getAllCustomerGroupCustomer(customer,groupId);
			   
			return new ResponseResult<List<Customer> >(SUCCESS,customers);
		
		}    

		/**
		 *新增CustomerGroup   
		 */  
		  
		@RequestMapping(value = "/customerGroup",method = RequestMethod.PUT)
		@ResponseBody
		public ResponseResult<String> addCustomerGroup(@RequestParam("customerIds[]")String[] paramCustomerIds
		,@RequestParam("groupId")Integer groupId,HttpSession session) {
			if(paramCustomerIds.length==0) {
				throw new DeleteException("新增错误，请重试！");
			}
			
			List<Integer> customerIds = new ArrayList<Integer>();
			for (String id : paramCustomerIds) {
				customerIds.add(Integer.valueOf(id));
			}
			
			
			customerService.addCustomerGroup(customerIds,groupId);
			   
			return new ResponseResult<String>(SUCCESS);
		
		} 
		/**         
		 * 删除CustomerGroup       
		 */  
		@RequestMapping(value = "/customerGroup",method = RequestMethod.DELETE)
		@ResponseBody
	    public ResponseResult<String> deleteCustomerGroup(
	    		@RequestParam("groupId")Integer groupId, @RequestParam("customerId")Integer customerId,
	    		HttpSession session){
			
		
			customerService.deleteCustomerGroup(groupId,customerId);
	        return new ResponseResult<>(SUCCESS);
	    }
		
		/**
		 *新增CustomerItem   
		 */  
		  
		@RequestMapping(value = "/customerItem",method = RequestMethod.PUT) 
		@ResponseBody
		public ResponseResult<String> addCustomerItem(@RequestParam("itemIds[]")String[] paramItemIds
		,@RequestParam("customerId")Integer customerId,HttpSession session) {
			if(paramItemIds.length==0) {
				throw new DeleteException("新增错误，请重试！");
			}
			
			List<Integer> itemIds = new ArrayList<Integer>();
			for (String id : paramItemIds) {
				itemIds.add(Integer.valueOf(id));
			}
			
			
			customerService.addCustomerItem(itemIds,customerId,(String)session.getAttribute("username"));
			        
			return new ResponseResult<String>(SUCCESS);
		
		} 
		/**
		 *新增CustomerItem   
		 */  
		  
		@RequestMapping(value = "/customerItem",method = RequestMethod.DELETE) 
		@ResponseBody
		public ResponseResult<String> deleteCustomerItem(@RequestParam("itemIds[]")String[] paramItemIds
		,@RequestParam("customerId")Integer customerId,HttpSession session) {
			if(paramItemIds.length==0) {
				throw new DeleteException("新增错误，请重试！");
			}
			   
			List<Integer> itemIds = new ArrayList<Integer>();
			for (String id : paramItemIds) {
				itemIds.add(Integer.valueOf(id));
			}
			
			  
			customerService.deleteCustomerItem(itemIds,customerId);
			
			return new ResponseResult<String>(SUCCESS);
		
		} 
}  
