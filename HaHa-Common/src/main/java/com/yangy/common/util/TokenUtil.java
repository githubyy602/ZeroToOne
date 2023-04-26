package com.yangy.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yangy.common.bean.Token;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.exception.CustomException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
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
	
	public static boolean checkToken(String token) throws CustomException {

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
			throw CustomException.custom(ResponseCodeEnum.PARAM_ERROR.getCode());
		}
		
		long now = LocalDateTime.now(ZoneId.systemDefault()).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
		if(Objects.isNull(tokenInfo.getTime()) || now - tokenInfo.getTime() > 7200*1000){
			throw CustomException.custom(ResponseCodeEnum.TOKEN_EXPIRE.getCode());
		}
		
		return true;
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
	public String assembleSign(String requestId, String time, Object param, String key, String iv) {

        Assert.notNull(requestId, () -> "The parameter requestId must not null");
        Assert.notNull(time, () -> "The parameter time must not null");
        Assert.notNull(key, () -> "The parameter key must not null");
        Assert.notNull(iv, () -> "The parameter iv must not null");

        String initStr = String.join("", requestId, time);

        if (Objects.nonNull(param)) {
            String paramJson = JSONObject.toJSONString(param);
            initStr = String.join("", initStr, paramJson);
        }

        initStr = String.join("", initStr, key, iv);
//		log.info("Inited sign text is : {}",initStr);

        //正则过滤
        initStr = com.yangy.common.util.StringUtils.saveLetterDigitOrChinese(initStr);
//		log.info("Regex string is : {}",initStr);

        //base64处理
        String base64Str = Base64Utils.encodeToString(initStr.getBytes(Charset.forName("UTF-8")));
//		log.info("Base64 string is : {}",base64Str);

        //按ASCII升序排序
        base64Str = com.yangy.common.util.StringUtils.sortCharByASCII(base64Str);
//		log.info("Base64 sorted string is : {}",base64Str);

        //用MD5加密为16进制小写字符
        String md5Str = Md5Util.MD5(base64Str);
//		log.info("MD5 sign is : {}",md5Str);

        return md5Str;
    }
	
}
