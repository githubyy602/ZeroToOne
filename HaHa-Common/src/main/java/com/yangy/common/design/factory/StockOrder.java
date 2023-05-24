package com.yangy.common.design.factory;

/**
 * @Author: Yangy
 * @Date: 2023/5/24 17:05
 * @Description
 */
public class StockOrder implements OrderProcessor {
	
	@Override
	public String createOrder() {
		return "创建了一个股票类订单";
	}
}
