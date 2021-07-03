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

	static String photoPrefix="";
	static {

		// 根据关键字查询相应的值
		try {
			photoPrefix = PropertiesLoaderUtils.loadAllProperties("value.properties").getProperty("photoPrefix");
			   
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
