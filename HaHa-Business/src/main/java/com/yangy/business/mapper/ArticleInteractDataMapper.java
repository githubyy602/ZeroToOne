package com.yangy.business.mapper;

import com.yangy.business.bean.PO.ArticleInteractData;
import com.yangy.business.bean.VO.ArticlePopularDataVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author yangyu
* @description 针对表【tb_article_interact_data(文章互动数据记录)】的数据库操作Mapper
* @createDate 2024-05-17 16:47:54
* @Entity com.yangy.business.bean.PO.ArticleInteractData
*/
@Mapper
public interface ArticleInteractDataMapper{
    int insertSelective(ArticleInteractData articleInteractData);

    List<ArticlePopularDataVo> selectArticleData(Integer type);
}
