package com.yangy.user.service.impl;

import com.yangy.common.util.AESUtils;
import com.yangy.user.service.EncryptService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @Author: Yangy
 * @Date: 2023/7/25 14:37
 * @Description 
 */
@Service
public class EncryptServiceImpl implements EncryptService {
	
	private final String key = "23lkj987qe2k3m#@1nh1hw"; 
	
	@Override
	public String encrypt(String plaintext){
		if(StringUtils.isEmpty(plaintext)){
			return plaintext;
		}
		return AESUtils.encrypt(plaintext,key);
	}

	@Override
	public String decrypt(String ciphertext){
		if(StringUtils.isEmpty(ciphertext)){
			return ciphertext;
		}
		return AESUtils.decrypt(ciphertext,key);
	}
}
