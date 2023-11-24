package com.yangy.file;

import com.yangy.common.constant.CommonConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.nio.charset.StandardCharsets;

@ComponentScan(basePackages = {CommonConstant.BASE_PACKAGE})
@SpringBootApplication
public class HahaFileApplication {

	public static void main(String[] args) {
		SpringApplication.run(HahaFileApplication.class, args);
	}
	
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding(StandardCharsets.UTF_8.name());
		multipartResolver.setMaxUploadSize(-1);
		return multipartResolver;
	}

}
