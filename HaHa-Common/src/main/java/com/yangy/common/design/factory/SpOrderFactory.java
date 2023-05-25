package com.yangy.common.design.factory;

import org.springframework.stereotype.Component;

/**
 * @Author: Yangy
 * @Date: 2023/5/24 16:54
 * @Description 具体工厂
 */
@Component("SpOrderFactory")
public class SpOrderFactory implements OrderFactory {
	
	@Override
	public OrderProcessor getOrder() {
		return new SpOrder();
	}
}
