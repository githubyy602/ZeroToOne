package com.yangy.search.controller;

import com.yangy.search.impl.EsServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author: Yangy
 * @Date: 2023/7/10 14:48
 * @Description
 */
@RequestMapping("/basic")
@RestController
public class BasicController {
	
	@Resource
	private EsServiceImpl esService;
	
	/**
	* @Author Yangy
	* @Description 创建索引
	* @Date 11:46 2023/7/20
	* @Param []
	* @return java.lang.Object
	**/
	@PostMapping("/createIndex")
	public Object createIndex() throws IOException {
		return esService.operateOfCreateIndex();
	}
	
	/**
	* @Author Yangy
	* @Description 删除索引
	* @Date 11:47 2023/7/20
	* @Param []
	* @return java.lang.Object
	**/
	@PostMapping("/deleteIndex")
	public Object deleteIndex() throws IOException {
		return esService.operateOfDelIndex();
	}
	
	
	/**
	* @Author Yangy
	* @Description 创建mapping
	* @Date 11:47 2023/7/20
	* @Param []
	* @return java.lang.Object
	**/
	@PostMapping("/createMapping")
	public Object createMapping() throws IOException {
		return esService.operateOfCreateMapping();
	}
	
	/**
	* @Author Yangy
	* @Description 创建索引且创建mapping
	* @Date 11:47 2023/7/20
	* @Param []
	* @return java.lang.Object
	**/
	@PostMapping("/createIndexAndMapping")
	public Object createIndexAndMapping() throws IOException {
		return esService.operateOfCreateAndAddMapping();
	}
	
	/**
	* @Author Yangy
	* @Description 添加doc数据
	* @Date 12:16 2023/7/20
	* @Param []
	* @return java.lang.Object
	**/
	@PostMapping("/addDoc")
	public Object addDoc() throws IOException {
//		return esService.operateOfAddDoc();
		return esService.operateOfAddDocByClass();
	}
	
	/**
	* @Author Yangy
	* @Description 查询所有文档
	* @Date 15:09 2023/7/20
	* @Param []
	* @return java.lang.String
	**/
	@PostMapping("/getAllData")
	public Object getData() throws IOException {
		return esService.searchAllData();
	}
	
	/**
	* @Author Yangy
	* @Description 查询指定doc
	* @Date 15:14 2023/7/20
	* @Param []
	* @return java.lang.Object
	**/
	@PostMapping("/queryData")
	public Object queryData() throws IOException {
		return esService.getData();
	}
	
	/**
	* @Author Yangy
	* @Description 覆盖修改整条doc
	* @Date 16:54 2023/7/20
	* @Param []
	* @return java.lang.Object
	**/
	@PostMapping("/updateData")
	public Object updateData() throws IOException {
		return esService.updateData();
	}
	
	/**
	* @Author Yangy
	* @Description 修改某条doc的部分内容
	* @Date 16:54 2023/7/20
	* @Param []
	* @return java.lang.Object
	**/
	@PostMapping("/updateField")
	public Object updateField() throws IOException {
		return esService.updateDataField();
	}
	
	/**
	* @Author Yangy
	* @Description 删除指定doc
	* @Date 17:11 2023/7/20
	* @Param []
	* @return java.lang.Object
	**/
	@PostMapping("/deletaData")
	public Object deletaData() throws IOException {
		return esService.delData();
	}
}
