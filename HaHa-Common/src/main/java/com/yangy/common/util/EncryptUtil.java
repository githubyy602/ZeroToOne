package com.yangy.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * @Author: Yangy
 * @Date: 2023/12/5 18:20
 * @Description
 */
public class EncryptUtil {
	
	private  static final String key = "23lkj987qe2k3m#@1nh1hw"; 
	
	public static String encrypt(String plaintext){
		if(org.apache.commons.lang.StringUtils.isEmpty(plaintext)){
			return plaintext;
		}
		return AESUtils.encrypt(plaintext,key);
	}

	public static String decrypt(String ciphertext){
		if(StringUtils.isEmpty(ciphertext)){
			return ciphertext;
		}
		return AESUtils.decrypt(ciphertext,key);
	}
}
