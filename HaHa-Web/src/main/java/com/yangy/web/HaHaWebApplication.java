package com.yangy.web;

import com.yangy.common.constant.CommonConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = {CommonConstant.FEIGN_PACKAGE})
@ComponentScan(basePackages = CommonConstant.BASE_PACKAGE)
@EnableWebMvc
@SpringBootApplication
public class HaHaWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaHaWebApplication.class, args);
	}

}
