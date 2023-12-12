package com.yangy.common.feign;

import com.github.pagehelper.PageInfo;
import com.yangy.common.bean.PageQuery;
import com.yangy.common.bean.ResultBean;
import com.yangy.common.bean.feign.ArticleVo;
import com.yangy.common.hystrix.BusinessFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Yangy
 * @Date: 2023/10/24 11:02
 * @Description
 */
@FeignClient(name = "${sys.serviceName.business}",path = "${sys.serviceContext.business}",
		url = "http://localhost:20013",fallback = BusinessFallBack.class)
public interface BusinessFeignClient {
	
	@RequestMapping(value = "/article/getArticleList",method = RequestMethod.POST)
	ResultBean<PageInfo<ArticleVo>> getArticleList(PageQuery query);
	
	@RequestMapping(value = "/article/getArticleDetail",method = RequestMethod.POST)
	ResultBean<ArticleVo> getArticleDetail(@RequestParam("id") Integer id);
	
}
