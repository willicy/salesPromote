package net.kingkid.SalesPromote.service;


import java.util.List;

import net.kingkid.SalesPromote.entity.User;
import net.kingkid.SalesPromote.service.exception.DuplicateKeyException;
import net.kingkid.SalesPromote.service.exception.InsertException;
import net.kingkid.SalesPromote.service.exception.PasswordNotMatchException;
import net.kingkid.SalesPromote.service.exception.UserNotFoundException;

/**
 * 处理用户数据的业务层接口
 */
public interface IUserService {

	/**
	 * 用户注册
	 * @param user 用户的注册信息
	 * @return 成功注册的用户数据
	 * @throws DuplicateKeyException
	 * @throws InsertException
	 */
	void register(String username, String password) 
		throws DuplicateKeyException, 
			InsertException;
	
	/**
	 * 用户登录
	 */
	User login(String username, String password) 
			throws UserNotFoundException, 
				PasswordNotMatchException;
	

	void updateInfo(
		Integer uid, String password, 
			String username) ;
	
	
	
	
	
	/**
	 * 根据id获取用户数据
	 * @param id 用户id
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User getById(Integer id,boolean showPassword);

	List<User> findAllUser();
	void deleteUserById(List<Integer> userIds);
	
	
	
	
	
	
	
	
}
