package com.yangy.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Yangy
 * @Date: 2021/5/13 16:22
 * @Description
 */
public abstract class CommonInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpRequestWrapper httpRequestWrapper = new HttpRequestWrapper(request);
		response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
		return check(httpRequestWrapper,response,handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

	public abstract boolean check(HttpRequestWrapper httpRequestWrapper, HttpServletResponse res, Object handler) throws IOException;
}
