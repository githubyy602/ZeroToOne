package com.yangy.common.util;

/**
 * @Author: Yangy
 * @Date: 2023/3/16 18:19
 * @Description
 */
public class SignUtil {
	
	public static final String SALT = "84B2J2hvk2jdnvk4#@#";
	
	public static String getSign(String param){
		//todo 此处使用的盐可以弄成动态的，比如弄一个秘钥接口，让前端每次使用2小时内有效的秘钥来生成sign并校验
		return HmacMd5Util.encrypt(param,SALT);
	}
	
	public static boolean checkSign(String param,String reqSign){
		if(reqSign.equals(getSign(param))){
			return true;
		}
		
		return false;
	} 
}
