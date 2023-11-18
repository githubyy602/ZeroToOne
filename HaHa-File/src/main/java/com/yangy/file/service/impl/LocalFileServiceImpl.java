package com.yangy.file.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Charsets;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.exception.CustomException;
import com.yangy.file.entity.File;
import com.yangy.file.mapper.FileDao;
import com.yangy.file.service.AbstractFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	public static String IMAGE_SUFFIX = "image,jpg,png,jpeg";
	public static String FILE_SUFFIX = "txt,doc,pdf,csv";
	
	@Autowired
	private FileDao fileDao;
	
	@Override
	public List<File> uploadFile(List<MultipartFile> fileList) throws CustomException {
		try {
			List<File> records = new ArrayList<>();
			List<File> insertList = new ArrayList<>();
			for (MultipartFile file : fileList) {
				// 文件原始名
				String originalFilename = file.getOriginalFilename();
				// 文件大小
				long size = file.getSize();
				// 文件类型
				String type = file.getContentType();
				
				File insertFile = File.builder()
						.fileName(originalFilename)
						.fileSize(size+"")
						.type(type)
						.encrypted(0).build();
				File existFile = fileDao.selectByNameSize(insertFile);
				if(Objects.nonNull(existFile)){
					records.add(existFile);
					continue;
				}
				
				String prefix_path = "";
				if(StringUtils.contains(type,"image")){
					prefix_path = "/image";
				}else{
					prefix_path = "/file";
				}
				
				String filePath =  prefix_path+StrUtil.C_SLASH+originalFilename;
				
				java.io.File newFile = new java.io.File(localPath+filePath);
				file.transferTo(newFile);
				
				String base64Path = Base64Utils.encodeToString(filePath.getBytes(Charsets.UTF_8));
				insertFile.setPath(base64Path);
				insertFile.setUrl(base64Path);
				insertList.add(insertFile);
			}
			
			if(CollectionUtil.isNotEmpty(insertList)){
				fileDao.batchInsertFiles(insertList);
				records.addAll(insertList);
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
