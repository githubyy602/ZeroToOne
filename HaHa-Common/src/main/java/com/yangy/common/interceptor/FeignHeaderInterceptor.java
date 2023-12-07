package com.yangy.common.interceptor;

import com.yangy.common.constant.CommonConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @Author: Yangy
 * @Date: 2023/12/6 16:46
 * @Description
 */
public class FeignHeaderInterceptor implements RequestInterceptor {
	@Override
	public void apply(RequestTemplate template) {
		//添加feign header，内部请求时放行token和sign校验
		template.header(CommonConstant.HEADER_CHECK_REQ,CommonConstant.HEADER_FROM_FEIGN);
	}
}
