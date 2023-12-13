package com.yangy.hahafront;

import com.yangy.common.constant.CommonConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients(basePackages = {CommonConstant.FEIGN_PACKAGE})
@ComponentScan(basePackages = {CommonConstant.BASE_PACKAGE})
@SpringBootApplication
public class HahaFrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(HahaFrontApplication.class, args);
	}

}
