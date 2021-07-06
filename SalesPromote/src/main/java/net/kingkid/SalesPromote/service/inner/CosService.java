package net.kingkid.SalesPromote.service.inner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

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
import com.qcloud.cos.transfer.MultipleFileUpload;
import com.qcloud.cos.transfer.TransferManager;

import net.kingkid.SalesPromote.controller.exception.FileDeleteException;
import net.kingkid.SalesPromote.controller.exception.FileUploadException;
import net.kingkid.SalesPromote.service.ICosService;
import net.kingkid.SalesPromote.service.exception.QuantityOutOfLimitException;
/**
 * 处理COS
 */
@Service
public class CosService implements ICosService{
	static final String BUCKET_NAME = "kingkidbucket-1305101123";
	static final String SECRET_KEY = "f6IFyTjkX5QZT8aooa7RXP3TedOuBD2d";
	static final String SECRET_ID = "AKIDZ1GH4cw2tXwOgkZ9UaR2OVNzMUN0OYVJ";
	
	public COSClient cosSetUp() {  
		
		
		COSCredentials cred = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
		// 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
		Region region = new Region("ap-hongkong");
		// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
		
		ClientConfig clientConfig = new ClientConfig(region);
		// 这里建议设置使用 https 协议
		clientConfig.setHttpProtocol(HttpProtocol.https);
		// 3 生成 cos 客户端。
		COSClient cosClient = new COSClient(cred, clientConfig);
		return cosClient;
		
	 
	}
	public void cosUpload(MultipartFile file,String fileName) throws FileUploadException{
		COSClient cosClient= cosSetUp();
		try {
			InputStream multipartFileInputStream = file.getInputStream();
			ObjectMetadata meta = new ObjectMetadata();
			 // 必须设置ContentLength 
		      meta.setContentLength(file.getSize()); 
			// 指定文件将要存放的存储桶
			
			// 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
		    if(meta!=null&&file!=null&&multipartFileInputStream!=null) {
				PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, fileName, multipartFileInputStream,meta);
		
				cosClient.putObject(putObjectRequest);
			
		    }else {
		    	throw new FileUploadException("上传失败，请重试！");
			}
			
		} catch (IOException e) {
			
					
			
			e.printStackTrace();
			throw new FileUploadException("上传失败，请重试！");
		}finally {
			cosClient.shutdown();
		}
		
		
	
		 
	}
	public void cosBatchUpload(Map<String,MultipartFile> files) throws FileUploadException{
		COSClient cosClient= cosSetUp();
		
		ObjectMetadata meta ;
		MultipartFile file;
		PutObjectRequest putObjectRequest;
		for(String fileName:files.keySet()) {
			
			meta=null;
			file=null;
			putObjectRequest=null;
			file=files.get(fileName);
			meta = new ObjectMetadata();
			try (InputStream multipartFileInputStream=file.getInputStream();){
				
				
				// 必须设置ContentLength 
			    meta.setContentLength(file.getSize()); 
			    // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
				if(meta!=null&&file!=null&&multipartFileInputStream!=null) {
					putObjectRequest = new PutObjectRequest(BUCKET_NAME, fileName, multipartFileInputStream,meta);
					
					cosClient.putObject(putObjectRequest);
				}else {
					throw new FileUploadException("上传失败，请重试！");
				}
				
			} catch (IOException e) {
				
						
				
				e.printStackTrace();
				cosClient.shutdown();
				
				throw new FileUploadException("上传失败，请重试！");
				
			}
			
			
			
		}
		cosClient.shutdown();
		
			
	}
	
	
	
	public void cosDelete(String photoLocation) throws FileDeleteException{
		COSClient cosClient= cosSetUp();
		try {
		
			cosClient.deleteObject(BUCKET_NAME, photoLocation);
			
		
			
		} catch (Exception e) {
			
					
			
			e.printStackTrace();
			throw new FileDeleteException("删除失败，请重试！");
		}finally {
			cosClient.shutdown();
		}
		
		 
	}
	
	
	public void cosBatchDelete(List<DeleteObjectsRequest.KeyVersion> photoLocations) throws QuantityOutOfLimitException{
		if(photoLocations.size()>999) {
			throw new QuantityOutOfLimitException("删除数量超出限制(1000)!");
		}
		COSClient cosClient= cosSetUp();
		
		
		DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(BUCKET_NAME);
		// 设置要删除的key列表, 最多一次删除1000个

		deleteObjectsRequest.setKeys(photoLocations);
		
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
	 
	}
	
}
