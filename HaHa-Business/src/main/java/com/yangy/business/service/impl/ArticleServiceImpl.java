package com.yangy.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yangy.business.bean.PO.Articles;
import com.yangy.business.bean.VO.ArticleVo;
import com.yangy.business.mapper.ArticlesMapper;
import com.yangy.business.service.ArticleService;
import com.yangy.common.bean.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: Yangy
 * @Date: 2023/10/23 11:39
 * @Description
 */
@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	private ArticlesMapper articlesMapper;

	@Override
	public PageInfo<ArticleVo> queryArticleListByPage(PageQuery query) {

		PageHelper.startPage(query.getPageIndex(),query.getPageSize());
		List<ArticleVo> articlesList = articlesMapper.selectAllArticles();
		PageInfo<ArticleVo> articlesPageInfo = new PageInfo<>(articlesList);
		return articlesPageInfo;
	}

	@Override
	public ArticleVo queryOne(Integer id) {
		return articlesMapper.selectArticleDetail(id);
	}

	@Override
	public Articles insertArticle(Articles articles) {
		articles.setCreateTime(new Date());
		articles.setPublishTime(new Date());
		articles.setStatus(2);
		articles.setView(1);
		articlesMapper.insertSelective(articles);
		return articles;
	}

	@Override
	public int updateArticle(Articles articles) {
		return articlesMapper.updateByPrimaryKeySelective(articles);
	}
}
