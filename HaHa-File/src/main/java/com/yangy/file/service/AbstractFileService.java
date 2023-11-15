package com.yangy.file.service;

import com.yangy.common.exception.CustomException;
import com.yangy.file.entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: Yangy
 * @Date: 2023/11/13 10:44
 * @Description
 */
public abstract class AbstractFileService {
	
	public abstract List<File> uploadFile(List<MultipartFile> fileList) throws CustomException;
	
	public abstract Object downloadFile();
}
