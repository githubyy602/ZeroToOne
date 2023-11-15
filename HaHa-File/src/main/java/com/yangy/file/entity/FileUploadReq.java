package com.yangy.file.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: Yangy
 * @Date: 2023/11/13 12:09
 * @Description
 */
@Data
public class FileUploadReq {
//	@JSONField(serialize = false)
	List<MultipartFile> fileList;
	
}
