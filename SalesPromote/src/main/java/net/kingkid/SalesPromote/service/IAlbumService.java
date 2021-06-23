package net.kingkid.SalesPromote.service;
   

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import net.kingkid.SalesPromote.controller.exception.FileEmptyException;
import net.kingkid.SalesPromote.entity.Item;
import net.kingkid.SalesPromote.entity.ItemPhoto;
import net.kingkid.SalesPromote.service.exception.DuplicateKeyException;
import net.kingkid.SalesPromote.service.exception.InsertException;

/**
 * 处理图库数据的业务层接口
 */
public interface IAlbumService {

	/**    
	 * 插入款式
	 * 
	 * @throws DuplicateKeyException 重复款式名
	 * @throws InsertException 插入错误
	 */
	void addItem(Item item,MultipartFile file,String createBy) 
		throws DuplicateKeyException, 
			InsertException,FileEmptyException;
	  

	
	/**
	 * 修改款式
	*/
	void updateItem(
		Item item,MultipartFile file, String updateBy) ;
				 
	
	/**      
	 * 删除款式  
	*/
	void deleteItemById(List<Integer> ids);
	 
	/**
	 * 找出全部款式数据
	 * @return 匹配的文件夹数据，如果没有匹配的数据，则返回null
	 */
	List<Item> findAllItem(String itemName);


	/**
	 * 根据id找出款式数据
	 * @return 匹配的文件夹数据，如果没有匹配的数据，则返回null
	 */
	Item findItem(Integer id);


	/**
	 * 根据id找出款式细节图片
	 * @return 匹配的文件夹数据，如果没有匹配的数据，则返回null
	 */
	List<ItemPhoto> getAllItemPhoto(Integer id);


	/**   
	 * 款细节图片做处理    
	 */
	void actionItemPhoto(Integer id,Map<Integer,MultipartFile> files, String[] removePhotos);
	
	/**
	 * 根据款式名获取款式数据
	 * @param folderName 
	 * @return 匹配的文件夹数据，如果没有匹配的数据，则返回null
	 
	List<Folder> findAlikeByItemName(String folderName);
*/


	
	
	
	
	
	 
	
	
	
}
