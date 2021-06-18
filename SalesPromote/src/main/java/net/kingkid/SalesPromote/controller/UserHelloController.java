package net.kingkid.SalesPromote.controller;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.kingkid.SalesPromote.entity.ResponseResult;
import net.kingkid.SalesPromote.entity.User;
import net.kingkid.SalesPromote.service.IUserService;

/**
 * 客户端登录Controller
 * 
 */

@Controller
@RequestMapping("/user")
public class UserHelloController extends BaseController{
	
	@Autowired
	private IUserService userService;
	
	/**
	 * Login page请求
	 */
	@GetMapping("/login")  
	public String toUserLogin() {
		
		return "user/login";
		
	}
	/**
	 * Login page请求
	 */
	@GetMapping("")  
	public String toUserIndexLogin() {
		
		return "user/libary";
		
	}
	/**
	 * 登录
	 */
	@PostMapping("/login")
	@ResponseBody
	public ResponseResult<User> login(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			HttpSession session) {
		User user
		= userService.login(username, password);
		session.setAttribute("uid", user.getId());
		session.setAttribute("username", user.getUsername());
		// 返回
		return new ResponseResult<>(SUCCESS, user);
	}

	/**
	 * 登录
	 */
	@GetMapping("/logout") 
	@ResponseBody
	public ResponseResult<User> logout(
			HttpSession session) {  
		session.removeAttribute("uid");
		session.removeAttribute("username");
		// 返回
		return new ResponseResult<>(SUCCESS); 
	}
	/**
	 * Register page请求
	 */
	@GetMapping("/register")
	public String toUserRegister() {
		return "user/register";
		
	}
	
	/**
	 * 注册
	 */
	@PostMapping("/register")
	@ResponseBody
	public ResponseResult<User>register(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			HttpSession session) {
		userService.register(username, password);
		// 返回
		return new ResponseResult<>(SUCCESS);
	}
}
