package net.kingkid.SalesPromote.interceptor;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;



import net.kingkid.SalesPromote.service.ICustomerService;
import net.kingkid.SalesPromote.service.IUserService;

/**
 * 登录拦截器
 */

@Component
public class LoginInterceptor 
	implements HandlerInterceptor {
	@Autowired
	private ICustomerService customerService;  
	@Autowired
	private IUserService userService;
	@Override
	public boolean preHandle(
			HttpServletRequest request,  
			HttpServletResponse response, 
			Object handler) 
			throws Exception {
		// 获取Session对象
		HttpSession session 
			= request.getSession();
		Properties prop = null;
		String value = null;
	
		try {
			// 通过Spring中的PropertiesLoaderUtils工具类进行获取
			prop = PropertiesLoaderUtils.loadAllProperties("value.properties");
			// 根据关键字查询相应的值
			value = prop.getProperty("userLoginUrl");
		} catch (IOException e) { 
			e.printStackTrace();
		} 
		//判断是否为ajax请求，默认不是  
	    boolean isAjaxRequest = false; 
	    if(request.getHeader("x-requested-with")!=null && request.getHeader("x-requested-with").equals("XMLHttpRequest")){ 
	      isAjaxRequest = true; 
	    } 
	    if(request.getRequestURI().contains("error")) {
	    	return false;
	    }
		if(request.getRequestURI().contains(value)) {
			// 判断Session中是否存在uid
			if (session.getAttribute("uid") == null||userService.getById((Integer)session.getAttribute("uid"),false)==null) {
				if(isAjaxRequest) {
					response.addHeader("REDIRECT", "REDIRECT");//告诉ajax这是重定向
					response.addHeader("CONTEXTPATH", "/user/login");//重定向地址
					return false;
				}else {
				response.sendRedirect("/user/login");
					return false;
				} 
				
				  
				
			} else {
				
				// 非null，即存在uid，即已经登录 
				return true;    
			}
			
		}else {
			
			// 判断Session中是否存在cid
			if (session.getAttribute("cid") == null||customerService.getCustomer((Integer)session.getAttribute("cid"))==null) {
				if(isAjaxRequest) {
					response.addHeader("REDIRECT", "REDIRECT");//告诉ajax这是重定向
					response.addHeader("CONTEXTPATH", "/login");//重定向地址
					return false;
				}else {
				response.sendRedirect("/login");
				return false;
				}
				
				 
				
			} else {
				// 非null，即存在cid，即已经登录  
				return true;
			}
	
		}
		
		
	}

}
