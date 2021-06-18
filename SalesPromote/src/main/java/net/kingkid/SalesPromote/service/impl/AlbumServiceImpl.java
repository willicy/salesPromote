package net.kingkid.SalesPromote.service.impl;


import java.io.IOException;
import java.io.InputStream;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.DeleteObjectsRequest;
import com.qcloud.cos.model.DeleteObjectsResult;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;

import net.kingkid.SalesPromote.controller.exception.FileEmptyException;
import net.kingkid.SalesPromote.controller.exception.FileSizeOutOfLimitException;
import net.kingkid.SalesPromote.controller.exception.FileTypeNotSupportException;
import net.kingkid.SalesPromote.controller.exception.FileUploadException;
import net.kingkid.SalesPromote.entity.Item;
import net.kingkid.SalesPromote.entity.TablesName;
import net.kingkid.SalesPromote.mapper.CustomerMapper;
import net.kingkid.SalesPromote.mapper.FolderMapper;
import net.kingkid.SalesPromote.mapper.ItemMapper;
import net.kingkid.SalesPromote.mapper.ShopMapper;
import net.kingkid.SalesPromote.service.IAlbumService;
import net.kingkid.SalesPromote.service.IIdentifierService;
import net.kingkid.SalesPromote.service.exception.ConcurentException;
import net.kingkid.SalesPromote.service.exception.DuplicateKeyException;
import net.kingkid.SalesPromote.service.exception.InsertException;
import net.kingkid.SalesPromote.service.exception.ServiceException;

/**
 * 处理用户数据的业务层实现类
 */
@Service 
public class AlbumServiceImpl extends BaseService 
	implements IAlbumService {
	static final String BUCKET_NAME = "kingkidbucket-1305101123";
	static final String SECRET_KEY = "f6IFyTjkX5QZT8aooa7RXP3TedOuBD2d";

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

		String secretId = "AKIDZ1GH4cw2tXwOgkZ9UaR2OVNzMUN0OYVJ";
		
		COSCredentials cred = new BasicCOSCredentials(secretId, SECRET_KEY);
		// 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
		Region region = new Region("ap-hongkong");
		// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
		
		ClientConfig clientConfig = new ClientConfig(region);
		// 这里建议设置使用 https 协议
		clientConfig.setHttpProtocol(HttpProtocol.https);
		// 3 生成 cos 客户端。
		COSClient cosClient = new COSClient(cred, clientConfig);
		
		if(file.getSize() != 0 && !"".equals(file.getName())){
			
			
//			 getInputStream()返回一个InputStream以从中读取文件的内容。通过此方法就可以获取到流
		    InputStream multipartFileInputStream = null;
			try {
				multipartFileInputStream = file.getInputStream();
			} catch (IOException e) {
				
						
				cosClient.shutdown();
				e.printStackTrace();
				throw new FileUploadException("上传失败，请重试！");
			}
			final String fileName = System.currentTimeMillis() + "" + (new Random().nextInt(90000000) + 10000000) +"_"+file.getOriginalFilename();
			   // 创建上传Object的Metadata
			 ObjectMetadata meta = new ObjectMetadata();
			 // 必须设置ContentLength 
		      meta.setContentLength(file.getSize()); 
				// 指定文件将要存放的存储桶
				
				// 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
				String key = fileName;
				PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, key, multipartFileInputStream,meta);
				
				cosClient.putObject(putObjectRequest);
				cosClient.shutdown();
				
				
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
		
		String secretId = "AKIDZ1GH4cw2tXwOgkZ9UaR2OVNzMUN0OYVJ";
		
		COSCredentials cred = new BasicCOSCredentials(secretId, SECRET_KEY);
		// 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
		Region region = new Region("ap-hongkong");
		// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
		
		ClientConfig clientConfig = new ClientConfig(region);
		// 这里建议设置使用 https 协议
		clientConfig.setHttpProtocol(HttpProtocol.https);
		// 3 生成 cos 客户端。
		COSClient cosClient = new COSClient(cred, clientConfig);
		
		
		DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(BUCKET_NAME);
		// 设置要删除的key列表, 最多一次删除1000个
		ArrayList<DeleteObjectsRequest.KeyVersion> keyList = new ArrayList<DeleteObjectsRequest.KeyVersion>();
		// 传入要删除的文件名
		List<Item> items=itemMapper.findItemByItemIds(ids);
		for (Item item : items) {
			keyList.add(new DeleteObjectsRequest.KeyVersion(item.getPhotoLocation()));
		}	
		
		deleteObjectsRequest.setKeys(keyList);
		// 批量删除文件
		try {
		   DeleteObjectsResult deleteObjectsResult = cosClient.deleteObjects(deleteObjectsRequest);
		  // List<DeleteObjectsResult.DeletedObject> deleteObjectResultArray = 
				   deleteObjectsResult.getDeletedObjects();
		} catch (MultiObjectDeleteException mde) { // 如果部分删除成功部分失败, 返回MultiObjectDeleteException
		   //List<DeleteObjectsResult.DeletedObject> deleteObjects = mde.getDeletedObjects();
		   //List<MultiObjectDeleteException.DeleteError> deleteErrors = mde.getErrors();
		   mde.printStackTrace();
		} catch (CosServiceException e) { // 如果是其他错误，例如参数错误， 身份验证不过等会抛出 CosServiceException
		   e.printStackTrace();
		   throw e;
		} catch (CosClientException e) { // 如果是客户端错误，例如连接不上COS
		   e.printStackTrace();
		   throw e;
		}finally {
			cosClient.shutdown();
		}
		
		
		
		
		
		
		
		
		
		
		

		itemMapper.deleteItemById(ids);  
		folderMapper.deleteFolderItemById(ids);
		customerMapper.deleteCustomerItemByItemIds(ids);
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
							String secretId = "AKIDZ1GH4cw2tXwOgkZ9UaR2OVNzMUN0OYVJ";
							
							COSCredentials cred = new BasicCOSCredentials(secretId, SECRET_KEY);
							// 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
							Region region = new Region("ap-hongkong");
							// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
							
							ClientConfig clientConfig = new ClientConfig(region);
							// 这里建议设置使用 https 协议
							clientConfig.setHttpProtocol(HttpProtocol.https);
							// 3 生成 cos 客户端。
							COSClient cosClient = new COSClient(cred, clientConfig);
							
	//						 getInputStream()返回一个InputStream以从中读取文件的内容。通过此方法就可以获取到流
						    InputStream multipartFileInputStream = null;
							try {
								multipartFileInputStream = file.getInputStream();
							} catch (IOException e) {
								
										
								cosClient.shutdown();
								e.printStackTrace();
								throw new FileUploadException("更新失败，请重试！");
							}
							final String fileName = System.currentTimeMillis() + "" + (new Random().nextInt(90000000) + 10000000) +"_"+file.getOriginalFilename();
							   // 创建上传Object的Metadata
							 ObjectMetadata meta = new ObjectMetadata();
							 // 必须设置ContentLength 
						      meta.setContentLength(file.getSize()); 
								// 指定文件将要存放的存储桶
								
								// 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
								String key = fileName;
								PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, key, multipartFileInputStream,meta);
								
								cosClient.putObject(putObjectRequest);
								
						
						
								item.setPhotoLocation(fileName);
				        
				 
				        	 cosClient.deleteObject(BUCKET_NAME, dbItem.getPhotoLocation());
							
						
							 cosClient.shutdown();
						
				       
				       
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
	public List<Item> findAllItem() {
		List<Item> items = new ArrayList<Item>();
		items=itemMapper.findAllItem();
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


	





	

	
	
	
	
}





