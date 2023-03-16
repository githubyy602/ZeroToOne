package interceptor;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Yangy
 * @Date: 2021/5/13 17:03
 * @Description
 */
@Configuration
public class SysMvcInterceptor implements WebMvcConfigurer {
	
	@Autowired
	private MyInterceptor myInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(myInterceptor)
					.addPathPatterns("/**");
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
