package com.yangy.business.bean.VO;

import com.yangy.business.bean.PO.Articles;
import com.yangy.common.annotation.EncryptEntity;
import com.yangy.common.annotation.EncryptField;
import lombok.Data;

/**
 * @author Yangy
 * @create 2024-05-21 15:27
 */
@Data
@EncryptEntity
public class ArticlePopularDataVo extends Articles {

    //文章类型的总计数量
    private Integer num;

    @EncryptField
    private String creatorName;
}
