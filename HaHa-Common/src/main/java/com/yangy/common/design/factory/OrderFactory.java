package com.yangy.common.design.factory;

/**
 * @Author: Yangy
 * @Date: 2023/5/24 16:53
 * @Description 抽象工厂
 */
public interface OrderFactory {
	
	OrderProcessor getOrder();
	
}
