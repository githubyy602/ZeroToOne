package com.yangy.common.feign;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.bean.feign.Articles;
import com.yangy.common.hystrix.BusinessFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: Yangy
 * @Date: 2023/10/24 11:02
 * @Description
 */
@FeignClient(name = "${sys.serviceName.business}",path = "${sys.serviceContext.business}",fallback = BusinessFallBack.class)
public interface BusinessFeignClient {
	
	@RequestMapping(value = "/article/getArticleDetail/{id}",method = RequestMethod.GET)
	ResultBean<Articles> getArticleDetail(@PathVariable Integer id);
	
}
