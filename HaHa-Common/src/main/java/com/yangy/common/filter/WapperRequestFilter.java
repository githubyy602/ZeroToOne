package com.yangy.common.filter;

import com.yangy.common.wrapper.HttpRequestWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: Yangy
 * @Date: 2023/5/10 15:09
 * @Description 这个是为了满足在HandlerInterceptor中HttpServletRequest被获取入参后，参数丢失问题。
 * 所以必须此处透传下去，controller层才能获取到
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "wapperRequestFilter")
public class WapperRequestFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    ServletRequest requestWrapper = null;
    if(servletRequest instanceof HttpServletRequest) {
      requestWrapper = new HttpRequestWrapper((HttpServletRequest) servletRequest);
    }
    if(requestWrapper == null) {
      filterChain.doFilter(servletRequest, servletResponse);
    } else {
        // 将请求封装并传递下去
      filterChain.doFilter(requestWrapper, servletResponse);
    }
  }

  @Override
  public void destroy() {

  }
}
