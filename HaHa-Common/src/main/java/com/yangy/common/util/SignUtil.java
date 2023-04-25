package com.yangy.common.util;

import com.yangy.common.constant.CharacterConstant;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: Yangy
 * @Date: 2023/3/16 18:19
 * @Description
 */
@Slf4j
public class SignUtil {
	
	public static final String SALT = "84B2J2hvk2jdnvk4#@#";
	public static final String SIGN = "sign";
	
	public static String getSign(String param){
		return HmacMd5Util.encrypt(param,SALT);
	}
	
	public static boolean checkSign(HttpServletRequest request) throws CustomException{
		
		if(Objects.isNull(request)){
			log.warn("Request object is null ......");
			throw CustomException.custom(ResponseCodeEnum.SIGN_ERROR.getCode());
		}

		String method = request.getMethod();
		Map<String,String> params = null;
		if(StringUtils.equals(method,RequestMethod.POST.name())){
			params = postParam(request);
		}else if(StringUtils.equals(method,RequestMethod.GET.name())){
			params = getParam(request);
		}
		
		//请求的签名
		String reqSign = params.get(SIGN);
		if(StringUtils.isEmpty(reqSign)){
			log.warn("Request sign is null ......");
			throw CustomException.custom(ResponseCodeEnum.SIGN_ERROR.getCode());
		}
		
		StringBuffer sf = new StringBuffer();
		params.forEach((k,v)->{
					sf.append(k).append(CharacterConstant.EQUAL).append(v).append(CharacterConstant.JUST);
				});
		
		String text = sf.substring(0,sf.lastIndexOf(CharacterConstant.JUST));
		String createSign = getSign(text);
		
		if(StringUtils.equals(reqSign,createSign)){
			log.warn("Request sign error ......");
			throw CustomException.custom(ResponseCodeEnum.SIGN_ERROR.getCode());
		}
		
		return false;
	}
	
	private static Map<String,String> postParam(HttpServletRequest request){
		Map<String,String> params = new HashMap<>();
		Enumeration<String> keys = request.getParameterNames();
		while (keys.hasMoreElements()){
			String key = keys.nextElement();
			String val = request.getParameter(key);
			
			if(StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(val)){
				params.put(key,val);
			}
		}
		
		return params;
	}
	
	private static Map<String,String> getParam(HttpServletRequest request){
		
		String url = request.getRequestURI();
		
		if (StringUtils.isBlank(url) || !url.contains("?")) {
            return null;
        }
        
        Map<String, String> paramMap = new HashMap<>();
        String params = url.split("\\?")[1];
        for (String param : params.split("&")) {
            String[] p = param.split("=");
            paramMap.put(p[0], p[1]);
        }
		
		return paramMap;
	}

}
