package com.yangy.hahaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@ComponentScan(basePackages = "com.yangy")
@EnableWebMvc
@SpringBootApplication
public class HaHaWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaHaWebApplication.class, args);
	}

}
