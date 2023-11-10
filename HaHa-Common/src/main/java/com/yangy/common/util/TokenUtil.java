package com.yangy.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yangy.common.bean.Token;
import com.yangy.common.constant.CommonConstant;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.exception.CustomException;
import com.yangy.common.wrapper.HttpRequestWrapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects; 

/**
 * @Author: Yangy
 * @Date: 2023/3/13 12:20
 * @Description 
 * 常见的对称加密算法：DES、3DES、AES、HS256
 * 常见的非对称加密算法：RSA、DSA
 * 散列算法：SHA-1、MD5
 */
@Data
@Slf4j
public class TokenUtil {
	
	/**
	* @Author Yangy
	* @Description toke生成：用户id加时间戳，转json；
	 * 再使用json字符串+盐生成md5串（md5串用来签名校验）；
	 * 最后base64加密
	* @Date 14:15 2023/3/13
	* @Param [userId]
	* @return java.lang.String
	**/
	public static String buildToken(Integer userId) throws Exception {
		
		long  nowTime = LocalDateTime.now(ZoneId.systemDefault()).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();

		//token明文信息
		Token token = Token.builder().userId(userId).time(nowTime).build();
		String paramJson = JSON.toJSONString(token);
		
		return DESUtils.encrypt(paramJson);
	}
	
	public static boolean checkToken(HttpRequestWrapper requestWrapper) throws CustomException {
		String token = requestWrapper.getHeader(CommonConstant.HEADER_ACCESS_TOKEN);
		if(StringUtils.isEmpty(token)){
			throw CustomException.custom(ResponseCodeEnum.TOKEN_ERROR.getCode());
		}

		String deStr;
		try {
			deStr = DESUtils.decrypt(token);
		} catch (Exception e) {
			log.error("Token decrypt error ! {}",e.getMessage(),e);
			throw CustomException.custom(ResponseCodeEnum.TOKEN_ERROR.getCode());
		}

		Token tokenInfo = JSONObject.parseObject(deStr,Token.class);
		if(Objects.isNull(tokenInfo)){
			throw CustomException.custom(ResponseCodeEnum.TOKEN_ERROR.getCode());
		}
		
		//获取userId进行token匹配
		String body = requestWrapper.getBody();
		if(StringUtils.isEmpty(body)){
			throw CustomException.custom(ResponseCodeEnum.TOKEN_ERROR.getCode());
		}
		
		//todo 校验json字符串
		JSONObject jsonObject = JSONObject.parseObject(body);
		Object reqUserId = jsonObject.get("userId");
		if(Objects.isNull(reqUserId)){
			throw CustomException.custom(ResponseCodeEnum.TOKEN_ERROR.getCode());
		}
		
		if(Integer.valueOf(reqUserId.toString()).compareTo(tokenInfo.getUserId()) != 0){
			log.warn("Request userId is not match token : {} ; token :{}",reqUserId.toString(),token);
			throw CustomException.custom(ResponseCodeEnum.TOKEN_ERROR.getCode());
		}
		
		long now = LocalDateTime.now(ZoneId.systemDefault()).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
		if(Objects.isNull(tokenInfo.getTime()) || now - tokenInfo.getTime() > 7200*1000){
			throw CustomException.custom(ResponseCodeEnum.TOKEN_EXPIRE.getCode());
		}
		
		return true;
	}
	
	
	
}
