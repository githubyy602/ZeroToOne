package com.yangy.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.exception.CustomException;
import com.yangy.common.wrapper.HttpRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

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
		
		//正则过滤
        String initStr = com.yangy.common.util.StringUtils.saveLetterDigitOrChinese(param);
//		log.info("Regex string is : {}",initStr);

        //base64处理
        String base64Str = Base64Utils.encodeToString(initStr.getBytes(Charset.forName("UTF-8")));
//		log.info("Base64 string is : {}",base64Str);

        //按ASCII升序排序
        base64Str = com.yangy.common.util.StringUtils.sortCharByASCII(base64Str);
		
		return HmacMd5Util.encrypt(base64Str,SALT);
	}
	
	public static boolean checkSign(HttpRequestWrapper request) throws CustomException{
		
		if(Objects.isNull(request)){
			log.warn("Request object is null ......");
			throw CustomException.custom(ResponseCodeEnum.SIGN_ERROR.getCode());
		}

		String method = request.getMethod();
		Map<String,Object> params = null;
		if(StringUtils.equals(method,RequestMethod.POST.name())){
			params = postParam(request);
		}else if(StringUtils.equals(method,RequestMethod.GET.name())){
			params = getParam(request);
		}
		
		if(Objects.isNull(params)){
			throw CustomException.custom(ResponseCodeEnum.PARAM_ERROR.getCode());
		}
		
		//请求的签名
		String reqSign = Objects.nonNull(params.get(SIGN)) ? params.get(SIGN).toString() : "";
		if(StringUtils.isEmpty(reqSign)){
			log.warn("Request sign is null ......");
			throw CustomException.custom(ResponseCodeEnum.SIGN_ERROR.getCode());
		}
		
		params.remove(SIGN);
		String text = JSON.toJSONString(params);
		String createSign = getSign(text);
		
		if(!StringUtils.equals(reqSign,createSign)){
			log.warn("Request sign error ......");
			throw CustomException.custom(ResponseCodeEnum.SIGN_ERROR.getCode());
		}
		
		return true;
	}
	
	private static Map<String,Object> postParam(HttpRequestWrapper request){
		Map<String,Object> params = JSONObject.parseObject(request.getBody(),TreeMap.class);
		
		return params;
	}
	
	private static Map<String,Object> getParam(HttpServletRequest request){
		
		String url = request.getRequestURI();
		
		if (StringUtils.isBlank(url) || !url.contains("?")) {
            return null;
        }
        
        Map<String, Object> paramMap = new TreeMap<>();
        String params = url.split("\\?")[1];
        for (String param : params.split("&")) {
            String[] p = param.split("=");
            paramMap.put(p[0], p[1]);
        }
		
		return paramMap;
	}
	
	/**
     * @return java.lang.String String paramJson
     * @Author Yangy
     * @Description 通过秘钥信息生成的请求签名
     * 生成规则：
     * 1、处理请求参数，将json body或者form表单（若有文件需剔除）转为json字符串。
     * 2、将header中的req-id和timstamp，步骤1中的json字符串（若没有则忽略），客户端凭证中的key，iv，依次拼接组成一个字符串，用正则表达式处理为只包含数字，字母和汉字的字符串。
     * 3、步骤2中处理好的字符串用base64转为UTF-8编码的字符串。
     * 4、将步骤3中的base64字符串按ASCII升序排序。
     * 5、将步骤4中排序好的字符用MD5加密为16进制小写字符，得到的即为最终的签名。
     * <p>
     * example：
     * 前置参数如下：req-id=14254223, timestamp=2023-01-10 11:25:06, key=j5WwPS7Bba9C8nTZ, iv=6W0iJoIZL5BgyF84, jsonBody={"params":{"assetIds":["00700.HK"]}}
     * 生成的签名为：c63c3d776b856aa9f4d36f632c8bb038
     * @Date 11:04 2023/2/3
     * @Param [requestId, time, paramJson, clientId, clientSecret]
     **/
//	public String assembleSign(String requestId, String time, Object param, String key, String iv) {
//
//        Assert.notNull(requestId, () -> "The parameter requestId must not null");
//        Assert.notNull(time, () -> "The parameter time must not null");
//        Assert.notNull(key, () -> "The parameter key must not null");
//        Assert.notNull(iv, () -> "The parameter iv must not null");
//
//        String initStr = String.join("", requestId, time);
//
//        if (Objects.nonNull(param)) {
//            String paramJson = JSONObject.toJSONString(param);
//            initStr = String.join("", initStr, paramJson);
//        }
//
//        initStr = String.join("", initStr, key, iv);
////		log.info("Inited sign text is : {}",initStr);
//
//        //正则过滤
//        initStr = com.yangy.common.util.StringUtils.saveLetterDigitOrChinese(initStr);
////		log.info("Regex string is : {}",initStr);
//
//        //base64处理
//        String base64Str = Base64Utils.encodeToString(initStr.getBytes(Charset.forName("UTF-8")));
////		log.info("Base64 string is : {}",base64Str);
//
//        //按ASCII升序排序
//        base64Str = com.yangy.common.util.StringUtils.sortCharByASCII(base64Str);
////		log.info("Base64 sorted string is : {}",base64Str);
//
//        //用MD5加密为16进制小写字符
//        String md5Str = Md5Util.MD5(base64Str);
////		log.info("MD5 sign is : {}",md5Str);
//
//        return md5Str;
//    }
	
}
