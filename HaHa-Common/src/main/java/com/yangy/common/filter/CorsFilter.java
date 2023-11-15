package com.yangy.common.filter;

import com.yangy.common.constant.CharacterConstant;
import org.apache.http.HttpStatus;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Yangy
 * @Date: 2023/11/13 14:43
 * @Description
 */
public class CorsFilter implements Filter {
	
	public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
	public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		//跨域
		response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, CharacterConstant.PUNCTUATION_ASTERISK);
		//跨域 Header
		response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, CharacterConstant.PUNCTUATION_ASTERISK);
		response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type,XFILENAME,XFILECATEGORY,XFILESIZE");
		// 浏览器是会先发一次options请求，如果请求通过，则继续发送正式的post请求
		// 配置options的请求返回
		if ("OPTIONS".equals(request.getMethod())) {
			response.setStatus(HttpStatus.SC_OK);
			// hresp.setContentLength(0);
			response.getWriter().write("OPTIONS returns OK");
			return;
		}
	}
}
