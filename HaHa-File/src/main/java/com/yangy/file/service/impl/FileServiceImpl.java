package com.yangy.file.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.yangy.common.exception.CustomException;
import com.yangy.common.util.AESUtils;
import com.yangy.file.entity.File;
import com.yangy.file.mapper.FileDao;
import com.yangy.file.service.AbstractFileService;
import com.yangy.file.service.FileService;
import com.yangy.file.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

/**
 * @Author: Yangy
 * @Date: 2023/8/29 15:36
 * @Description
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
	
	private static final String ORIGIN_FILE = "D:\\Java\\ZeroToOne\\file\\origin\\";
	private static final String ENCRYPT_KEY = "ewr*hu82)1jjf@";
	
	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private AbstractFileService fileService;
	
	@Override
	public List<File> uploadFile(List<MultipartFile> list) throws CustomException {
		List<File> fileList = fileService.uploadFile(list);
		if(CollectionUtil.isNotEmpty(fileList)){
			createFile(fileList);
		}
		return fileList;
	}

	@Override
	public void encryptFile(){
		
		String txtFile = "微软认证单点对接.zip";
		
		try {
			FileInputStream fis = new FileInputStream(ORIGIN_FILE+txtFile);
			String targetFilePath = "D:\\Java\\ZeroToOne\\file\\target\\encrypted\\"+
					"zipEncrypt.zip";
			FileOutputStream fos = new FileOutputStream(targetFilePath);
			FileUtils.encryptFileToStream(fis,fos,AESUtils.initSecretKey(ENCRYPT_KEY));
		} catch (Exception e) {
			log.error("{}",e.getMessage(),e);
		}
	}

	@Override
	public void decryptFile() {
		
		String txtFile = "D:\\Java\\ZeroToOne\\file\\target\\encrypted\\"+
					"zipEncrypt.zip";
		
		try {
			FileInputStream fis = new FileInputStream(txtFile);
			String targetFilePath = "D:\\Java\\ZeroToOne\\file\\target\\decrypted\\"+
					"zipDecrypt.zip";
			FileOutputStream fos = new FileOutputStream(targetFilePath);
			FileUtils.decryptStreamToFile(fis,fos,AESUtils.initSecretKey(ENCRYPT_KEY));
		} catch (Exception e) {
			log.error("{}",e.getMessage(),e);
		}
	}

	@Override
	public int createFile(List<File> files) {
		return fileDao.batchInsertFiles(files);
	}

	public static void main(String[] args) {
		FileServiceImpl fileService = new FileServiceImpl();
		fileService.encryptFile();
		fileService.decryptFile();
		System.out.println("执行结束！！！");
	}
}
