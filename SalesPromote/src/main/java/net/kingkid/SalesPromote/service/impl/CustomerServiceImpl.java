package net.kingkid.SalesPromote.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kingkid.SalesPromote.entity.Customer;
import net.kingkid.SalesPromote.entity.CustomerGroup;
import net.kingkid.SalesPromote.entity.CustomerItem;
import net.kingkid.SalesPromote.entity.Group;
import net.kingkid.SalesPromote.entity.Item;
import net.kingkid.SalesPromote.entity.TablesName;
import net.kingkid.SalesPromote.mapper.CustomerMapper;
import net.kingkid.SalesPromote.mapper.FolderMapper;
import net.kingkid.SalesPromote.mapper.GroupMapper;
import net.kingkid.SalesPromote.mapper.OrderMapper;
import net.kingkid.SalesPromote.mapper.ShopMapper;
import net.kingkid.SalesPromote.service.ICustomerService;
import net.kingkid.SalesPromote.service.IIdentifierService;
import net.kingkid.SalesPromote.service.exception.DuplicateKeyException;
import net.kingkid.SalesPromote.service.exception.InsertException;
import net.kingkid.SalesPromote.service.exception.PasswordNotMatchException;
import net.kingkid.SalesPromote.service.exception.ServiceException;
import net.kingkid.SalesPromote.service.exception.UserNotFoundException;

/**
 * 处理用户数据的业务层实现类
 */
@Service
public class CustomerServiceImpl  extends BaseService 
	implements ICustomerService {
	@Autowired
	IIdentifierService identifierService;
	@Autowired
	CustomerMapper customerMapper;
	@Autowired
	GroupMapper groupMapper;
	@Autowired 
	FolderMapper folderMapper;
	@Autowired
	private OrderMapper orderMapper;  
	@Autowired
	private ShopMapper shopMapper;  
	
	@Override
	public Customer login(String code, String password)  {
		// 根据参数username查询用户数据
		Customer data = customerMapper.findByCode(code);
		// 判断用户数据是否为null
		if (data == null) {
			// 是：为null，用户名不存在，则抛出UserNotFoundException
			throw new UserNotFoundException(
				"登录失败！您尝试登录的账号不存在！");
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
	public void addCustomer(Customer customer, Integer sales_id) throws DuplicateKeyException, InsertException {
		if(customer.getName().equals("")) {
			throw new ServiceException("姓名不得为空！");
		}
		if(customer.getCode().equals("")) {
			throw new ServiceException("账号不得为空！");
		}
		if(customer.getPassword().equals("")) {
			throw new ServiceException("密码不得为空！");
		}
		if(sales_id==null) {
			throw new ServiceException("获取用户信息错误，请重新登录！"); 
		}  
		if(customerMapper.findByCode(customer.getCode())!=null) {
			throw new ServiceException("账号重复，请尝试其他账号！");
		}
		customer.setId(identifierService.getIdForCreate(TablesName.getCustomer()));
		customer.setSalesId(sales_id);
		customerMapper.addCustomer(customer);
	}  
	@Override
	public void deleteCustomer(List<Integer> ids) {
		  
		customerMapper.deleteCustomerById(ids); 
	
		groupMapper.deleteCustomerGroupByCustomerIds(ids);
		
		
		customerMapper.deleteCustomerItemByCustomerIds(ids); 
		  
		shopMapper.deleteCartItemByCustomerIds(ids); 
		//item必须在order前删除否则无法透过customerIds 删除Item 
		orderMapper.deleteOrderItemByCustomerIds(ids);
		orderMapper.deleteOrderByCustomerIds(ids);
	}  
	
	 
	@Override 
	public void modifyCustomer(Customer customer) {
		if(customer.getName().equals("")) {
			throw new ServiceException("姓名不得为空！");
		}
		if(customer.getCode().equals("")) { 
			throw new ServiceException("账号不得为空！");
		}
		if(customer.getPassword().equals("")) {
			throw new ServiceException("密码不得为空！");
		}
		Customer dbcustomer=customerMapper.findByCode(customer.getCode());
		if(dbcustomer!=null&&dbcustomer.getId()!=customer.getId()) {
			throw new ServiceException("账号重复，请尝试其他账号！");
		}
		
		customerMapper.updateCustomer(customer);
	}

	@Override
	public List<Customer> getAllCustomer(String  customer,Integer sales_id) {
		return customerMapper.findAllBySalesId(customer,sales_id);
		
	}
	@Override
	public Customer getCustomer(Integer id) { 

		return customerMapper.findById(id); 
		
	}

	@Override
	public void addGroup(String name, Integer sales_id) throws DuplicateKeyException, InsertException {
		if(name.equals("")) {
			throw new ServiceException("组名不得为空！");
		}
		
		if(sales_id==null) {
			throw new ServiceException("获取用户信息错误，请重新登录！"); 
		}  
		if(groupMapper.findByNameAndSalesId(name, sales_id).size()>0) {
			throw new ServiceException("组名重复，请尝试其他组名！");
		}
		Group group =new Group(identifierService.getIdForCreate(TablesName.getCusgroup()),name,sales_id);
		
		groupMapper.addGroup(group);
	}  
	@Override
	public List<Group> getAllGroup(String group,Integer sales_id) {
		return groupMapper.findAllBySalesId(group,sales_id);
		
	}
	@Override
	public Group getGroup(Integer id) {
		return groupMapper.findById(id);
		
	}
	@Override
	public void deleteGroup(List<Integer> ids) {
		  
		groupMapper.deleteGroupById(ids);
		
		
		for(Integer id:ids) {
			groupMapper.deleteCustomerGroupByGroupId(id );
		}     
		
		
	} 
	@Override
	public void modifyGroup(Group group) {
		if(group.getName().equals("")) {
			throw new ServiceException("组名不得为空！");
		}
		

		List<Group> dbGroups =groupMapper.findByNameAndSalesId(group.getName(), group.getSalesId());
		
		if(dbGroups.size()>0) {
		
			if(dbGroups.size()>1||(dbGroups.size()==1&&dbGroups.get(0).getId()!=group.getId())) {
				throw new ServiceException("组名重复，请尝试其他组名！");
			}
			  
		}
		groupMapper.updateGroup(group);
	}
	public void addCustomerGroup(List<Integer> customerIds,Integer groupId) throws DuplicateKeyException, InsertException {
		
		List<CustomerGroup> customerGroups=new ArrayList<CustomerGroup>();
		for (Integer customerId : customerIds) {
			customerGroups.add(new CustomerGroup(customerId,groupId));
		}
		groupMapper.addCustomerGroups(customerGroups);
	}        
	@Override
	public List<Customer> getAllCustomerGroup(Integer groupId) {
		List<Integer> customerIds=groupMapper.findCustomerByGroupId(groupId);
		
		List<Customer>customers = null;
		if(customerIds.size()>0) {
			customers=customerMapper.findByIds(customerIds);
		}
		return customers;
		
	}
	@Override
	public List<Customer> getAllCustomerGroupCustomer(String  customer,Integer groupId) {

		List<Integer> customerIds=groupMapper.findCustomerByGroupId(groupId);
		
		if(customerIds.size()<1) {
			customerIds= null;
		}
	
		List<Customer>customers = null;
	
		customers=customerMapper.findNotInByIds(groupMapper.findById(groupId).getSalesId(),customer,customerIds);
			
		
		
		return customers;
		
	}
	@Override
	public void deleteCustomerGroup(Integer groupId,Integer customerId) {
		  
		groupMapper.deleteCustomerGroupByIdAndGroupId(groupId,customerId);  
	}
	
	 
	
	@Override
	public List<Item> findCustomerItemById(Integer id,String itemName) {
	
		List<Item> items = customerMapper.findCustomerItemById(id,itemName);

		for (Item item : items) {
			item.setPhotoLocation(photoPrefix+item.getPhotoLocation()); 
		}
		return items; 
	}
	
	public void addCustomerItem(List<Integer> itemIds,Integer customerId, String createBy) throws DuplicateKeyException, InsertException {
		
		List<CustomerItem> customerItems=new ArrayList<CustomerItem>();
		//当前时间
		TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
		TimeZone.setDefault(timeZone);
		Timestamp time = new Timestamp(new Date().getTime());
		
		for (Integer itemId : itemIds) {
			customerItems.add(new CustomerItem(customerId,itemId,time,createBy));
		}
		customerMapper.addCustomerItems(customerItems);
	}  
	@Override
	public void deleteCustomerItem(List<Integer> itemIds,Integer customerId) {
		  
		customerMapper.deleteCustomerItemByIdAndCustomerId(itemIds,customerId);  
		shopMapper.deleteCartItemByCustomerIdAndItemIds(customerId,itemIds);
	}
	public void send(List<Integer> groupIds, List<Integer> customerIds, List<Integer> itemIds, String createBy) {
		List<Integer> tempCustomerIds;
	
		for (Integer groupId : groupIds) {
			tempCustomerIds=groupMapper.findCustomerByGroupId(groupId);
			for (Integer customerId : tempCustomerIds) {
				if(customerIds.indexOf(customerId)==-1) {
					customerIds.add(customerId);
				}
			}
			
		}
		
		
			
		
		List<CustomerItem> customerItems=new ArrayList<CustomerItem>();
		//当前时间
		TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
		TimeZone.setDefault(timeZone);
		Timestamp time = new Timestamp(new Date().getTime());
		
		for (Integer itemId : itemIds) {
			for (Integer customerId : customerIds) {
				customerItems.add(new CustomerItem(customerId,itemId,time,createBy));
			}
			 
		}
		customerMapper.addCustomerItems(customerItems);
		
		
	}  
	public void sendFolder(List<Integer> groupIds, List<Integer> customerIds, List<Integer> folderIds, String createBy) {
		List<Integer> tempCustomerIds;
	
		for (Integer groupId : groupIds) {
			tempCustomerIds=groupMapper.findCustomerByGroupId(groupId);
			for (Integer customerId : tempCustomerIds) {
				if(customerIds.indexOf(customerId)==-1) {
					customerIds.add(customerId);
				}
			}
			
		}
		
		
		List<Item> tempItemIds;
		List<Integer> itemIds = new ArrayList<Integer>();
		for (Integer folderId : folderIds) {
			tempItemIds=folderMapper.findFolderItemById(folderId,null);
			for (Item item : tempItemIds) {
				if(itemIds.isEmpty()) {
					itemIds.add(item.getId());
					continue;
				}
				if(itemIds.indexOf(item.getId())==-1) {
					itemIds.add(item.getId());
				}
				
				
				
				
			}
			
		}
		
		List<CustomerItem> customerItems=new ArrayList<CustomerItem>();
		//当前时间
		TimeZone timeZone = TimeZone.getTimeZone("GMT+8");     
		TimeZone.setDefault(timeZone);
		Timestamp time = new Timestamp(new Date().getTime());
		
		for (Integer itemId : itemIds) {
			for (Integer customerId : customerIds) {
				customerItems.add(new CustomerItem(customerId,itemId,time,createBy));
			}
			 
		}
		 customerMapper.addCustomerItems(customerItems);
		
		
		}  
}





