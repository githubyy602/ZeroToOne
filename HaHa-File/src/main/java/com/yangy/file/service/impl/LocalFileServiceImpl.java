package com.yangy.file.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.exception.CustomException;
import com.yangy.file.entity.File;
import com.yangy.file.service.AbstractFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Yangy
 * @Date: 2023/11/13 10:47
 * @Description
 */
@Service
@ConditionalOnProperty(name = "file.service.choose",havingValue = "local",matchIfMissing = false)
@Slf4j
public class LocalFileServiceImpl extends AbstractFileService {
	
	public String localPath = System.getProperty("user.dir") + "/HaHa-File/src/main" + "/resources/files/";
	public static String IMAGE_SUFFIX = "jpg,png,jpeg";
	public static String FILE_SUFFIX = "txt,doc,pdf,csv";
	
	@Override
	public List<File> uploadFile(List<MultipartFile> fileList) throws CustomException {
		try {
			List<File> records = new ArrayList<>();
			for (MultipartFile file : fileList) {
				// 文件原始名
				String originalFilename = file.getOriginalFilename();
				// 文件类型
				String type = FileUtil.extName(originalFilename);
				String prefix_path = "";
				if(StringUtils.contains(IMAGE_SUFFIX,type)){
					prefix_path = "/image";
				}else{
					prefix_path = "/file";
				}
				// 文件大小
				long size = file.getSize();
				String filePath =  prefix_path+StrUtil.C_SLASH+originalFilename;
				java.io.File newFile = new java.io.File(localPath+filePath);
				file.transferTo(newFile);
				
				File insertFile = File.builder()
						.fileName(originalFilename)
						.fileSize(size+"")
						.type(type)
						.path(filePath)
						.url(filePath)
						.encrypted(0).build();
				records.add(insertFile);
			}
			
			return records;
		} catch (Exception e) {
			log.error("LocalFileServiceImpl.uploadFile occurs an exception!!!\n",e);
			throw CustomException.custom(ResponseCodeEnum.FAIL.getCode());
		}
	}

	@Override
	public Object downloadFile() {
		return null;
	}
}
