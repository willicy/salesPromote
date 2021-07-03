package net.kingkid.SalesPromote.service.impl;




import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qcloud.cos.model.DeleteObjectsRequest;

import net.kingkid.SalesPromote.controller.exception.FileEmptyException;
import net.kingkid.SalesPromote.controller.exception.FileSizeOutOfLimitException;
import net.kingkid.SalesPromote.controller.exception.FileTypeNotSupportException;
import net.kingkid.SalesPromote.entity.Item;
import net.kingkid.SalesPromote.entity.ItemPhoto;
import net.kingkid.SalesPromote.entity.TablesName;
import net.kingkid.SalesPromote.mapper.CustomerMapper;
import net.kingkid.SalesPromote.mapper.FolderMapper;
import net.kingkid.SalesPromote.mapper.ItemMapper;
import net.kingkid.SalesPromote.mapper.ShopMapper;
import net.kingkid.SalesPromote.service.IAlbumService;
import net.kingkid.SalesPromote.service.ICosService;
import net.kingkid.SalesPromote.service.IIdentifierService;
import net.kingkid.SalesPromote.service.exception.ConcurentException;
import net.kingkid.SalesPromote.service.exception.DuplicateKeyException;
import net.kingkid.SalesPromote.service.exception.InsertException;
import net.kingkid.SalesPromote.service.exception.ServiceException;

/**
 * 处理资料数据的业务层实现类
 */
@Service 
public class AlbumServiceImpl extends BaseService 
	implements IAlbumService {
	/**
	 * 上传文件的最大大小
	 */
	private static final long FILE_MAX_SIZE = 10 * 1024 * 1024;
	/**
	 * 允许上传的文件类型
	 */
	private static final List<String> FILE_CONTENT_TYPES 
		= new ArrayList<>();
	
	/**
	 * 初始化允许上传的文件类型的集合
	 */
	static {
		FILE_CONTENT_TYPES.add("image/jpeg");
		FILE_CONTENT_TYPES.add("image/png");
	}
	@Autowired
	private FolderMapper folderMapper;
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private IIdentifierService identifierService;
	@Autowired
	private ICosService cosService;
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private ShopMapper shopMapper; 

	


	@Override
	public void addItem(Item item,MultipartFile file,String createBy) throws DuplicateKeyException, InsertException, FileEmptyException{
		
		
	
		 // 检查是否存在上传文件 > file.isEmpty()
		if (file.isEmpty()) {
			// 抛出异常：文件不允许为空
			throw new FileEmptyException(
				"没有选择上传的文件，或选中的文件为空！");
		}
		
		if (file.getSize() > FILE_MAX_SIZE) {
			throw new FileSizeOutOfLimitException("文件大小超出限制，需小于10MB");
		}


		if (!FILE_CONTENT_TYPES.contains(
				file.getContentType())) {
			throw new FileTypeNotSupportException("文件类型错误，允许类型为JPG/PNG");
		}


		
		
		
		if(file.getSize() != 0 && !"".equals(file.getName())){
			
			

			final String fileName = System.currentTimeMillis() + "" + (new Random().nextInt(90000000) + 10000000) +"_"+file.getOriginalFilename();
			 
			cosService.cosUpload(file, fileName);
				
			item.setPhotoLocation(fileName);
			item.setId(identifierService.getIdForCreate(TablesName.getLibItem()));
			 
			//当前时间
			TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
			TimeZone.setDefault(timeZone);
			Timestamp time = new Timestamp(new Date().getTime());
			item.setUpdateTime(time);
			item.setUpdateBy(createBy);
			item.setCreateTime(time);
			item.setCreateBy(createBy);
			if(item.getState().equals("")) {
				item.setState("...");
			}
			if(item.getRemark().equals("")) {
				item.setRemark("...");
			}
			itemMapper.addItem(item);
		}

      
		
	}

	
	@Override
	public void deleteItemById(List<Integer> ids) {
		
		
		//删除图片
		List<Item> items=itemMapper.findItemByItemIds(ids);
		ArrayList<DeleteObjectsRequest.KeyVersion> photoLocations=new ArrayList<DeleteObjectsRequest.KeyVersion>();
		for (Item item : items) {
			photoLocations.add((new DeleteObjectsRequest.KeyVersion(item.getPhotoLocation())));
		}
		cosService.cosBatchDelete(photoLocations);
		
		//删除款
		itemMapper.deleteItemById(ids);  
		//删除文件夹存储的款数据
		folderMapper.deleteFolderItemById(ids);
		//删除客户存储的款数据
		customerMapper.deleteCustomerItemByItemIds(ids);
		//删除客户购物车存储的款数据
		shopMapper.deleteCartItemByItemIds(ids);
	}
	 
	@Override 
	public void updateItem(Item item,MultipartFile file, String updateBy) {
		 
		Item dbItem = itemMapper.findItemByItemId(item.getId()); 
		
		if(item.getUpdateTime().equals(dbItem.getUpdateTime()) ) {
			
			
				if(file.getSize()==0||file.isEmpty()) { 
					item.setPhotoLocation(dbItem.getPhotoLocation());
				}else {  					 // 检查是否存在上传文件 > file.isEmpty() 
					
					if (file.getSize() > FILE_MAX_SIZE) {
						throw new FileSizeOutOfLimitException("文件大小超出限制，需小于10MB");
					}


					if (!FILE_CONTENT_TYPES.contains(
							file.getContentType())) {
						throw new FileTypeNotSupportException("文件类型错误，允许类型为JPG/PNG");
					}

					
					if(file.getSize() != 0 && !"".equals(file.getName())){
							
							

	
							final String fileName = System.currentTimeMillis() + "" + (new Random().nextInt(90000000) + 10000000) +"_"+file.getOriginalFilename();
							
								cosService.cosUpload(file, fileName);
								
						
						
								item.setPhotoLocation(fileName);
				        
								cosService.cosDelete(dbItem.getPhotoLocation());
						
				       
				       
					}
				
				}
				if(item.getType()==null) {
					item.setType(dbItem.getType());
				}
				if(item.getSize()==null) {        
					item.setSize(dbItem.getSize());
				}
				//当前时间 
				TimeZone timeZone = TimeZone.getTimeZone("GMT+8"); 
				TimeZone.setDefault(timeZone); 
				Timestamp time = new Timestamp(new Date().getTime());
				item.setUpdateTime(time);
				item.setUpdateBy(updateBy); 
				itemMapper.updateItem(item); 
			
			
			
		}else {
			throw new ConcurentException("此数据以被其他人修改，请刷新页面！");
		}
	}    
	@Override
	public List<Item> findAllItem(String itemName) {
		List<Item> items = new ArrayList<Item>();
		items=itemMapper.findAllItem(itemName);
		for (Item item : items) {
			item.setPhotoLocation(photoPrefix+item.getPhotoLocation());
		}
		return items;
	}


	@Override
	public Item findItem(Integer id) {
		
		Item item=itemMapper.findItemByItemId(id);
		
		if(item==null) {
			throw new ServiceException("未找到数据，请重试！");
		}
	
		
		item.setPhotoLocation(photoPrefix+item.getPhotoLocation());
		return item;
	}


	@Override  
	public List<ItemPhoto> getAllItemPhoto(Integer id) {
		List<ItemPhoto> itemPhotos=itemMapper.findAllItemPhoto(id);
		for (ItemPhoto item : itemPhotos) {
			
			item.setPhotoLocation(photoPrefix+item.getPhotoLocation());
		
		}
		return itemPhotos;
	}


	@Override
	public void actionItemPhoto(Integer id,Map<Integer,MultipartFile> files, String[] removePhotos) {
		List<ItemPhoto> itemPhotos=itemMapper.findAllItemPhoto(id);
		Map<Integer,String> deleteList = new HashMap<Integer,String>();
		
		Map<Integer,MultipartFile> createList = new HashMap<Integer,MultipartFile>();
		Map<Integer,String> deleteForUpdateList = new HashMap<Integer,String>();
		Map<Integer,MultipartFile> createForUpdateList = new HashMap<Integer,MultipartFile>();
		//取出要删除的
		for (ItemPhoto itemPhoto : itemPhotos) {
			
			for(String index:removePhotos) {
				if(index.equals(String.valueOf(itemPhoto.getPriority()))) {
					deleteList.put(itemPhoto.getPriority(),itemPhoto.getPhotoLocation());
				}
			}
			
			
		}
		
		
		boolean flag=false;
		for(int i=1;i<6;i++) {
			flag=false;
			if(files.containsKey(i)) {
				
				for (ItemPhoto itemPhoto : itemPhotos) {
					//取出要更新的
					if(itemPhoto.getPriority()==i) {
						createForUpdateList.put(i,files.get(i));
						deleteForUpdateList.put(i,itemPhoto.getPhotoLocation());
						flag=true;
					}
					
					
				}
				//取出要新增的
				if(flag==false) {
					createList.put(i,files.get(i));
				}
				
			}
			
			
		}
		
		if(!deleteList.isEmpty()) {deleteItemPhoto(deleteList,id);}
		if(!createList.isEmpty()) {createItemPhoto(createList,id);}
		if(!createForUpdateList.isEmpty()&&!deleteForUpdateList.isEmpty()) {updateItemPhoto(createForUpdateList,deleteForUpdateList,id);}
		
		
		
		
	}

	  
	void deleteItemPhoto(Map<Integer,String> deleteList,Integer id) {
		
		for (Integer integer : deleteList.keySet()) {
			String photoLocation = deleteList.get(integer);
			cosService.cosDelete(photoLocation);
			itemMapper.deleteItemPhoto(new ItemPhoto(id,photoLocation,integer));
		}
	}
	void createItemPhoto(Map<Integer,MultipartFile> createList,Integer id) {
		
		        
		for (Integer integer : createList.keySet()) {
			MultipartFile file = createList.get(integer);
			if (file.getSize() > FILE_MAX_SIZE) {
				throw new FileSizeOutOfLimitException("文件大小超出限制，需小于10MB");
			}


			if (!FILE_CONTENT_TYPES.contains(
					file.getContentType())) {
				throw new FileTypeNotSupportException("文件类型错误，允许类型为JPG/PNG"); 
			}
			if(file.getSize() != 0 && !"".equals(file.getName())){
				String fileName = System.currentTimeMillis() + "" + (new Random().nextInt(90000000) + 10000000) +"_"+file.getOriginalFilename();
				cosService.cosUpload(file,fileName);
				itemMapper.addItemPhoto(new ItemPhoto(id,fileName,integer));
			}else {
				throw new FileEmptyException("文件大小为空或未命名，请重试！");
			}
		}   
		
	}  
	void updateItemPhoto(Map<Integer,MultipartFile> createForUpdateList,Map<Integer,String> deleteForUpdateList,Integer id) {
		
		deleteItemPhoto(deleteForUpdateList,id);
		createItemPhoto(createForUpdateList,id);
		
		
	}

      


	
	
	
}




