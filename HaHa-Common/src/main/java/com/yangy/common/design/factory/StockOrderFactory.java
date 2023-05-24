package com.yangy.common.design.factory;

/**
 * @Author: Yangy
 * @Date: 2023/5/24 16:56
 * @Description
 */
public class StockOrderFactory implements OrderFactory {

	@Override
	public OrderProcessor getOrder() {
		return new StockOrder();
	}
}
