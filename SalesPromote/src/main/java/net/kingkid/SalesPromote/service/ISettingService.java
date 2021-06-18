package net.kingkid.SalesPromote.service;


import java.util.List;

import net.kingkid.SalesPromote.entity.ItemDropdown;

/**
 * 处理设置数据的业务层接口
 */
public interface ISettingService {


	/**
	 * 删除款码数
	 * @param id 所有款码数的id
	 * @return 受影响的行数
	 */
	void deleteItemSizeById(Integer id
	);
	/**
	 * 根据款ids查询款码数
	 * @return 匹配的款码数数据，如果没有匹配的数据，则返回null
	 */
	List<ItemDropdown> findItemSize();
	
	void addSize(String size, Integer num); 
	
	/**
	 * 删除款品名
	 * @param id 所有品名的id
	 * @return 受影响的行数
	 */
	void deleteItemTypeById( Integer id
	);
	/**
	 * 根据款ids查询款品名
	 * @return 匹配的款品名数据，如果没有匹配的数据，则返回null
	 */
	List<ItemDropdown> findItemType();
	
	void addType(String type);
	
	
	
	
	
}
