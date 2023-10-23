package com.yangy.business.service;

import com.github.pagehelper.PageInfo;
import com.yangy.business.bean.PO.Articles;
import com.yangy.common.bean.PageQuery;

/**
 * @Author: Yangy
 * @Date: 2023/10/23 11:39
 * @Description
 */
public interface ArticleService {
	
	PageInfo<Articles> queryArticleListByPage(PageQuery query);
	
}
