package net.kingkid.SalesPromote.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.kingkid.SalesPromote.entity.Folder;
import net.kingkid.SalesPromote.entity.ResponseResult;
import net.kingkid.SalesPromote.service.IFolderService;
import net.kingkid.SalesPromote.service.exception.DeleteException;

@Controller
@RequestMapping("/user/libary")
public class LibaryController extends BaseController{
	  
	@Autowired
	private IFolderService folderService;  
	 
		/**   
		 * libary page请求 
		 */
		@GetMapping("")
		public String toLibary() {
			return "user/libary";
			 
		} 
		
		/**
		 * 取所有Folder 
		 */  
		 
		@GetMapping("/folder") 
		@ResponseBody 
		public ResponseResult<List<Folder>> getAllFolder(@RequestParam(value="forderName",required=false)String forderName) {
			
			List<Folder> folders = folderService.findAllFolder(forderName);
			
			return new ResponseResult<List<Folder>>(SUCCESS,folders);
		
		}
		/** 
		 * 新增Folder 
		 */
		@RequestMapping(value = "/folder",method = RequestMethod.PUT)
		@ResponseBody 
	    public ResponseResult<String> addFolder(
	    		@RequestParam("foldername")String foldername,
	    		HttpSession session){
			  
	        folderService.addFolder(foldername, (String)session.getAttribute("username"));
	        return new ResponseResult<>(SUCCESS);
	    }
		/**
		 * 更新Folder Name 
		 */ 
		@RequestMapping(value = "/folder",method = RequestMethod.PATCH)
		@ResponseBody
	    public ResponseResult<String> updateFolderName(
	    		@RequestParam("folderId")Integer folderId,
	    		@RequestParam("foldername")String foldername,
	    		HttpSession session){
			
	        folderService.updateFolderName(folderId, foldername,(String)session.getAttribute("username"));
	        return new ResponseResult<>(SUCCESS);
	    } 
		/**
		 * 删除Folder 
		 */ 
		@RequestMapping(value = "/folder",method = RequestMethod.DELETE)
		@ResponseBody
	    public ResponseResult<String> deleteFolder(
	    		@RequestParam("folderIds[]")String[] paramFolderIds, 
	    		HttpSession session){
			if(paramFolderIds.length==0) {
				throw new DeleteException("删除错误，请重试！");
			}
			
			List<Integer> folderIds = new ArrayList<Integer>();
			for (String id : paramFolderIds) {
				folderIds.add(Integer.valueOf(id));
			}
	        folderService.deleteFolderById(folderIds);        
	        return new ResponseResult<>(SUCCESS);
	    }
	
}
