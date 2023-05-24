package com.yangy.common.constant;

/**
 * @Author: Yangy
 * @Date: 2023/3/13 14:50
 * @Description
 */
public class CommonConstant {
	
	public static final String BASE_PACKAGE = "com.yangy";
	
	public static final String FEIGN_PACKAGE = BASE_PACKAGE + ".common.feign";
	
	//常用header
	public static final String HEADER_ACCESS_TOKEN = "accessToken";
	public static final String X_FORAWRDED_FOR = "x-forwarded-for";
	public static final String X_FORAWRDED_HOST = "x-forwarded-host";
	public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
	public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
	public static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
	public static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
	public static final String HTTP_X_REAL_IP = "X-Real-IP";

	public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
	public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	
	
	//方法类型
	public static final String METHOD_TYPE_OPTIONS = "OPTIONS";
	
	
	
	//字符编码
	public static final String CHARSET_UTF8 = "UTF-8";
	
}
