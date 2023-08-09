package com.yangy.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @Author: Yangy
 * @Date: 2023/7/25 14:54
 * @Description
 */
@Slf4j
public class AESUtils {
	
	private static final String KEY_ALGORITHM = "AES";
	//默认的加密算法
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	
	/**根据自定义的key得到256 Bit 的 AES密钥
	 * @param key 自定义的关键字
	 * @return AES密钥（32位的16进制字符串）
	 * @throws Exception
	 */
    public static byte[] initSecretKey(String key) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        //初始化此密钥生成器，使其具有确定的密钥大小
        //AES 要求密钥长度为 256
		SecureRandom secureRandom = new SecureRandom(key.getBytes());
        kg.init(256,secureRandom);
        //生成一个密钥
        SecretKey secretKey = kg.generateKey();
		System.out.println("生成的秘钥为："+showByteArray(secretKey.getEncoded()));
		System.out.println("字符串："+new String(secretKey.getEncoded(),"UTF-8"));
        return secretKey.getEncoded();
    }
    
	/**加密
	 * @param content 需要加密的内容
	 * @param keyStr  AES解密密钥（32位的16进制字符串）
	 * @return 加密后的字节数组
	 */
	public static String encrypt(String content, String keyStr) {
		try {
			byte [] bytes = initSecretKey(keyStr);
			// 根据自定义的密钥构建SecretKeySpec对象
			SecretKeySpec key = new SecretKeySpec(bytes, KEY_ALGORITHM);
			// 创建密码器，声明为AES算法
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			// 初始化
			cipher.init(Cipher.ENCRYPT_MODE, key);
			//一般加密后的结果，这里无法直接通过new String(result)拿到结果，会乱码
			//这里使用的是Apache的commons-codec进行处理为16进制
			// 加密
			byte[] result = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
			return new String(Hex.encodeHex(result));
		} catch (Exception e) {
			log.error("{}",e.getMessage(),e);
		}
		return content;
	}
	
	/*** 解密
	 *  @param content 加密后的密文
	 *  @param keyStr 密钥
	 */
	public static String decrypt(String content, String keyStr) {
		try {
			byte [] bytes = initSecretKey(keyStr);
			SecretKeySpec key = new SecretKeySpec(bytes, KEY_ALGORITHM);
			// 创建密码器
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			// 初始化
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 解密
			byte[] result = cipher.doFinal(Hex.decodeHex(content.toCharArray()));
			return new String(result);
		} catch (Exception e) {
			log.error("{}",e.getMessage(),e);
		}
		return content;
	}
	
	private static String  showByteArray(byte[] data){
        if(null == data){
            return null;
        }
        StringBuilder sb = new StringBuilder("{");
        for(byte b:data){
            sb.append(b).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("}");
        return sb.toString();
    }
    
//	public static void main(String[] args) throws Exception {
//    	String data = "iloveyy";
//    	String orgKey = "213kjs@m1k2j3#wmqke541";
//    	String key = "23lkj987qe2k3m#@1nh1hw";
//
////    	byte [] aesKey = initSecretKey(orgKey);
//		String encrypt = encrypt(data, key);
//		String decrypt = decrypt(encrypt, key);
////		System.out.println("密钥= " + showByteArray(aesKey));
//		System.out.println("加密结果 = " + encrypt);
//		System.out.println("解密 = " + decrypt);
//
//	}
}
