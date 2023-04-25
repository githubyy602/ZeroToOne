package com.yangy.common.util;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * @Author: Yangy
 * @Date: 2023/4/3 18:12
 * @Description
 */
public class DESUtils {

    /**
     * encrypt algorithm
     */
    private final static String DES = "DES";

    /**
     * coding format
     */
    private final static String ENCODE = "UTF-8";

    /**
     * private key
	 * 加密的私钥，位数只要是8的倍数就行
     */
    private final static String KEY = "wer4trt54681!!2e";

    /**
     * encrypt input text using default algorithm
     *
     * @param text input text
     * @return encrypted text
     */
    public static String encrypt(String text) {
        try {
            //generate random number
            SecureRandom sr = new SecureRandom();

            //get SecretKey
            DESKeySpec dks = new DESKeySpec(KEY.getBytes(ENCODE));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);

            //encrypt
            Cipher cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
            byte[] bytes = cipher.doFinal(text.getBytes(ENCODE));

            //return encrypted text
            return new String(Base64Utils.encode(bytes));
        } catch (Exception e) {
            throw new RuntimeException("unable to encrypt text:" + text, e);
        }
    }

    /**
     * decrypt input text using default algorithm
     *
     * @param text input text
     * @return decrypted text
     */
    public static String decrypt(String text) {
        try {
            //generate random number
            SecureRandom sr = new SecureRandom();

            //get SecretKey
            DESKeySpec dks = new DESKeySpec(KEY.getBytes(ENCODE));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);

            //decrypt
            Cipher cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
            byte[] buffer = Base64Utils.decode(text.getBytes());
            byte[] bytes = cipher.doFinal(buffer);

            //return decrypted text
            return new String(bytes, ENCODE);
        } catch (Exception e) {
            throw new RuntimeException("unable to decrypt text:" + text, e);
        }
    }
}

