package com.yangy.business.controller;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.yangy.business.bean.PO.Articles;
import com.yangy.business.bean.VO.ArticlePopularDataVo;
import com.yangy.business.bean.VO.ArticleVo;
import com.yangy.business.constant.ArticleInteractEnum;
import com.yangy.business.service.ArticleInteractDataService;
import com.yangy.business.service.ArticleService;
import com.yangy.common.bean.PageQuery;
import com.yangy.common.bean.ResultBean;
import com.yangy.common.enums.ResponseCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

	@Autowired
	private ArticleInteractDataService articleInteractDataService;

	@PostMapping("/getArticleList")
	public ResultBean<PageInfo<ArticleVo>> getArticleList(@RequestBody PageQuery query){
		return ResultBean.success(articleService.queryArticleListByPage(query));
	}
	
	@PostMapping("/getArticleDetail")
	public ResultBean<ArticleVo> getArticleDetail(@RequestParam("id") Integer id,
												  @RequestParam(value = "userId",required = false) Integer userId){
		return ResultBean.success(articleService.queryOne(id,userId));
	}
	
	@PostMapping("/createArticle")
	public ResultBean createArticle(@RequestBody Articles articles){
		return ResultBean.success(articleService.insertArticle(articles));
	}
	
	@PostMapping("/updateArticle")
	public ResultBean updateArticle(@RequestBody Articles articles){
		if(ObjectUtil.isAllNotEmpty(articles,articles.getId()) && articles.getId() > 0){
			return ResultBean.success(articleService.updateArticle(articles));
		}else {
			return ResultBean.returnResult(ResponseCodeEnum.PARAM_ERROR);
		}
	}

	@PostMapping("/getPopularArticles")
	public ResultBean<List<ArticlePopularDataVo>> getPopularArticles(){
		return ResultBean.success(articleInteractDataService.queryArticleDataList(ArticleInteractEnum.VIEW));
	}
}
