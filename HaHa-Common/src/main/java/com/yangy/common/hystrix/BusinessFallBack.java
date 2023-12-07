package com.yangy.common.hystrix;

import com.github.pagehelper.PageInfo;
import com.yangy.common.bean.PageQuery;
import com.yangy.common.bean.ResultBean;
import com.yangy.common.bean.feign.ArticleVo;
import com.yangy.common.bean.feign.Articles;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.feign.BusinessFeignClient;
import org.springframework.stereotype.Component;

/**
 * @Author: Yangy
 * @Date: 2023/10/24 11:03
 * @Description
 */
@Component
public class BusinessFallBack implements BusinessFeignClient {

	@Override
	public ResultBean<Articles> getArticleDetail(Integer id) {
		return ResultBean.returnResult(ResponseCodeEnum.FEIGN_ERROR);
	}

	@Override
	public ResultBean<PageInfo<ArticleVo>> getArticleList(PageQuery query) {
		return ResultBean.returnResult(ResponseCodeEnum.FEIGN_ERROR);
	}
}
