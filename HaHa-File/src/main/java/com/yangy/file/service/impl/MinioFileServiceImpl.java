package com.yangy.file.service.impl;

import com.yangy.file.entity.File;
import com.yangy.file.service.AbstractFileService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: Yangy
 * @Date: 2023/11/13 10:48
 * @Description
 */
@Service
@ConditionalOnProperty(name = "file.service.choose",havingValue = "minio",matchIfMissing = false)
public class MinioFileServiceImpl extends AbstractFileService {

	@Override
	public List<File> uploadFile(List<MultipartFile> fileList) {
		return null;
	}

	@Override
	public Object downloadFile() {
		return null;
	}
}
