package com.yangy.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yangy.business.bean.PO.ArticleInteractData;
import com.yangy.business.bean.PO.Articles;
import com.yangy.business.bean.VO.ArticleVo;
import com.yangy.business.constant.ArticleInteractEnum;
import com.yangy.business.mapper.ArticlesMapper;
import com.yangy.business.service.ArticleInteractDataService;
import com.yangy.business.service.ArticleService;
import com.yangy.common.bean.PageQuery;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

/**
 * @Author: Yangy
 * @Date: 2023/10/23 11:39
 * @Description
 */
@Service
@Log4j
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	private ArticlesMapper articlesMapper;

	@Autowired
	private ArticleInteractDataService articleInteractDataService;

	@Override
	public PageInfo<ArticleVo> queryArticleListByPage(@RequestBody PageQuery query) {
		PageHelper.startPage(query.getPageIndex(),query.getPageSize());
		List<ArticleVo> articlesList = articlesMapper.selectAllArticles();
		PageInfo<ArticleVo> articlesPageInfo = new PageInfo<>(articlesList);
		return articlesPageInfo;
	}

	@Override
	public ArticleVo queryOne(Integer id,Integer userId) {
		//查看数记录
		this.saveInteractRecord(id,userId,ArticleInteractEnum.VIEW);
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

	private void saveInteractRecord(int articleId, int userId, ArticleInteractEnum type){

		try {
			if(-1 == userId){
				//编辑文章，不算查看
				return;
			}
			ArticleInteractData data = new ArticleInteractData();
			data.setArticleId(articleId);
			data.setUserId(userId);
			data.setType(type.getType());
			articleInteractDataService.addInteract(data);
		}catch (Exception e){
			log.error("文章互动记录新增异常！！！",e);
		}

	}

}
