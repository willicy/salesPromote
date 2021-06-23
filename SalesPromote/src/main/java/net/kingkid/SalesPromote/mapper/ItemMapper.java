package net.kingkid.SalesPromote.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.kingkid.SalesPromote.entity.Item;
import net.kingkid.SalesPromote.entity.ItemDropdown;
import net.kingkid.SalesPromote.entity.ItemPhoto;

/**
 * 处理款数据的持久层
 */

public interface ItemMapper {

	/**
	 * 插入款
	 * @param item 款数据
	 * @return 受影响的行数
	 */
	Integer addItem(Item item);
	
	/**
	 * 更新款
	 * @param item 款数据
	 * @return 受影响的行数
	 */
	Integer updateItem(Item item
	);
	
	/**
	 * 删除款
	 * @param ids 所有款的id
	 * @return 受影响的行数
	 */
	Integer deleteItemById(
		@Param("ids") List<Integer> ids
	);
	/**
	 * 根据款ids查询款数据
	 * @return 匹配的款数据，如果没有匹配的数据，则返回null
	 */
	List<Item> findItemByItemIds(@Param("ids") List<Integer> ids);
	/**
	 * 根据款id查询款数据
	 * @return 匹配的款数据，如果没有匹配的数据，则返回null
	 */
	Item findItemByItemId(Integer id);
	/**
	 * 根据款名查询款数据
	 * @return 匹配的款数据，如果没有匹配的数据，则返回null
	 */
	List<Item> findItemByItemName(String itemName,Integer id);
	
	
	/**
	 * 找出全部款数据
	 * @return 匹配的款数据，如果没有匹配的数据，则返回null
	 */
	List<Item> findAllItem(String itemName);
	
	
	/**
	 * 根据相似文件夹名查询所有文件夹数据
	 * @param foldername 文件夹名
	 * @return 匹配的文件夹数据，如果没有匹配的数据，则返回null
	 
	List<Folder> findAlikeByFolderName(String folderName);
	*/
	/**
	 * 删除款码数
	 * @param id 所有款码数的id
	 * @return 受影响的行数
	 */
	Integer deleteItemSizeById(
		@Param("id") Integer id  
	);
	/**
	 * 根据款ids查询款码数
	 * @return 匹配的款码数数据，如果没有匹配的数据，则返回null
	 */
	List<ItemDropdown> findItemSize();  
	
	List<ItemDropdown> findColorsByItemName(String name,Integer cid);
	 
	Integer addSize(ItemDropdown itemSize); 
	
	/** 
	 * 删除款品名
	 */
	Integer deleteItemTypeById(
		@Param("id") Integer id  
	);
	/**
	 * 根据款id查询款品名
	 */
	List<ItemDropdown> findItemType();  

	
	Integer addType(ItemDropdown itemType); 
	/**
	 * 根据款id查询款品细节图片
	 */
	List<ItemPhoto>findAllItemPhoto(Integer id);
	
	Integer addItemPhoto(ItemPhoto itemPhoto);
	
	Integer deleteItemPhoto(ItemPhoto itemPhoto);
	
}



