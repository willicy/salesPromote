package net.kingkid.SalesPromote.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.kingkid.SalesPromote.entity.Customer;
import net.kingkid.SalesPromote.entity.CustomerItem;
import net.kingkid.SalesPromote.entity.Item;

/**
 * 处理客户数据的持久层 
 */   
 
public interface CustomerMapper {

	/**
	 * 插入客户数据
	 * @param Customer 客户数据
	 * @return 受影响的行数
	 */
	Integer addCustomer(Customer customer);
	/**
	 * 删除客户数据
	 * @return 受影响的行数
	 */
	Integer deleteCustomerById(@Param("ids")List<Integer> ids);
	/**
	 * 更新客户数据
	 */
	Integer updateCustomer(Customer customer);
	 

	/**
	 * 根据销售id找客户数据
	 */
	List<Customer> findAllBySalesId(@Param("customer")String customer,@Param("salesId") Integer salesId);

	/**
	 * 根据姓名和销售id找客户数据
	 */
	List<Customer> findByNameAndSalesId(@Param("name")String name,@Param("salesId") Integer salesId);
	
	/**
	 * 根据客户id找客户数据
	 */
	Customer findById(@Param("id") Integer id);
	/**
	 * 根据客户code找客户数据
	 */
	Customer findByCode(@Param("code") String code);
	/**
	 * 根据客户ids找客户数据
	 */
	List<Customer> findByIds(@Param("ids") List<Integer> ids);
	/**
	 * 根据客户ids找客户数据
	 */
	List<Customer> findNotInByIds(@Param("salesId") Integer salesId,String customer,@Param("ids") List<Integer> ids);
	
	/**
	 * 找出全部客户内款的数据
	 */
	List<Item> findCustomerItemById(Integer id,String itemName);
	
	/**
	 * 插入客户款数据
	 * @return 受影响的行数
	 */
	void addCustomerItems(List<CustomerItem> customerItems);

	/**
	 * 删除客户款数据
	 * @return 受影响的行数
	 */
	void deleteCustomerItemByIdAndCustomerId(List<Integer> itemIds, Integer customerId);
	/**
	 * 删除客户款数据
	 * @return 受影响的行数
	 */
	void deleteCustomerItemByCustomerIds(List<Integer> customerIds);
	/**
	 * 删除客户款数据
	 * @return 受影响的行数
	 */
	void deleteCustomerItemByItemIds(List<Integer> itemIds);
}



