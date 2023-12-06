package com.yangy.business.bean.VO;

import com.yangy.business.bean.PO.Articles;
import com.yangy.common.annotation.EncryptEntity;
import com.yangy.common.annotation.EncryptField;

/**
 * @Author: Yangy
 * @Date: 2023/12/5 17:46
 * @Description
 */
@EncryptEntity
public class ArticleVo extends Articles {
	
	@EncryptField
    private String userName;
	
	@EncryptField
    private String imgUrl;
	
    //用户职称
    private String userTitle;
	
}
