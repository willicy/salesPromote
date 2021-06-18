package net.kingkid.SalesPromote.service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import net.kingkid.SalesPromote.controller.exception.FileEmptyException;
import net.kingkid.SalesPromote.entity.Folder;
import net.kingkid.SalesPromote.entity.Item;
import net.kingkid.SalesPromote.service.exception.DuplicateKeyException;
import net.kingkid.SalesPromote.service.exception.FolderNotFoundException;
import net.kingkid.SalesPromote.service.exception.InsertException;
import net.kingkid.SalesPromote.service.exception.UpdateException;

/**
 * 处理文件夹数据的业务层接口
 */
public interface IFolderService {

	/**
	 * 插入文件夹
	 * 
	 * @throws DuplicateKeyException 重复文件夹名
	 * @throws InsertException 插入错误
	 */
	void addFolder(String name,String createBy) 
		throws DuplicateKeyException, 
			InsertException;
	

	
	/**
	 * 修改文件夹名称
	 * @param fid 文件夹id
	 * @param name 名称
	 * @throws FolderNotFoundException
	 * @throws UpdateException
	 */
	void updateFolderName(
		Integer fid, String name, String updateBy) 
				throws FolderNotFoundException,  
					UpdateException;
	
	/**
	 * 删除文件夹
	 * @param ids 所有文件夹的id
	 * @return 受影响的行数
	 */
	void deleteFolderById(
		List<Integer> ids
	);
	
	/**
	 * 找出全部文件夹数据
	 * @return 匹配的文件夹数据，如果没有匹配的数据，则返回null
	 */
	List<Folder> findAllFolder();
	
	/**
	 * 根据文件夹名获取文件夹数据
	 * @param folderName 
	 * @return 匹配的文件夹数据，如果没有匹配的数据，则返回null
	 */
	List<Folder> findAlikeByFolderName(String folderName);



	/**
	 * 找出全部文件夹内款的数据
	 */
	List<Item> findFolderItemById(Integer id,String itemName);



	/**
	 * 插入款式
	 * @param folderId 
	 * 
	 * @throws DuplicateKeyException 重复款式名
	 * @throws InsertException 插入错误
	 */
	void addItem(Item item,MultipartFile file,Integer folderId, String createBy) 
		throws DuplicateKeyException, 
			InsertException,FileEmptyException;



	/**
	 * 删除款式
	 * @param ids 所有文件夹的id
	*/
	void deleteItemById(List<Integer> ids,Integer folderId);
	
	/**
	 * 复制款到多个文件夹
	*/
	void copyItemToFolders(List<Integer> folderIds,List<Integer> itemIds,String createBy);



	
	
	
	
	
	
	
}
