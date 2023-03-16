package com.yangy.hahauser;

import com.yangy.common.constant.CommonConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {CommonConstant.BASE_PACKAGE})
@SpringBootApplication
public class HaHaUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaHaUserApplication.class, args);
	}

}
