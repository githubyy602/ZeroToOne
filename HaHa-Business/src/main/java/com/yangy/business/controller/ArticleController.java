package com.yangy.business.controller;

import com.github.pagehelper.PageInfo;
import com.yangy.business.bean.VO.ArticleVo;
import com.yangy.business.service.ArticleService;
import com.yangy.common.bean.PageQuery;
import com.yangy.common.bean.ResultBean;
import com.yangy.common.bean.feign.Articles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResultBean<PageInfo<ArticleVo>> getArticleList(PageQuery query){
		return ResultBean.success(articleService.queryArticleListByPage(query));
	}
	
	@PostMapping("/getArticleDetail")
	public ResultBean<Articles> getArticleDetail(@RequestParam Integer id){
		return ResultBean.success(articleService.queryOne(id));
	}
}
