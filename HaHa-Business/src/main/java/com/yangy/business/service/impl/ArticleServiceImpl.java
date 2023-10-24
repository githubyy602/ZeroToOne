package com.yangy.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yangy.business.bean.PO.Articles;
import com.yangy.business.mapper.ArticlesMapper;
import com.yangy.business.service.ArticleService;
import com.yangy.common.bean.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public PageInfo<Articles> queryArticleListByPage(PageQuery query) {

		PageHelper.startPage(query.getPageIndex(),query.getPageSize());
		List<Articles> articlesList = articlesMapper.selectAllArticles();
		PageInfo<Articles> articlesPageInfo = new PageInfo<>(articlesList);
		return articlesPageInfo;
	}

	@Override
	public Articles queryOne(Integer id) {
		return articlesMapper.selectByPrimaryKey(id);
	}
}
