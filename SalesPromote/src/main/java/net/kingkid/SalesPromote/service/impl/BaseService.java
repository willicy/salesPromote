package net.kingkid.SalesPromote.service.impl;

import java.io.IOException;

import org.springframework.core.io.support.PropertiesLoaderUtils;

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
