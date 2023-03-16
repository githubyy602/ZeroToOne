package util;

import bean.Token;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import constant.CharacterConstant;
import constant.CommonConstant;
import enums.ResponseCodeEnum;
import exception.CustomException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
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
	
	public static final String SALT = "84B2J2hvk2jdnvk4#@#";
	
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
		String sign = HmacMd5Util.encrypt(paramJson,SALT);
		
		String tokenStr = String.join(CharacterConstant.SEMICOLON,paramJson,sign);
		
		return Base64Utils.encodeToString(tokenStr.getBytes(CommonConstant.CHARSET_UTF8));
	}
	
	public static boolean checkToken(String token) throws CustomException {

		Assert.isNull(token, "Token is empty!!!");

		String deStr;
		try {
			deStr = Base64Utils.encodeToString(token.getBytes(CommonConstant.CHARSET_UTF8));
		} catch (UnsupportedEncodingException e) {
			log.error("Token decrypt error ! {}",e.getMessage(),e);
			return false;
		}

		String param = deStr.split(CharacterConstant.SEMICOLON)[0];
		String sign = deStr.split(CharacterConstant.SEMICOLON).length > 1 ? deStr.split(CharacterConstant.SEMICOLON)[1] : null;
		
		if(Objects.isNull(sign)){
			throw CustomException.custom(ResponseCodeEnum.SIGN_ERROR.getCode());
		}
		
		if(!sign.equals(HmacMd5Util.encrypt(param,SALT))){
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
