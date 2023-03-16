package com.yangy.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.yangy.common.bean.ResultBean;
import com.yangy.common.constant.CommonConstant;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.exception.CustomException;
import com.yangy.common.util.TokenUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Yangy
 * @Date: 2021/5/13 16:45
 * @Description
 */
public class MyInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		//跨域请求会首先发一个option请求，直接返回正常状态并通过拦截器
        if (request.getMethod().equals(CommonConstant.METHOD_TYPE_OPTIONS)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
		
		try {
			String token = request.getHeader(CommonConstant.HEADER_ACCESS_TOKEN);
			return TokenUtil.checkToken(token);
		} catch (CustomException e) {
			response.getOutputStream().write(JSON.toJSONString(ResultBean.returnResult(ResponseCodeEnum.getResp(e.getCode())))
					.getBytes(CommonConstant.CHARSET_UTF8)
			);
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
