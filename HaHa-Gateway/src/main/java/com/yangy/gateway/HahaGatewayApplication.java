package com.yangy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@EnableDiscoveryClient
@SpringBootApplication
public class HahaGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(HahaGatewayApplication.class, args);
	}
	
	@Bean(name = "remoteAddrKeyResolver")
	public KeyResolver remoteAddrKeyResolver() {
		//按请求ip限流
	    return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
	}
	
}
