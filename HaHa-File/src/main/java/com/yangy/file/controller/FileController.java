package com.yangy.file.controller;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.exception.CustomException;
import com.yangy.file.entity.File;
import com.yangy.file.entity.FileUploadReq;
import com.yangy.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	@CrossOrigin(origins = {"http://localhost:30000"})
    public ResultBean<File> upload(FileUploadReq req) throws CustomException {
		return ResultBean.success(fileService.uploadFile(req.getFileList()));
	}
	
	
}
