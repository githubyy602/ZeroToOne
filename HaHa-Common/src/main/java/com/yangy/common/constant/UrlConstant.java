package com.yangy.common.constant;

/**
 * @Author: Yangy
 * @Date: 2023/12/5 16:32
 * @Description
 */
public class UrlConstant {
	
	public static final String DOMAIN = "http://localhost:";
	
	public static String ASSEMBLE_URL(int port,String context){
		return String.format("%s%s%s",DOMAIN,port,context);
	}
	
	public static String FILE_SERVICE_URL = ASSEMBLE_URL(20012,"/file");
}
