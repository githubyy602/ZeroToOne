package com.yangy.gateway.filter;

/**
 * @Author: Yangy
 * @Date: 2023/3/23 11:26
 * @Description 与 GatewayFilter 的作用一样。
 * 区别在于 GlobalFilter 的逻辑可以写代码来自定义规则；
 * 而 GatewayFilter 通过配置定义，处理逻辑是固定的。
 */
//@Component
//public class GatewayFilter implements GlobalFilter,Ordered {
//	
//	@Override
//	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//		//todo 例如请求加解密处理、白名单校验等等
//		return chain.filter(exchange);
//	}
//
//	//设置过滤器优先级，值越低优先级越高
//	@Override
//	public int getOrder() {
//		return 10;
//	}
//}
