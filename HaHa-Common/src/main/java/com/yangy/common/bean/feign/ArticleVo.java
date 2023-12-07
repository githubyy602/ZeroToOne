package com.yangy.common.bean.feign;

import lombok.Data;

/**
 * @Author: Yangy
 * @Date: 2023/12/5 17:46
 * @Description
 */
@Data
public class ArticleVo extends Articles {
	
    private String userName;
	
    private String imgUrl;
	
    //用户职称
    private String userTitle;
	
}
