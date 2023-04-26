package com.yangy.common.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Yangy
 * @Date: 2023/4/26 16:00
 * @Description
 */
public class StringUtils {
	/**
	* @Author Yangy
	* @Description 只保留数字、字母和汉字
	* @Date 12:19 2023/2/3
	* @Param [source]
	* @return java.lang.String
	**/
	public static String saveLetterDigitOrChinese(String source){
		String format = "[a-zA-Z0-9\\u4E00-\\u9FA5]*";
		Matcher matcher = Pattern.compile(format).matcher(source);
		String str = "";
		while (matcher.find()){
			str += matcher.group(0);
		}
		return str;
	}
	
	public static String sortCharByASCII(String source){
		if(org.apache.commons.lang.StringUtils.isEmpty(source)) return source;
		char [] arys = source.toCharArray();
		Arrays.sort(arys);
		return new String(arys);
	}
}
