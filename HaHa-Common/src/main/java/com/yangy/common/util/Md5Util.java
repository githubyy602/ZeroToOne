package com.yangy.common.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.util.Random;

public class Md5Util {
	
	public static final String PUBLICKEY="zxcvbnmasdfghjklqwertyuiop";

	 /**
     * MD5方法
     * 
     * @param text 明文
     * @param key 密钥
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text, String key) throws Exception {
        //加密后的字符串
        String encodeStr=DigestUtils.md5Hex(text + key);
//        System.out.println("MD5加密后的字符串为:encodeStr="+encodeStr);
        return encodeStr;
        }

    /**
     * MD5验证方法
     * 
     * @param text 明文
     * @param key 密钥
     * @param md5 密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String key, String md5) throws Exception {
        //根据传入的密钥进行验证
        String md5Text = md5(text, key);
        if(md5Text.equalsIgnoreCase(md5))
        {
            System.out.println("MD5验证通过");
            return true;
        }

            return false;
    }

	public static String generateRandomString(int length) {
		// 产生随机数
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		// 循环length次
		for (int i = 0; i < length; i++) {
			// 产生0-2之间的一个随机数，既与a-z，A-Z，0-9三种可能
			int number = random.nextInt(3);
			long result;
			switch (number) {
			// 如果number产生的是数字0；
			case 0:
				// 产生A-Z的ASCII码
				result = Math.round(Math.random() * 25 + 65);
				// 将ASCII码转换成字符
				sb.append(String.valueOf((char) result));
				break;
			case 1:
				// 产生a-z的ASCII码
				result = Math.round(Math.random() * 25 + 97);
				sb.append(String.valueOf((char) result));
				break;
			case 2:
				// 产生0-9的数字
				sb.append(String.valueOf(new Random().nextInt(10)));
				break;
			}
		}
		return sb.toString();
	}
	
	/**
	* @Author Yangy
	* @Description md5加密--32位16进制小写
	* @Date 15:06 2023/2/3
	* @Param [str]
	* @return java.lang.String
	**/
	public final static String MD5(String str) {
        char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f' };
        try {
            byte[] strTemp = str.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte tmp[] = mdTemp.digest(); // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char strs[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i]; // 取第 i 个字节
                strs[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                strs[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
//            return new String(strs).toUpperCase(); // 换后的结果转换为字符串
            return new String(strs).toLowerCase(); // 换后的结果转换为字符串
        } catch (Exception e) {
            return null;
        }
    }
	
}
