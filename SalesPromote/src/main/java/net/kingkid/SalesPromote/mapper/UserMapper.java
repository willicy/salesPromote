package net.kingkid.SalesPromote.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.kingkid.SalesPromote.entity.User;

/**
 * 处理用户数据的持久层
 */

public interface UserMapper {

	/**
	 * 插入用户数据
	 * @param user 用户数据
	 * @return 受影响的行数
	 */
	Integer addnew(User user);
	
	/**
	 * 更新密码
	 * @param uid 用户的id
	 * @param password 新密码
	 * @return 受影响的行数
	 */
	Integer updateInfo(
		@Param("id") Integer uid,
		@Param("username") String username,
		@Param("password") String password
	);
	/**
	 * 查询用户数据
	 */
	List<User> findAll();
	

	/**
	 * 删除用户数据
	 */
	Integer deleteUserById(@Param("ids")List<Integer> ids);
	/**
	 * 根据用户名查询用户数据
	 * @param username 用户名
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User findByUsername(String username);
	
	/**
	 * 根据id查询用户数据
	 * @param id 用户id
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User findById(Integer id);
	
}



