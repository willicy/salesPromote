package net.kingkid.SalesPromote.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kingkid.SalesPromote.entity.TablesName;
import net.kingkid.SalesPromote.entity.User;
import net.kingkid.SalesPromote.mapper.UserMapper;
import net.kingkid.SalesPromote.service.IIdentifierService;
import net.kingkid.SalesPromote.service.IUserService;
import net.kingkid.SalesPromote.service.exception.DuplicateKeyException;
import net.kingkid.SalesPromote.service.exception.InsertException;
import net.kingkid.SalesPromote.service.exception.PasswordNotMatchException;
import net.kingkid.SalesPromote.service.exception.UpdateException;
import net.kingkid.SalesPromote.service.exception.UserNotFoundException;

/**
 * 处理用户数据的业务层实现类
 */
@Service
public class UserServiceImpl  extends BaseService 
	implements IUserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private IIdentifierService identifierService;
	@Override
	public void register(String username, String password) throws DuplicateKeyException, InsertException {
		// 根据尝试注册的用户名查询用户数据
		User data = findByUsername(username);
		// 判断查询到的数据是否为null
		if (data == null) {
			
			User user = new User();
			user.setId(identifierService.getIdForCreate(TablesName.getUser()));
			user.setUsername(username);
			user.setPassword(password);
			addnew(user);
			// 返回注册的用户对象
		} else {
			// 否：用户名已被占用，抛出DuplicateKeyException异常
			throw new DuplicateKeyException(
				"注册失败！尝试注册的用户名(" + username + ")已经被占用！");
		}
	}
	
	@Override
	public User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException {
		// 根据参数username查询用户数据
		User data = findByUsername(username);
		// 判断用户数据是否为null
		if (data == null) {
			// 是：为null，用户名不存在，则抛出UserNotFoundException
			throw new UserNotFoundException(
				"登录失败！您尝试登录的用户名不存在！");
		}
		
		
		
		
		// 	判断密码是否匹配
		if (data.getPassword().equals(password)) {
			
			// 返回用户数据
			return data;
		}else {
			// 否：不匹配，密码错误，则抛出PasswordNotMatchException
			throw new PasswordNotMatchException(
				"登录失败！密码错误！");
		}
	}
	
	
	

	
	
	
	@Override
	public User getById(Integer id,boolean showPassword) {
		User data = findById(id);
		if(data!=null&&showPassword==false) {
			data.setPassword(null);
			};
		
		return data;
	}
	
	/**
	 * 获取根据MD5加密的密码
	 * @param srcPassword 原密码
	 * @param salt 盐值
	 * @return 加密后的密码
	 */
	
	
	/**
	 * 插入用户数据
	 * @param user 用户数据
	 * @throws InsertException
	 */
	private void addnew(User user) {
		Integer rows = userMapper.addnew(user);
		if (rows != 1) {
			throw new InsertException(
				"增加用户数据时出现未知错误！");
		}
	}
	
	/**
	 * 更新用户资料
	 */
	public void updateInfo(
			Integer uid, String password,
			String username) {
		Integer rows = userMapper.updateInfo(
				uid, username,password);
		if (rows != 1) {
			throw new UpdateException(
				"更新出现未知错误！");
		}
	}
	

	/**
	 * 查询用户数据
	 */
	public List<User> findAllUser() {
		return userMapper.findAll();
	}

	/**
	 * 根据用户名查询用户数据
	 * @param username 用户名
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	private User findByUsername(String username) {
		return userMapper.findByUsername(username);
	}
	
	/**
	 * 根据用户id查询用户数据
	 * @param id 用户id
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	private User findById(Integer id) {
		return userMapper.findById(id);
	}

	@Override
	public void deleteUserById(List<Integer> userIds) {
		Integer rows = userMapper.deleteUserById(userIds);
		if (rows <1) {
			throw new UpdateException(
				"更新出现未知错误！");
		}
		
	}




	
	
}





