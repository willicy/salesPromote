package net.kingkid.SalesPromote.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.kingkid.SalesPromote.entity.CustomerGroup;
import net.kingkid.SalesPromote.entity.Group;

/**
 * 处理客户组数据的持久层
 */

public interface GroupMapper {

	/**
	 * 插入组数据
	 * @param Customer 客户数据
	 * @return 受影响的行数
	 */
	Integer addGroup(Group group);
	/**
	 * 删除组数据
	 * @return 受影响的行数
	 */
	Integer deleteGroupById(@Param("ids")List<Integer> ids);
	/**
	 * 更新组数据
	 */
	Integer updateGroup(Group group);
	 

	/**
	 * 根据销售id找组数据
	 * @param group 
	 */
	List<Group> findAllBySalesId(@Param("group")String group, @Param("salesId") Integer salesId);

	/**
	 * 根据组名和销售id找组数据
	 */
	List<Group> findByNameAndSalesId(@Param("name")String name,@Param("salesId") Integer salesId);
	
	/**
	 * 根据组id找组数据
	 */
	Group findById(@Param("id") Integer id);
	/**
	 * 插入客户组数据
	 * @return 受影响的行数
	 */
	Integer addCustomerGroups(List<CustomerGroup> customerGroups);
	/**
	 * 根据组id找客户id
	 */
	List<Integer> findCustomerByGroupId(@Param("groupId") Integer groupId);
	/**
	 * 根据组id和客户id找客户id
	 */
	List<Integer> findCustomerByGroupIdAndCustomerId(@Param("groupId") Integer groupId,@Param("customerId") Integer customerId);
	
	/**
	 * 删除组内客户数据
	 * @return 受影响的行数
	 */
	Integer deleteCustomerGroupByIdAndGroupId(@Param("groupId") Integer groupId,@Param("customerId") Integer customerId);
	/**
	 * 删除组内客户数据
	 * @return 受影响的行数
	 */
	Integer deleteCustomerGroupByGroupId(@Param("groupId") Integer groupId);
	/**
	 * 删除组内客户数据
	 * @return 受影响的行数
	 */
	Integer deleteCustomerGroupByCustomerIds(@Param("customerIds") List<Integer> customerIds);
}



