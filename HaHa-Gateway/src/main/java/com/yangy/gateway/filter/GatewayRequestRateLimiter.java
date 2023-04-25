package com.yangy.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Author: Yangy
 * @Date: 2023/4/25 10:44
 * @Description gateway限流limiter，重写限流原过滤器RequestRateLimiterGatewayFilterFactory
 */
@Component
@Slf4j
public class GatewayRequestRateLimiter extends RequestRateLimiterGatewayFilterFactory {

  private final RateLimiter defaultRateLimiter;

  private final KeyResolver defaultKeyResolver;

  public GatewayRequestRateLimiter(RateLimiter defaultRateLimiter, KeyResolver defaultKeyResolver) {
    super(defaultRateLimiter, defaultKeyResolver);
    this.defaultRateLimiter = defaultRateLimiter;
    this.defaultKeyResolver = defaultKeyResolver;
  }

  @Override
  public GatewayFilter apply(Config config) {
    KeyResolver resolver = getOrDefault(config.getKeyResolver(), defaultKeyResolver);
    RateLimiter<Object> limiter = getOrDefault(config.getRateLimiter(), defaultRateLimiter);
    return (exchange, chain) -> resolver.resolve(exchange).flatMap(key -> {
      String routeId = config.getRouteId();
      if (routeId == null) {
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        routeId = route.getId();
      }
      String finalRouteId = routeId;
      return limiter.isAllowed(routeId, key).flatMap(response -> {
        for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
          exchange.getResponse().getHeaders().add(header.getKey(), header.getValue());
        }
        
        if (response.isAllowed()) {
          return chain.filter(exchange);
        }
        
        log.warn("已限流: {}", finalRouteId);
        ServerHttpResponse httpResponse = exchange.getResponse();
        //修改code为500
        httpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        if (!httpResponse.getHeaders().containsKey("Content-Type")) {
          httpResponse.getHeaders().add("Content-Type", "application/json");
        }
        
        //此处无法触发全局异常处理，手动返回
        JSONObject object = new JSONObject();
        object.put("status","429");
        object.put("message","请求已被限流");
        DataBuffer buffer = httpResponse.bufferFactory().wrap(object.toJSONString().getBytes(StandardCharsets.UTF_8));

        return httpResponse.writeWith(Mono.just(buffer));
      });
    });
  }

  private <T> T getOrDefault(T configValue, T defaultValue) {
    return (configValue != null) ? configValue : defaultValue;
  }
}
