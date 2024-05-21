package com.yangy.business.service.impl;

import com.yangy.business.bean.PO.ArticleInteractData;
import com.yangy.business.bean.VO.ArticlePopularDataVo;
import com.yangy.business.constant.ArticleInteractEnum;
import com.yangy.business.mapper.ArticleInteractDataMapper;
import com.yangy.business.service.ArticleInteractDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author yangyu
* @description 针对表【tb_article_interact_data(文章互动数据记录)】的数据库操作Service实现
* @createDate 2024-05-17 16:47:54
*/
@Service
public class ArticleInteractDataServiceImpl implements ArticleInteractDataService{

    @Autowired
    private ArticleInteractDataMapper articleInteractDataMapper;

    @Override
    public int addInteract(ArticleInteractData articleInteractData) {
        return articleInteractDataMapper.insertSelective(articleInteractData);
    }

    @Override
    public List<ArticlePopularDataVo> queryArticleDataList(ArticleInteractEnum articleInteractEnum) {
        return articleInteractDataMapper.selectArticleData(articleInteractEnum.getType());
    }
}
