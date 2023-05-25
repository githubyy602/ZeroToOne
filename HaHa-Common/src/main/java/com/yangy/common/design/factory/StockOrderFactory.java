package com.yangy.common.design.factory;

import org.springframework.stereotype.Component;

/**
 * @Author: Yangy
 * @Date: 2023/5/24 16:56
 * @Description 具体工厂
 */
@Component("StockOrderFactory")
public class StockOrderFactory implements OrderFactory {

	@Override
	public OrderProcessor getOrder() {
		return new StockOrder();
	}
}
