package com.yangy.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Yangy
 * @Date: 2023/3/13 14:31
 * @Description MAC算法 (Message Authentication Codes) 
 *   带秘密密钥的Hash函数：消息的散列值由只有通信双方知道的秘密密钥K来控制。
 *   此时Hash值称作MAC。
 */
@Slf4j
public class HmacMd5Util {
	/**
     * MAC算法可选以下多种算法
     *
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_MAC = "HmacMD5";

    /**
     * HMAC加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptHMAC(String data, String key){

        try {
            SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), KEY_MAC);
            Mac mac = Mac.getInstance(KEY_MAC);
            mac.init(secretKey);
            return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("HmacMd5Util.encryptHMAC occurs an com.yangy.exception ! \n {}",e);
            return null;
        }
    }

    /*byte数组转换为HexString*/
    public static String byteArrayToHexString(byte[] b) {
       StringBuffer sb = new StringBuffer(b.length * 2);
        for (byte value : b) {
            int v = value & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
       return sb.toString();
     }
     
     public static String encrypt(String data, String key){
        return byteArrayToHexString(encryptHMAC(data,key));
    }
}
