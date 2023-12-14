package com.yangy.file.controller;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.exception.CustomException;
import com.yangy.file.entity.File;
import com.yangy.file.entity.FileUploadReq;
import com.yangy.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yangy
 * @Date: 2023/11/13 11:12
 * @Description
 */
@RestController
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	@PostMapping(value = "/upload")
    public ResultBean<File> upload(FileUploadReq req) throws CustomException {
		//todo 可以改为免费oss存储，https://developer.qiniu.com/kodo/1233/console-quickstart
		return ResultBean.success(fileService.uploadFile(req.getFileList()));
	}

//	public static void main(String[] args) {
//		SpringApplication app = new SpringApplication(HahaFileApplication.class);
//
//		// 获取Environment对象
//		Environment environment = app.run(args).getEnvironment();
//
//		// 通过getProperty获取启动参数
//		String configPath = environment.getProperty("qny.skey");
//		System.out.println("Config Path: " + configPath);
//	}
}
