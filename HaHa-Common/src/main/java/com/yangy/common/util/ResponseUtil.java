package com.yangy.common.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: Yangy
 * @Date: 2023/3/22 17:03
 * @Description
 */
public class ResponseUtil {
	
	public static void responseOutWithJson(HttpServletResponse response,Object content){
		
		//将实体对象转换为JSON Object转换  
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("application/json; charset=utf-8");  
		PrintWriter out = null;  
		try {  
			out = response.getWriter();  
			out.append(JSON.toJSONString(content));  
		} catch (IOException e) {  
			e.printStackTrace();  
		} finally {  
			if (out != null) {  
				out.close();  
			}  
		}  
		
	}
	
}
