package net.kingkid.SalesPromote.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.multipart.MultipartFile;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;

import net.kingkid.SalesPromote.controller.exception.FileUploadException;

public class BaseService {
	static final String BUCKET_NAME = "kingkidbucket-1305101123";
	static final String SECRET_KEY = "f6IFyTjkX5QZT8aooa7RXP3TedOuBD2d";
	static final String SECRET_ID = "AKIDZ1GH4cw2tXwOgkZ9UaR2OVNzMUN0OYVJ";
	static String photoPrefix="";
	static {

		// 根据关键字查询相应的值
		try {
			photoPrefix = PropertiesLoaderUtils.loadAllProperties("value.properties").getProperty("photoPrefix");
			   
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
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
	public void cosUpload(MultipartFile file,String fileName) {
		COSClient cosClient= cosSetUp();
		try {
			InputStream multipartFileInputStream = file.getInputStream();
			ObjectMetadata meta = new ObjectMetadata();
			 // 必须设置ContentLength 
		      meta.setContentLength(file.getSize()); 
				// 指定文件将要存放的存储桶
				
				// 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
				String key = fileName;
				PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, key, multipartFileInputStream,meta);
				
				cosClient.putObject(putObjectRequest);
				
		
			
		} catch (IOException e) {
			
					
			
			e.printStackTrace();
			throw new FileUploadException("上传失败，请重试！");
		}finally {
			cosClient.shutdown();
		}
		
		
	
		 
	}
	
	
	
	public void cosDelete(String photoLocation) {
		COSClient cosClient= cosSetUp();
		try {
		
			cosClient.deleteObject(BUCKET_NAME, photoLocation);
			
		
			
		} catch (Exception e) {
			
					
			
			e.printStackTrace();
			throw new FileUploadException("上传失败，请重试！");
		}finally {
			cosClient.shutdown();
		}
		
		 
	}
}
