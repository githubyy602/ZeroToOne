package com.yangy.common.hystrix;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.bean.feign.Articles;
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
		return ResultBean.success("暂时无法获取文章内容，请稍后再试！");
	}
}
