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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.kingkid.SalesPromote.entity.Folder;
import net.kingkid.SalesPromote.entity.Item;
import net.kingkid.SalesPromote.entity.ResponseResult;
import net.kingkid.SalesPromote.service.IFolderService;

@Controller
@RequestMapping("/user/folder")
public class FolderController extends BaseController{

	@Autowired
	private IFolderService folderService;  
	 
		/**   
		 * folder page请求 
		 */   
		@GetMapping("/page")   
		public String toFolder() {        
			
			return "user/folder"; 
			  
		}    
		@GetMapping("/folder") 
		@ResponseBody      
		public ResponseResult<List<Folder>> getAllFolderExceptSelf(Integer id) {
			
			List<Folder> folders = folderService.findAllFolder(null); 
			int index=-1; 
			for (Folder folder : folders) {
				if(folder.getId()==id) {
					index=folders.indexOf(folder);   
				}
			}
			if(index!=-1) { 
				folders.remove(index);
			}
			
			return new ResponseResult<List<Folder>>(SUCCESS,folders);
		 
		}  
		@GetMapping("/item")               
		@ResponseBody   
		public ResponseResult<List<Item>> getAllItem(Integer id,String itemName) {
			List<Item> items = folderService.findFolderItemById(id,itemName );
			
			return new ResponseResult<List<Item>>(SUCCESS,items); 
		
		}
		/**     
		 * 删除款       
		 *     
		 *   
		 */ 
		@RequestMapping(value = "/item",method = RequestMethod.DELETE)	
		@ResponseBody  
	    public ResponseResult<String> deleteItem(
	    		@RequestParam("itemIds[]")String[] paramItemIds,@RequestParam("folderId")Integer folderId,
	    		HttpSession session)  { 
			   
			List<Integer> itemIds = new ArrayList<Integer>();
			for (String id : paramItemIds) {
				itemIds.add(Integer.valueOf(id));
			} 
              
            folderService.deleteItemById(itemIds,folderId);
             
	        return new ResponseResult<>(SUCCESS);
	    }       
		/**        
		 * 新增款    
		 *    
		 *  
		 */ 
		@RequestMapping(value = "/item",method = RequestMethod.POST)	
		@ResponseBody 
	    public ResponseResult<String> addItem( 
	    		Item item,@RequestPart("file") MultipartFile file,@RequestParam("folderId")Integer folderId,
	    		HttpSession session)  { 
			 
	       
            
            folderService.addItem(item, file,folderId,(String)session.getAttribute("username"));
            
	        return new ResponseResult<>(SUCCESS);  
	    } 
		/**        
		 * 复制款到其他文件夹    
		 *    
		 *  
		 */ 
		@RequestMapping(value = "/copyItemToFolders",method = RequestMethod.POST)	
		@ResponseBody 
	    public ResponseResult<String> copyItemToFolders( 
	    		@RequestParam("folderIds")List<String> folderIds,@RequestParam("itemIds")List<String> itemIds,
	    		HttpSession session)  {  
			 
		
			List<Integer> intFolderIds=new ArrayList<Integer>(); 
			List<Integer> intItemIds=new ArrayList<Integer>();   
			    
			for (String string : folderIds) {
				intFolderIds.add(Integer.valueOf(string.substring(string.indexOf('"')+1, string.lastIndexOf('"'))));   
				   
			} 
			for (String string : itemIds) { 
				intItemIds.add(Integer.valueOf(string.substring(string.indexOf('"')+1, string.lastIndexOf('"'))));
				
			}
			 
			
			folderService.copyItemToFolders(intFolderIds,intItemIds, (String)session.getAttribute("username"));
            
	        return new ResponseResult<>(SUCCESS);  
	    } 
		/**        
		 * 复制款到其他文件夹    
		 *       
		 *  
		 */ 
		@RequestMapping(value = "/moveItemToFolders",method = RequestMethod.POST)	
		@ResponseBody 
	    public ResponseResult<String> moveItemToFolders( @RequestParam("currentFolderId")Integer currentFolderId,
	    		@RequestParam("folderIds")List<String> folderIds,@RequestParam("itemIds")List<String> itemIds,
	    		HttpSession session)  { 
		
			List<Integer> intFolderIds=new ArrayList<Integer>(); 
			List<Integer> intItemIds=new ArrayList<Integer>();   
			    
			for (String string : folderIds) {
				intFolderIds.add(Integer.valueOf(string.substring(string.indexOf('"')+1, string.lastIndexOf('"'))));
				 
			}                   
			for (String string : itemIds) { 
				intItemIds.add(Integer.valueOf(string.substring(string.indexOf('"')+1, string.lastIndexOf('"'))));
				   
			}
			 
			
			folderService.copyItemToFolders(intFolderIds,intItemIds, (String)session.getAttribute("username"));
			folderService.deleteItemById(intItemIds, currentFolderId);
	        return new ResponseResult<>(SUCCESS);  
	    } 
}
