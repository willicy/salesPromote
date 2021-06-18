package net.kingkid.SalesPromote;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * 主程序类 
 */
@SpringBootApplication 
@MapperScan("net.kingkid.SalesPromote.mapper")
public class MainApplication {                    
	
	public static void main(String[] args) {  
		 
		SpringApplication.run(MainApplication.class, args);
	}
}
