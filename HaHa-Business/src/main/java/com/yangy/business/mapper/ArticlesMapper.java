package com.yangy.business.mapper;

import com.yangy.business.bean.PO.Articles;
import com.yangy.business.bean.VO.ArticleVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticlesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Articles record);

    int insertSelective(Articles record);

    Articles selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Articles record);

    int updateByPrimaryKey(Articles record);
    
    List<ArticleVo> selectAllArticles();
    
    ArticleVo selectArticleDetail(Integer id);
}