package net.kingkid.SalesPromote.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.kingkid.SalesPromote.controller.exception.FileEmptyException;
import net.kingkid.SalesPromote.entity.Folder;
import net.kingkid.SalesPromote.entity.FolderItem;
import net.kingkid.SalesPromote.entity.Item;
import net.kingkid.SalesPromote.entity.TablesName;
import net.kingkid.SalesPromote.mapper.FolderMapper;
import net.kingkid.SalesPromote.mapper.ItemMapper;
import net.kingkid.SalesPromote.service.IAlbumService;
import net.kingkid.SalesPromote.service.IFolderService;
import net.kingkid.SalesPromote.service.IIdentifierService;
import net.kingkid.SalesPromote.service.exception.DuplicateKeyException;
import net.kingkid.SalesPromote.service.exception.FolderNotFoundException;
import net.kingkid.SalesPromote.service.exception.InsertException;
import net.kingkid.SalesPromote.service.exception.ServiceException;
import net.kingkid.SalesPromote.service.exception.UpdateException;

/**
 * 处理用户数据的业务层实现类
 */
@Service 
public class FolderServiceImpl extends BaseService
	implements IFolderService {
	@Autowired
	private IAlbumService albumService;
	
	@Autowired
	private IIdentifierService identifierService;
	@Autowired
	private FolderMapper folderMapper;
	@Autowired
	private ItemMapper itemMapper;
	

	@Override
	public void addFolder(String name, String createBy) throws DuplicateKeyException, InsertException {

		
		if(folderMapper.findFolderByFolderName(name)!= null) {
			throw new DuplicateKeyException("创建失败，尝试创建的文件名已存在！");
		}
		
		
		Folder folder = new Folder();
		//当前时间
		TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
		TimeZone.setDefault(timeZone);
		Timestamp time = new Timestamp(new Date().getTime());
		
		folder.setName(name);
		folder.setId(identifierService.getIdForCreate(TablesName.getLibFolder()));
		folder.setCreateBy(createBy);
		folder.setCreateTime(time);
		folder.setUpdateBy(createBy);
		folder.setUpdateTime(time);
		folderMapper.addFolder(folder);
		
	}

	@Override
	public void updateFolderName(Integer fid, String name,String updateBy) throws FolderNotFoundException, UpdateException {
		
		if(folderMapper.findFolderByFolderName(name)!= null) {
			throw new DuplicateKeyException("命名失败，尝试命名的文件名已存在！");
		}
		
		//当前时间
		TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
		TimeZone.setDefault(timeZone);
		Timestamp time = new Timestamp(new Date().getTime());
		
		folderMapper.updateFolderName(fid, name,updateBy,time);
		
	}
	
	@Override
	public void deleteFolderById(List<Integer> ids) {
		for(Integer id :ids) {
			folderMapper.deleteFolderItemByFolderId(id);
		}
		folderMapper.deleteFolderById(ids);
	} 

	@Override
	public List<Folder> findAllFolder() {     
		  
		return folderMapper.findAllFolder();
	} 

	@Override
	public List<Folder> findAlikeByFolderName(String folderName) {
		return findAlikeByFolderName(folderName);
	}

	@Override
	public List<Item> findFolderItemById(Integer id) {
		List<Item> items = folderMapper.findFolderItemById(id);
		
		for (Item item : items) {
			item.setPhotoLocation(photoPrefix+item.getPhotoLocation()); 
		}
		return items;  
	}

	@Override
	public void addItem(Item item, MultipartFile file,Integer folderId, String createBy)
			throws DuplicateKeyException, InsertException, FileEmptyException {
		albumService.addItem(item, file, createBy);
		int itemId = 0;
		for (Item returnItem: itemMapper.findItemByItemName(item.getName(), 0)) {
			itemId=returnItem.getId();
		}
		if(itemId==0) {
			throw new InsertException("插入数据失败，请重试！");
		}
		//当前时间
				TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
				TimeZone.setDefault(timeZone);
				Timestamp time = new Timestamp(new Date().getTime());
		FolderItem folderItem = new FolderItem(folderId, itemId, time, createBy);
		folderMapper.addFolderItem(folderItem);
	}
	@Override
	public void deleteItemById(List<Integer> ids,Integer folderId) {
		 
		folderMapper.deleteFolderItemByIdAndFolderId(ids,folderId);  
	}
	
	@Override
	public void copyItemToFolders(List<Integer> folderIds,List<Integer> itemIds,String createBy) {
		if(folderIds.size()==0||itemIds.size()==0) {
			throw new ServiceException("服务器查找不到选取数据，请重试！");
		}
		//当前时间
		TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
		TimeZone.setDefault(timeZone);
		Timestamp time = new Timestamp(new Date().getTime());
		
		List<FolderItem> FolderItems=new ArrayList<FolderItem>();
		for (Integer folderId : folderIds) {
			for (Integer itemId : itemIds) {
				FolderItems.add(new FolderItem(folderId,itemId,time,createBy));
			}
			
		}
		
		folderMapper.addFolderItems(FolderItems);
	}




	
	
}





