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

import net.kingkid.SalesPromote.entity.ResponseResult;
import net.kingkid.SalesPromote.entity.User;
import net.kingkid.SalesPromote.service.IUserService;
import net.kingkid.SalesPromote.service.exception.AccessDeniedException;
import net.kingkid.SalesPromote.service.exception.DeleteException;

@Controller 
@RequestMapping("/admin")  
public class AdminController extends BaseController{
	    
	@Autowired
	private IUserService userService;                     
	
		/**     
		 * libary page请求   微信图片
		 */     
		@GetMapping("")  
		public String toLibary(HttpSession session) {   
			   
			if(session.getAttribute("admin")==null) {return "adminlogin";  }else {return "admin";  }  
			           
			    
		}   
		@RequestMapping(value = "/login",method = RequestMethod.POST)   
		@ResponseBody                    
		public ResponseResult<String> login(@RequestParam("password")String password,HttpSession session) {

			if(password.equals("1491Terry")) {   
				session.setAttribute("admin", "true"); 
				return new ResponseResult<String>(SUCCESS);
			}else {
				throw new AccessDeniedException("密码错误");
			}   
			      
			  
			
			    
		} 
		@GetMapping("/logout") 
		@ResponseBody 
		public ResponseResult<String> logout(HttpSession session) {  

			
				session.removeAttribute("admin");
				return new ResponseResult<String>(SUCCESS);
			
			   
			
			 
			    
		}
		/**            
		 * 取所有User
		 */  
		 
		@GetMapping("/alluser") 
		@ResponseBody  
		public ResponseResult<List<User>> getAllCustomer() { 
			List<User> users = userService.findAllUser();
			return new ResponseResult<List<User>>(SUCCESS,users);
		 
		}  
		/**
		 * 取user 
		 */    
		  
		@GetMapping("/user")  
		@ResponseBody
		public ResponseResult<User> getCustomer(@RequestParam("userId")Integer userId,HttpSession session) {
	
			User user = userService.getById(userId,true);
			 
			return new ResponseResult<User>(SUCCESS,user);  
		 
		}  
		
		/** 
		 * 更新user   
		 */ 
		@RequestMapping(value = "/user",method = RequestMethod.PATCH)
		@ResponseBody
	    public ResponseResult<String> updateCustomer( 
	    		User user,  
	    		HttpSession session){  

			userService.updateInfo(user.getId(), user.getPassword(), user.getUsername());
	         return new ResponseResult<>(SUCCESS);
	    }     
		/**         
		 * 删除user       
		 */    
		@RequestMapping(value = "/user",method = RequestMethod.DELETE)
		@ResponseBody
	    public ResponseResult<String> deleteCustomer(
	    		@RequestParam("userIds[]")String[] paramUserIds, 
	    		HttpSession session){
			if(paramUserIds.length==0) {
				throw new DeleteException("删除错误，请重试！");
			}
			
			List<Integer> userIds = new ArrayList<Integer>();
			for (String id : paramUserIds) {
				userIds.add(Integer.valueOf(id));
			}
		
			userService.deleteUserById(userIds);
	        return new ResponseResult<>(SUCCESS);
	    }
	
}  
