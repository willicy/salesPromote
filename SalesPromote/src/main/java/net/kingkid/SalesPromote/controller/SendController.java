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

import net.kingkid.SalesPromote.entity.Item;
import net.kingkid.SalesPromote.entity.ResponseResult;
import net.kingkid.SalesPromote.service.ICustomerService;
import net.kingkid.SalesPromote.service.exception.DeleteException;

@Controller
@RequestMapping("/user/send")  
public class SendController extends BaseController{
	   
	@Autowired
	private ICustomerService customerService;  
	 
		/**   
		 * send page请求 
		 */
		@GetMapping("")
		public String toSend() {   
			return "user/send";
			 
		} 
		
		/**   
		 * chat page请求   
		 */
		@GetMapping("page")   
		public String toChat() {   
			return "user/chat";
			     
		}  
		@GetMapping("/item")
		@ResponseBody                     
		public ResponseResult<List<Item>> getAllItem(Integer id) {  
			List<Item> items = customerService.findCustomerItemById(id);
			
			return new ResponseResult<List<Item>>(SUCCESS,items); 
		   
		} 
		@RequestMapping(value = "/send",method = RequestMethod.PUT) 
		@ResponseBody                     
		public ResponseResult<String> send(@RequestParam(value="groupIds",required=false)List<String> paramGroupIds,
				@RequestParam(value="customerIds",required=false)List<String> paramCustomerIds,
				@RequestParam("itemIds[]")String[] paramItemIds
				,HttpSession session) {
	
			if((paramGroupIds.get(0).equals("[]")&&paramCustomerIds.get(0).equals("[]"))||paramItemIds.length==0) {
				throw new DeleteException("删除错误，请重试！");
			} 
			  
			List<Integer> groupIds = new ArrayList<Integer>();
			
			if(!paramGroupIds.get(0).equals("[]")) {
				for (String string : paramGroupIds) {
					groupIds.add(Integer.valueOf(string.substring(string.indexOf('"')+1, string.lastIndexOf('"'))));
					  
				}  
			}
			
			List<Integer> customerIds = new ArrayList<Integer>();
			if(!paramCustomerIds.get(0).equals("[]")) {
				for (String string : paramCustomerIds) {
					customerIds.add(Integer.valueOf(string.substring(string.indexOf('"')+1, string.lastIndexOf('"'))));
					 
				} 
			}
			List<Integer> itemIds = new ArrayList<Integer>();
			for (String id : paramItemIds) {
				itemIds.add(Integer.valueOf(id));
			}
			
			customerService.send(groupIds,customerIds,itemIds,(String)session.getAttribute("username"));
			
			return new ResponseResult<String>(SUCCESS); 
		 
		}
		@RequestMapping(value = "/sendFolder",method = RequestMethod.PUT) 
		@ResponseBody  
		public ResponseResult<String> sendFolder(@RequestParam(value="groupIds",required=false)List<String> paramGroupIds,
				@RequestParam(value="customerIds",required=false)List<String> paramCustomerIds,
				@RequestParam("folderIds[]")String[] paramFolderIds
				,HttpSession session) {
	
			if((paramGroupIds.get(0).equals("[]")&&paramCustomerIds.get(0).equals("[]"))||paramFolderIds.length==0) {
				throw new DeleteException("删除错误，请重试！");
			} 
			 
			List<Integer> groupIds = new ArrayList<Integer>();
			
			if(!paramGroupIds.get(0).equals("[]")) {
				for (String string : paramGroupIds) {
					groupIds.add(Integer.valueOf(string.substring(string.indexOf('"')+1, string.lastIndexOf('"'))));
					  
				} 
			}
			
			List<Integer> customerIds = new ArrayList<Integer>();
			if(!paramCustomerIds.get(0).equals("[]")) {
				for (String string : paramCustomerIds) {
					customerIds.add(Integer.valueOf(string.substring(string.indexOf('"')+1, string.lastIndexOf('"'))));
					 
				} 
			}
			List<Integer> folderIds = new ArrayList<Integer>();
			for (String id : paramFolderIds) {
				folderIds.add(Integer.valueOf(id));
			}
			
			customerService.sendFolder(groupIds,customerIds,folderIds,(String)session.getAttribute("username"));
			
			return new ResponseResult<String>(SUCCESS); 
		 
		}

}  
