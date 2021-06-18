package net.kingkid.SalesPromote.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.kingkid.SalesPromote.entity.Folder;
import net.kingkid.SalesPromote.entity.FolderItem;
import net.kingkid.SalesPromote.entity.Item;

/**
 * 处理文件夹数据的持久层
 */

public interface FolderMapper {

	/**
	 * 插入文件夹
	 * @param folder 文件夹数据
	 * @return 受影响的行数
	 */
	Integer addFolder(Folder folder);
	
	/**
	 * 更新文件夹名称
	 * @param fid 文件夹的id
	 * @param name 新名称
	 * @param updateBy 
	 * @param time 
	 * @return 受影响的行数
	 */
	Integer updateFolderName(
		@Param("fid") Integer fid,
		@Param("name") String name, 
		@Param("updateBy") String updateBy, 
		@Param("time") Timestamp time
	);
	
	/**
	 * 删除文件夹
	 * @param ids 所有文件夹的id
	 * @return 受影响的行数
	 */
	Integer deleteFolderById(
		@Param("ids") List<Integer> ids
	);
	
	/**
	 * 根据文件夹名查询文件夹数据
	 * @return 匹配的文件夹数据，如果没有匹配的数据，则返回null
	 */
	Folder findFolderByFolderName(String folderName);
	
	/**
	 * 找出全部文件夹数据
	 * @return 匹配的文件夹数据，如果没有匹配的数据，则返回null
	 */
	List<Folder> findAllFolder();
	
	
	/**
	 * 根据相似文件夹名查询所有文件夹数据
	 * @param foldername 文件夹名
	 * @return 匹配的文件夹数据，如果没有匹配的数据，则返回null
	 */
	List<Folder> findAlikeByFolderName(String folderName);
	
	/**
	 * 找出全部文件夹内款的数据
	 */
	List<Item> findFolderItemById(Integer id);
	
	/**
	 * 插入多个文件夹的款
	 */
	Integer addFolderItems(List<FolderItem> folderItems);
	
	/**
	 * 插入文件夹的款
	 */
	Integer addFolderItem(FolderItem folderItem);

	
	/**
	 * 删除指定文件夹内的款
	 * @param ids 所有款的id
	 * @return 受影响的行数
	 */
	Integer deleteFolderItemByIdAndFolderId(
		@Param("ids") List<Integer> ids,@Param("folderId")Integer folderId
	);
	/**
	 * 删除款
	 * @param ids 所有款的id
	 * @return 受影响的行数
	 */
	Integer deleteFolderItemById(
		@Param("ids") List<Integer> ids
	);
	/**
	 * 透过文件夹id删除款
	 * @param ids 所有款的id
	 * @return 受影响的行数
	 */
	Integer deleteFolderItemByFolderId(
		@Param("id") Integer id
	);
	
}



