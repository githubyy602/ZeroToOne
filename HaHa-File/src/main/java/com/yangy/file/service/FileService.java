package com.yangy.file.service;

import com.yangy.common.exception.CustomException;
import com.yangy.file.entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @Author: Yangy
 * @Date: 2023/8/29 15:35
 * @Description
 */
public interface FileService {
	
	void encryptFile() throws UnsupportedEncodingException, NoSuchAlgorithmException, FileNotFoundException;
	
	void decryptFile();
	
	//todo 上传
	List<File>  uploadFile(List<MultipartFile> fileList) throws CustomException;
	
	//todo 下载
	
	int createFile(List<File> files);
}
