package com.yangy.common.config;

import com.alibaba.fastjson.JSON;
import com.yangy.common.interceptor.MyInterceptor;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Yangy
 * @Date: 2023/3/16 15:42
 * @Description
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MyInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/login/online","/css/**", "/images/**", "/js/**", "/fonts/**");
		//下面这句必须有才生效
		WebMvcConfigurer.super.addInterceptors(registry);
	}
	
	@Bean
	public ServletRegistrationBean<DispatcherServlet> servletServletRegistrationBean(DispatcherServlet dispatcherServlet){
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet);
		//配置请求url后缀匹配。必须结合
		//springboot2.x必须结合 spring.mvc.pathmatch.use-suffix-pattern=true 配置
		servletRegistrationBean.addUrlMappings("*.do");
		System.out.println(JSON.toJSONString(servletRegistrationBean.getUrlMappings()));
		return servletRegistrationBean;
	}
}
