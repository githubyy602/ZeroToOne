package com.yangy.hahauser.service.impl;

import com.yangy.common.util.AESUtils;
import com.yangy.hahauser.service.EncryptService;
import org.springframework.stereotype.Service;

/**
 * @Author: Yangy
 * @Date: 2023/7/25 14:37
 * @Description 加解密存储方案一：结合mybatis-plus的来实现，继承BaseTypeHandler实现加解密
 */
@Service
public class EncryptServiceImpl implements EncryptService {
	
	private final String key = "23lkj987qe2k3m#@1nh1hw"; 
	
	@Override
	public String encrypt(String plaintext){
		return AESUtils.encrypt(plaintext,key);
	}

	@Override
	public String decrypt(String ciphertext){
		return AESUtils.decrypt(ciphertext,key);
	}
}
