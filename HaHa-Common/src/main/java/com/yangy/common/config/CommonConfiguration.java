//package com.yangy.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.cloud.context.com.yangy.config.annotation.RefreshScope;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.com.yangy.config.annotation.WebMvcConfigurer;
//
///**
// * @Author: Yangy
// * @Date: 2023/3/16 16:13
// * @Description
// */
//public class CommonConfiguration {
//    @ConditionalOnClass({WebMvcConfigurer.class, HandlerInterceptor.class})
//    @RefreshScope
//    @Bean
//    public WebMvcConfig defaultWebMvcConfigurer(DefaultSignVerifyInterceptor signVerifyIntercepter) {
//        return new DefaultWebMvcConfigurer(signVerifyIntercepter);
//    }
//}
