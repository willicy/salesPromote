package net.kingkid.SalesPromote.service;


import java.util.List;

import net.kingkid.SalesPromote.entity.Customer;
import net.kingkid.SalesPromote.entity.Group;
import net.kingkid.SalesPromote.entity.Item;
import net.kingkid.SalesPromote.service.exception.DuplicateKeyException;
import net.kingkid.SalesPromote.service.exception.InsertException;

/**
 * 处理客户数据的业务层接口
 */
public interface ICustomerService {

	/**
	 * 创建客户
	 */
	void addCustomer(Customer customer,Integer sales_id) 
		throws DuplicateKeyException, 
			InsertException;

	/**
	 * 查找全部客户资料
	 */
	List<Customer> getAllCustomer(String  customer,Integer sales_id);
	
	
	/**
	 * 查找客户资料
	 */
	Customer getCustomer(Integer id);
	
	/**
	 * 更新客户资料
	 */
	void modifyCustomer(Customer customer);
	/**
	 * 删除客户资料
	 */
	void deleteCustomer(List<Integer> customerIds);	
	
	
	/**
	 * 创建组
	 */
	void addGroup(String name,Integer sales_id) 
		throws DuplicateKeyException, 
			InsertException;

	/**
	 * 查找全部组资料
	 * @param group 
	 */
	List<Group> getAllGroup(String group, Integer sales_id);
	/**
	 * 查找组资料
	 */
	Group getGroup(Integer id);
	/**
	 * 更新客户资料
	 */
	void modifyGroup(Group group);
	/**
	 * 删除客户资料
	 */
	void deleteGroup(List<Integer> groupIds);	
	
	/**
	 * 创建客户组
	 */
	void addCustomerGroup(List<Integer> customerIds,Integer groupId) 
		throws DuplicateKeyException, 
			InsertException;

	/**
	 * 查找全部组内客户资料
	 */
	List<Customer> getAllCustomerGroup(Integer groupId);
	/**
	 * 查找全部组内客户资料
	 * @param customer 
	 */
	List<Customer> getAllCustomerGroupCustomer(String customer, Integer groupId);
	/**
	 * 删除组内客户资料
	 */ 
	void deleteCustomerGroup(Integer groupId,Integer customerId);
	
	
	/**
	 * 找出全部客户内款的数据
	 * @param itemName 
	 */
	List<Item> findCustomerItemById(Integer id, String itemName);
	/**
	 * 创建客户款
	 */
	void addCustomerItem(List<Integer> itemIds, Integer customerId, String createBy);
	/**
	 * 删除客户款
	 */
	void deleteCustomerItem(List<Integer> itemIds, Integer customerId);
	/**
	 * send创建客户款
	 */
	void send(List<Integer> groupIds, List<Integer> customerIds, List<Integer> itemIds, String createBy);
	/**
	 * sendFolder创建客户款
	 */
	void sendFolder(List<Integer> groupIds, List<Integer> customerIds, List<Integer> folderIds, String attribute);

	Customer login(String code, String password);
}
