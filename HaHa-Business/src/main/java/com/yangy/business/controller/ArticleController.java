package com.yangy.business.controller;

import com.yangy.business.service.ArticleService;
import com.yangy.common.bean.PageQuery;
import com.yangy.common.bean.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yangy
 * @Date: 2023/10/23 11:30
 * @Description 文章业务控制器
 */
@RestController
@RequestMapping(value = "/article")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@PostMapping("/getArticleList")
	public ResultBean getArticleList(PageQuery query){
		return ResultBean.success(articleService.queryArticleListByPage(query));
	}
	
}
