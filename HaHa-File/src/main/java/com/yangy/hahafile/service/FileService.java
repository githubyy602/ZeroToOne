package com.yangy.hahafile.service;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: Yangy
 * @Date: 2023/8/29 15:35
 * @Description
 */
public interface FileService {
	
	void encryptFile() throws UnsupportedEncodingException, NoSuchAlgorithmException, FileNotFoundException;
	
	void decryptFile();
	
	//todo 上传
	
	//todo 下载
}
