package com.yangy.send;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class HaHaSendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaHaSendApplication.class, args);
	}

}
