package com.yangy.file.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: Yangy
 * @Date: 2023/8/29 15:06
 * @Description
 */
public class FileUtils {
	
	private static final String KEY_ALGORITHM = "AES";
	//默认的加密算法
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	
	/**
     * 上传文件
     * @param localFilePath 本地文件路径
     * @param uploadUrl 上传地址
     * @return 上传结果
     */
//    public static String uploadFile(String localFilePath, String uploadUrl) {
//        File file = new File(localFilePath);
//        return HttpUtil.post(uploadUrl, file);
//    }
//
//    /**
//     * 下载文件
//     * @param downloadUrl 下载地址
//     * @param saveFilePath 保存文件路径
//     */
//    public static void downloadFile(String downloadUrl, String saveFilePath) {
//        FileUtil.writeFromStream(HttpUtil.downloadBytes(downloadUrl), saveFilePath);
//    }
	
    /**
     * 将文件写入输出流
     * @param filePath 文件路径
     * @param outputStream 输出流
     * @throws IOException IO异常
     */
    public static void writeFileToStream(String filePath, OutputStream outputStream) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        fileInputStream.close();
    }

    /**
     * 从输入流读取数据并写入文件
     * @param inputStream 输入流
     * @param saveFilePath 保存文件路径
     * @throws IOException IO异常
     */
    public static void writeStreamToFile(InputStream inputStream, String saveFilePath) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(saveFilePath);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, length);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        inputStream.close();
    }
    
    /**
     * 将文件进行AES加密后写入输出流
     * @param inputStream 文件输入流
     * @param outputStream 文件输出流
     * @param key 密钥
     * @throws IOException IO异常
     * @throws NoSuchAlgorithmException 未找到算法异常
     * @throws NoSuchPaddingException 未找到填充方式异常
     * @throws InvalidKeyException 无效密钥异常
     * @throws IllegalBlockSizeException 非法块大小异常
     * @throws BadPaddingException 错误填充异常
     */
    public static void encryptFileToStream(InputStream inputStream, OutputStream outputStream, byte [] key)
            throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException
            {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            cipherOutputStream.write(buffer, 0, length);
        }
        cipherOutputStream.flush();
        cipherOutputStream.close();
        outputStream.close();
        inputStream.close();
    }

    /**
     * 从输入流读取经过AES解密后的数据并写入文件
     * @param inputStream 文件输入流
	 * @param outputStream 文件输出流
     * @param key 密钥
     * @throws IOException IO异常
     * @throws NoSuchAlgorithmException 未找到算法异常
     * @throws NoSuchPaddingException 未找到填充方式异常
     * @throws InvalidKeyException 无效密钥异常
     * @throws IllegalBlockSizeException 非法块大小异常
     * @throws BadPaddingException 错误填充异常
     */
    public static void decryptStreamToFile(InputStream inputStream, OutputStream outputStream , byte [] key)
            throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException
			{
				
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = cipherInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        cipherInputStream.close();
        inputStream.close();
    }
    
    /**
    * @Author Yangy
    * @Description 获取加密后的文件输入流
    * @Date 11:57 2023/8/31
    * @Param [inputStream, key]
    * @return java.io.InputStream
    **/
    public static InputStream encryptStream(InputStream inputStream, byte [] key)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException
            {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        
        CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
        return cipherInputStream;
    }
    
    /**
    * @Author Yangy
    * @Description 获取解密后的文件输出流
    * @Date 11:57 2023/8/31
    * @Param [outputStream, key]
    * @return java.io.OutputStream
    **/
    public static OutputStream decryptStream(OutputStream outputStream , byte [] key)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException
			{
				
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        
        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
        return cipherOutputStream;
    }
}
