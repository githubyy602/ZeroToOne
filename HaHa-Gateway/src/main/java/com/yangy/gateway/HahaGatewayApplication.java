package com.yangy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class HahaGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(HahaGatewayApplication.class, args);
	}

}
