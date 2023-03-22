package com.yangy.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yangy.common.bean.Token;
import com.yangy.common.constant.CharacterConstant;
import com.yangy.common.constant.CommonConstant;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.exception.CustomException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects; 

/**
 * @Author: Yangy
 * @Date: 2023/3/13 12:20
 * @Description 
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
		//签名
		String sign = SignUtil.getSign(paramJson);
		
		String tokenStr = String.join(CharacterConstant.SEMICOLON,paramJson,sign);
		
		return Base64Utils.encodeToString(tokenStr.getBytes(CommonConstant.CHARSET_UTF8));
	}
	
	public static boolean checkToken(String token) throws CustomException {

		if(StringUtils.isEmpty(token)){
			throw CustomException.custom(ResponseCodeEnum.TOKEN_ERROR.getCode());
		}

		String deStr;
		try {
			deStr = new String(Base64Utils.decodeFromString(token),Charset.forName(CommonConstant.CHARSET_UTF8));
		} catch (Exception e) {
			log.error("Token decrypt error ! {}",e.getMessage(),e);
			return false;
		}

		String param = deStr.split(CharacterConstant.SEMICOLON)[0];
		String sign = deStr.split(CharacterConstant.SEMICOLON).length > 1 ? deStr.split(CharacterConstant.SEMICOLON)[1] : null;
		
		if(Objects.isNull(sign)){
			throw CustomException.custom(ResponseCodeEnum.SIGN_ERROR.getCode());
		}
		
		if(!SignUtil.checkSign(param,sign)){
			throw CustomException.custom(ResponseCodeEnum.SIGN_ERROR.getCode());
		}
		
		Token tokenInfo = JSONObject.parseObject(param,Token.class);
		if(Objects.isNull(tokenInfo)){
			throw CustomException.custom(ResponseCodeEnum.PARAM_ERROR.getCode());
		}
		
		long now = LocalDateTime.now(ZoneId.systemDefault()).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
		if(Objects.isNull(tokenInfo.getTime()) || now - tokenInfo.getTime() > 7200*1000){
			throw CustomException.custom(ResponseCodeEnum.TOKEN_EXPIRE.getCode());
		}
		
		return true;
	}
	
}
