package net.kingkid.SalesPromote.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import net.kingkid.SalesPromote.entity.Item;
import net.kingkid.SalesPromote.entity.ItemPhoto;
import net.kingkid.SalesPromote.entity.ResponseResult;
import net.kingkid.SalesPromote.service.IAlbumService;

@Controller
@RequestMapping("/user/album")
public class AlbumController extends BaseController{
	  
	
	@Autowired
	private IAlbumService albumService;  
	 
		
		/**   
		 * album page请求 
		 */
		@GetMapping("")
		public String toAlbum() {  
			return "user/album";      
			 
		}
		
		/**   
		 * 取得所有款    
		 */
		@GetMapping("/allitem")
		@ResponseBody   
		public ResponseResult<List<Item>> getAllItem(@RequestParam(value="itemName",required=false)String itemName) { 
		
			List<Item> items = albumService.findAllItem(itemName);
			 
			return new ResponseResult<List<Item>>(SUCCESS,items);     
			 
		}
		/**       
		 * 取得款   
		 */
		@GetMapping("/item")
		@ResponseBody   
		public ResponseResult<Item> getItem(@RequestParam("itemId")Integer itemId) {  
			Item item = albumService.findItem(itemId);
			return new ResponseResult<Item>(SUCCESS,item);     
			     
		} 
		/**        
		 * 新增款    
		 *    
		 *  
		 */ 
		@RequestMapping(value = "/item",method = RequestMethod.POST)	
		@ResponseBody 
	    public ResponseResult<String> addItem( 
	    		Item item,@RequestPart("file") MultipartFile file, 
	    		HttpSession session)  { 
			 
	        
            
            albumService.addItem(item, file,(String)session.getAttribute("username"));
            
	        return new ResponseResult<>(SUCCESS); 
	    } 
		 
		/**    
		 * 删除款  
		 * 
		 * 
		 */
		@RequestMapping(value = "/item",method = RequestMethod.DELETE)	
		@ResponseBody 
	    public ResponseResult<String> deleteItem(
	    		@RequestParam("itemIds[]")String[] paramItemIds,
	    		HttpSession session)  { 
			  
			List<Integer> itemIds = new ArrayList<Integer>();
			for (String id : paramItemIds) {
				itemIds.add(Integer.valueOf(id));
			}
              
            albumService.deleteItemById(itemIds);
             
	        return new ResponseResult<>(SUCCESS);
	    }       
		/**       
		 * 修改款            
		 */  
		@RequestMapping(value = "/modifyItem",method = RequestMethod.POST)
		@ResponseBody   
		public ResponseResult<Item> modifyItem(Item item,@RequestPart("file") MultipartFile file,HttpSession session) {  
 
			albumService.updateItem(item,file, (String)session.getAttribute("username"));
			return new ResponseResult<Item>(SUCCESS);     
			        
		} 
		
		
		
		
		/**   
		 * 取得所有款细节图片    
		 */                  
		@GetMapping("/allitemphoto")    
		@ResponseBody       
		public ResponseResult<List<ItemPhoto>> getAllItemPhoto(Integer id) { 
		
			List<ItemPhoto> itemPhoto = albumService.getAllItemPhoto(id);
		
			return new ResponseResult<List<ItemPhoto>>(SUCCESS,itemPhoto);     
			 
		}
		/**   
		 * 款细节图片做处理    
		 */
		@RequestMapping(value = "/itemphoto",method = RequestMethod.POST)    
		@ResponseBody   
		public ResponseResult<String> actionItemPhoto(@RequestPart("file1") MultipartFile file1,@RequestPart("file2") MultipartFile file2,@RequestPart("file3") MultipartFile file3,@RequestPart("file4") MultipartFile file4,@RequestPart("file5") MultipartFile file5,String[] removePhotos,Integer id) { 
			Map<Integer,MultipartFile> files=new HashMap<Integer,MultipartFile>();
			if(!file1.isEmpty()) {
				files.put(1, file1);
			}
			if(!file2.isEmpty()) {
				files.put(2, file2);
			}
			if(!file3.isEmpty()) {
				files.put(3, file3);
			}
			if(!file4.isEmpty()) {
				files.put(4, file4);
			}
			if(!file5.isEmpty()) {
				files.put(5, file5);
			}
		
			 albumService.actionItemPhoto(id,files,removePhotos); 
		 
			return new ResponseResult<String>(SUCCESS);     
			 
		}           
		
		 
		  
	   
} 
