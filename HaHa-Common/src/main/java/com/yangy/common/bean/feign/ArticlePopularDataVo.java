package com.yangy.common.bean.feign;

import lombok.Data;

/**
 * @author Yangy
 * @create 2024-05-21 15:27
 */
@Data
public class ArticlePopularDataVo extends Articles {

    //文章类型的总计数量
    private Integer num;

    private String creatorName;
}
