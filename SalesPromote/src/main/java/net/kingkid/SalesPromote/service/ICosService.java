package net.kingkid.SalesPromote.service;
   

import java.util.ArrayList;
import org.springframework.web.multipart.MultipartFile;

import com.qcloud.cos.model.DeleteObjectsRequest;

import net.kingkid.SalesPromote.controller.exception.FileDeleteException;
import net.kingkid.SalesPromote.controller.exception.FileUploadException;
import net.kingkid.SalesPromote.service.exception.QuantityOutOfLimitException;

/**
 * 处理图库数据的业务层接口
 */
public interface ICosService {
	/**
	 * 上传文件
	 * @param file
	 * @param fileName
	 */
	void cosUpload(MultipartFile file,String fileName) throws FileUploadException;
	/**
	 * 删除文件
	 * @param photoLocation
	 */
	public void cosDelete(String photoLocation) throws FileDeleteException;
	/**
	 * 批量删除文件
	 * 最多一次删除1000个
	 * @param photoLocations
	 */
	public void cosBatchDelete(ArrayList<DeleteObjectsRequest.KeyVersion> photoLocations) throws QuantityOutOfLimitException;
	
	 
	
	
	
}
