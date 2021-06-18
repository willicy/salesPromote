package net.kingkid.SalesPromote.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kingkid.SalesPromote.entity.ItemDropdown;
import net.kingkid.SalesPromote.entity.TablesName;
import net.kingkid.SalesPromote.mapper.ItemMapper;
import net.kingkid.SalesPromote.service.IIdentifierService;
import net.kingkid.SalesPromote.service.ISettingService;
import net.kingkid.SalesPromote.service.exception.ServiceException;

/**
 * 处理用户数据的业务层实现类
 */
@Service 
public class SettingServiceImpl  extends BaseService 
	implements ISettingService {

	@Autowired
	private IIdentifierService identifierService;
	
	@Autowired
	private ItemMapper itemMapper;
	

	
	@Override
	public void deleteItemSizeById(Integer id) {
		itemMapper.deleteItemSizeById(id);
	} 

	@Override
	public List<ItemDropdown> findItemSize() {     
		  
		return itemMapper.findItemSize();
	} 

	@Override
	public void addSize(String size,Integer num) {
		if(size.equals("")) {
			throw new ServiceException("不得为空！");
		}
		
		for (ItemDropdown id : itemMapper.findItemSize()) {
			if(id.getData().equals(size)) {
				throw new ServiceException("码数重复，请重试！");
			}
		}
		
		ItemDropdown itemSize =new ItemDropdown(identifierService.getIdForCreate(TablesName.getItemsize()),size,num);
		
		itemMapper.addSize(itemSize);
	}  


	@Override
	public void deleteItemTypeById(Integer id) {
		itemMapper.deleteItemTypeById(id);
	} 

	@Override
	public List<ItemDropdown> findItemType() {     
		  
		return itemMapper.findItemType();
	} 

	@Override
	public void addType(String type) {
		if(type.equals("")) {
			throw new ServiceException("不得为空！");
		}
		
		for (ItemDropdown id : itemMapper.findItemType()) {
			if(id.getData().equals(type)) { 
				throw new ServiceException("品名重复，请重试！");
			}
		}
		 
		ItemDropdown itemType =new ItemDropdown(identifierService.getIdForCreate(TablesName.getItemtype()),type);
		
		itemMapper.addType(itemType);
	}  
	
	
}





