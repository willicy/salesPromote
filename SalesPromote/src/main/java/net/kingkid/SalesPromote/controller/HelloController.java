package net.kingkid.SalesPromote.controller;

 
 
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.kingkid.SalesPromote.entity.Customer;
import net.kingkid.SalesPromote.entity.ResponseResult;
import net.kingkid.SalesPromote.entity.User;   
import net.kingkid.SalesPromote.service.ICustomerService;
  
@Controller
public class HelloController extends BaseController{
	   
	@Autowired
	private ICustomerService customerService; 
	@GetMapping("/login")     
	public String toLogin() {     
		return "login";                 
	       
	}       
	@GetMapping("")       
	public String toIndexLogin() { 
		return "shop";                 
	       
	}   
	@GetMapping("/enlogin")      
	public String toEnLogin() { 
		return "enlogin";                 
	      
	}  
	/**         
	 * 登录
	 */     
	@PostMapping("/login")
	@ResponseBody
	public ResponseResult<Customer> login(
			@RequestParam("code") String code,      
			@RequestParam("password") String password,
			HttpSession session) {
		Customer client
		= customerService.login(code, password);
		session.setAttribute("cid", client.getId());
		session.setAttribute("clientname", client.getName());
		// 返回
		return new ResponseResult<>(SUCCESS, client);
	}

	/** 
	 * 登录
	 */
	@GetMapping("/logout") 
	@ResponseBody
	public ResponseResult<User> logout(   
			HttpSession session) {  
		session.removeAttribute("cid");
		session.removeAttribute("clientname");
		// 返回
		return new ResponseResult<>(SUCCESS); 
	}

	
}
