package com.yangy.hahafile.service.impl;

import com.yangy.common.util.AESUtils;
import com.yangy.hahafile.service.FileService;
import com.yangy.hahafile.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @Author: Yangy
 * @Date: 2023/8/29 15:36
 * @Description
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
	
	private static final String ORIGIN_FILE = "D:\\Java\\***\\file\\origin\\";
	private static final String ENCRYPT_KEY = "ewr*hu82)1jjf@";
	
	@Override
	public void encryptFile(){
		
		String txtFile = "练习题笔记.txt";
		
		try {
			String targetFilePath = "D:\\Java\\ZeroToOne\\file\\target\\encrypted\\"+
					"txtEncrypt.txt";
			FileOutputStream fos = new FileOutputStream(targetFilePath);
			FileUtils.encryptFileToStream(ORIGIN_FILE+txtFile,fos,AESUtils.initSecretKey(ENCRYPT_KEY));
		} catch (Exception e) {
			log.error("{}",e.getMessage(),e);
		}
	}

	@Override
	public void decryptFile() {
		
		String txtFile = "D:\\Java\\***\\file\\target\\encrypted\\"+
					"txtEncrypt.txt";
		
		try {
			String targetFilePath = "D:\\Java\\***\\file\\target\\decrypted\\"+
					"txtDecrypt.txt";
			FileInputStream fis = new FileInputStream(txtFile);
			FileUtils.decryptStreamToFile(targetFilePath,fis,AESUtils.initSecretKey(ENCRYPT_KEY));
		} catch (Exception e) {
			log.error("{}",e.getMessage(),e);
		}
	}

	public static void main(String[] args) {
		FileServiceImpl fileService = new FileServiceImpl();
		fileService.encryptFile();
		fileService.decryptFile();
		System.out.println("执行结束！！！");
	}
}
