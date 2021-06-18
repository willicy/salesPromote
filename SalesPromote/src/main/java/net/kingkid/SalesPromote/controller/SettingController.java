package net.kingkid.SalesPromote.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.kingkid.SalesPromote.entity.ItemDropdown;
import net.kingkid.SalesPromote.entity.ResponseResult;
import net.kingkid.SalesPromote.service.ISettingService;

@Controller
@RequestMapping("/user/setting")  
public class SettingController extends BaseController{
	   
	@Autowired
	private ISettingService settingService;  
	 
		/**   
		 * send page请求 
		 */
		@GetMapping("")
		public String toSetting() {               
			return "user/setting";    
			 
		}  
		 
		@GetMapping("/size")
		@ResponseBody  
		public ResponseResult <List<ItemDropdown>> getSize() {
			return new ResponseResult <List<ItemDropdown>>(SUCCESS,settingService.findItemSize());   
			 
		} 
		@RequestMapping(value = "/size",method = RequestMethod.PUT) 
		@ResponseBody                     
		public ResponseResult<String> addSize(@RequestParam("value")String size,@RequestParam("num")Integer num) {
	  
		 
			 
			settingService.addSize(size,num);
			
			return new ResponseResult<String>(SUCCESS);  
		 
		}
		@RequestMapping(value = "/size",method = RequestMethod.DELETE) 
		@ResponseBody                     
		public ResponseResult<String> deleteSize(@RequestParam("id")String id) {
	
		
			
			settingService.deleteItemSizeById(Integer.parseInt(id));   
			
			return new ResponseResult<String>(SUCCESS); 
		 
		}

		 
		@GetMapping("/type")
		@ResponseBody  
		public ResponseResult <List<ItemDropdown>> getType() {          
			
			return new ResponseResult <List<ItemDropdown>>(SUCCESS,settingService.findItemType());   
			 
		} 
		@RequestMapping(value = "/type",method = RequestMethod.PUT) 
		@ResponseBody                     
		public ResponseResult<String> addType(@RequestParam("value")String type) {
	
		
			
			settingService.addType(type);
			
			return new ResponseResult<String>(SUCCESS); 
		 
		}
		@RequestMapping(value = "/type",method = RequestMethod.DELETE) 
		@ResponseBody                     
		public ResponseResult<String> deleteType(@RequestParam("id")String id) {
	
		
			
			settingService.deleteItemTypeById(Integer.parseInt(id));  
			
			return new ResponseResult<String>(SUCCESS); 
		 
		}
}  
