package com.yangy.business.service;

import com.yangy.business.bean.PO.ArticleInteractData;
import com.yangy.business.bean.VO.ArticlePopularDataVo;
import com.yangy.business.constant.ArticleInteractEnum;

import java.util.List;

/**
* @author yangyu
* @description 针对表【tb_article_interact_data(文章互动数据记录)】的数据库操作Service
* @createDate 2024-05-17 16:47:54
*/
public interface ArticleInteractDataService {

    int addInteract(ArticleInteractData articleInteractData);

    List<ArticlePopularDataVo> queryArticleDataList(ArticleInteractEnum articleInteractEnum);
}
