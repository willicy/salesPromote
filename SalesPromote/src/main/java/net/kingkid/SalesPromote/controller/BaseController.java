package net.kingkid.SalesPromote.controller;



import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import net.kingkid.SalesPromote.controller.exception.FileEmptyException;
import net.kingkid.SalesPromote.controller.exception.FileSizeOutOfLimitException;
import net.kingkid.SalesPromote.controller.exception.FileTypeNotSupportException;
import net.kingkid.SalesPromote.controller.exception.FileUploadException;
import net.kingkid.SalesPromote.controller.exception.GeneralException;
import net.kingkid.SalesPromote.controller.exception.RequestException;
import net.kingkid.SalesPromote.service.exception.AccessDeniedException;
import net.kingkid.SalesPromote.service.exception.AddressNotFoundException;
import net.kingkid.SalesPromote.service.exception.ConcurentException;
import net.kingkid.SalesPromote.service.exception.DeleteException;
import net.kingkid.SalesPromote.service.exception.DuplicateKeyException;
import net.kingkid.SalesPromote.service.exception.InsertException;
import net.kingkid.SalesPromote.service.exception.PasswordNotMatchException;
import net.kingkid.SalesPromote.service.exception.ServiceException;
import net.kingkid.SalesPromote.service.exception.UpdateException;
import net.kingkid.SalesPromote.service.exception.UserNotFoundException;
import net.kingkid.SalesPromote.entity.ResponseResult;
 
/**   
 * 当前项目中所有控制器类的基类
 */ 
public abstract class BaseController {        
	
	
	
	/**    
	 * 正确响应时的代号   
	 */
	public static final Integer SUCCESS = 200;  
     
	@ExceptionHandler({SQLException.class})      
	@ResponseBody
	public ResponseResult<Void> handleSQLException(    
			Exception e) {  
		
	System.out.println("LOG --- [][][] ");
	System.out.println(e);
		return new ResponseResult<>(700, new GeneralException("数据库出错"));
		
	}
	@ExceptionHandler({ServiceException.class,  
		RequestException.class})      
	@ResponseBody
	public ResponseResult<Void> handleException(    
			Exception e) {  
		Integer state = null; 
		
		if (e instanceof DuplicateKeyException) {        
			// 400-违反了Unique约束的异常
			
			state = 400;  
		} else if (e instanceof UserNotFoundException) {
			// 401-用户数据不存在
			state = 401;
		} else if (e instanceof PasswordNotMatchException) {   
			// 402-密码错误
			state = 402;
		} else if (e instanceof AddressNotFoundException) {
			// 403-收货地址数据不存在
			state = 403;
		} else if (e instanceof AccessDeniedException) {
			// 404-访问被拒异常
			state = 404;
		} else if (e instanceof ConcurentException) { 
			// 405-并发异常
			state = 405;
		}  else if (e instanceof InsertException) {
			// 500-插入数据异常
			state = 500;
		} else if (e instanceof UpdateException) {
			// 501-更新数据异常
			state = 501;
		}  else if (e instanceof DeleteException) {
			// 502-删除数据异常
			state = 502;
		} else if (e instanceof FileEmptyException) {
			// 600-上传的文件为空的异常
			state = 600;
		} else if (e instanceof FileSizeOutOfLimitException) {
			// 601-上传的文件超出了限制的异常
			state = 601;
		} else if (e instanceof FileTypeNotSupportException) {
			// 602-上传的文件类型不支持的异常
			state = 602;
		} else if (e instanceof FileUploadException) {
			// 610-文件上传异常
			state = 610;   
		} 

		return new ResponseResult<>(state, e);
		
	}
	
	/**
	 * 从Session中获取uid
	 * @param session HttpSession对象
	 * @return 当前登录的用户的id
	 */
	protected Integer getUidFromSession(HttpSession session) {
		return Integer.valueOf(  
				session.getAttribute("uid").toString()); 
	} 
	
}




