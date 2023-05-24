package com.yangy.common.design.factory;

/**
 * @Author: Yangy
 * @Date: 2023/5/24 16:54
 * @Description
 */
public class SpOrderFactory implements OrderFactory {
	
	@Override
	public OrderProcessor getOrder() {
		return new SpOrder();
	}
}
