package com.yangy.hahauser.service;

/**
 * @Author: Yangy
 * @Date: 2023/7/25 14:35
 * @Description 加解密处理类
 */
public interface EncryptService {
	
	public String encrypt(String plaintext) throws Exception;
	
	public String decrypt(String ciphertext) throws Exception;
}
