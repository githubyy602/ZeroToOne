package com.yangy.file.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.exception.CustomException;
import com.yangy.file.constant.FileConstant;
import com.yangy.file.entity.File;
import com.yangy.file.mapper.FileDao;
import com.yangy.file.service.AbstractFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: Yangy
 * @Date: 2023/12/14 15:23
 * @Description 七牛云
 */
@Service
@ConditionalOnProperty(name = "file.service.choose",havingValue = "qny",matchIfMissing = false)
@Slf4j
public class QnyFileServiceImpl extends AbstractFileService {
	
	@Value("${qny.accessKey:8f6kXZ-AU6MVY7S3e6O6pIzv3sCi9VZ6iaq1zpnN}")
	private String qnyAccessKey="8f6kXZ-AU6MVY7S3e6O6pIzv3sCi9VZ6iaq1zpnN";
	
	private final static String DOMAIN = "http://s5l4ugp8r.bkt.clouddn.com/";
	private String qnyScreteKey;
	private String bucket;

	private final Auth auth;
	private final UploadManager uploadManager;
	
	@Autowired
	private FileDao fileDao;
	
	public QnyFileServiceImpl() {
		qnyScreteKey = System.getProperty("qny.skey");
		log.info("akey={},skey={}",qnyAccessKey,qnyScreteKey);
		this.bucket = "hahashow";
		this.auth = Auth.create(qnyAccessKey, qnyScreteKey);;
		this.uploadManager = new UploadManager(new Configuration());
	}

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
				//默认小于20M
				if(size > 20*1024*1024){
					throw CustomException.custom(ResponseCodeEnum.FILE_SIZE_ERROR.getCode());
				}

				// 文件类型
				String type = file.getContentType();
				String fileSuffix = FileUtil.extName(originalFilename);;

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
				if(StringUtils.contains(FileConstant.IMAGE_SUFFIX,fileSuffix)){
					prefix_path = "images";
				}else if(StringUtils.contains(FileConstant.FILE_SUFFIX,fileSuffix)){
					prefix_path = "file";
				}else {
					throw CustomException.custom(ResponseCodeEnum.FILE_TYPE_ERROR.getCode());
				}

				String filePath =  prefix_path+ StrUtil.C_SLASH+originalFilename;

				String uploadToken =  auth.uploadToken(bucket);
				Response response = uploadManager.put(file.getBytes(),prefix_path+StrUtil.C_SLASH+originalFilename,uploadToken);
				log.info("Qny upload response : {}",response.bodyString());
				// 解析上传成功的结果
				DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);

				insertFile.setPath(filePath);
				insertFile.setUrl(DOMAIN+putRet.key);
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
