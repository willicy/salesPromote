package net.kingkid.SalesPromote.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.kingkid.SalesPromote.interceptor.LoginInterceptor;

/*
 * 配置类
 * ·拦截所有请求(除白名单以外)
 *
 */
@Configuration
public class MyConfig implements WebMvcConfigurer{

	@Autowired 
	LoginInterceptor loginInterceptor;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	//映射图片保存地址
		registry.addResourceHandler("/images/products/**").addResourceLocations("file:D:/Work/TerryH/eclipse-workspace/SalesPromote/src/main/resources/static/images/products/");
	}  
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	
		List<String> excludePaths = new ArrayList<String>();
		excludePaths.add("/user/login");
		excludePaths.add("/user/register");
		excludePaths.add("/login");
		excludePaths.add("/enlogin");
		excludePaths.add("/register");
		excludePaths.add("/css/**");
		excludePaths.add("/js/**");
		excludePaths.add("/images/**");
		excludePaths.add("/fonts/**");
		excludePaths.add("/admin/**");
		excludePaths.add("/sweetalert/**");
		
		// 注册
		registry
			.addInterceptor(loginInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns(excludePaths);
		
		
		WebMvcConfigurer.super.addInterceptors(registry);
	}
	
	
	
}
