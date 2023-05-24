package com.yangy.common.design.factory;

/**
 * @Author: Yangy
 * @Date: 2023/5/24 17:08
 * @Description
 */
public class SpOrder implements OrderProcessor {

	@Override
	public String createOrder() {
		return "创建了一个SP品类的订单";
	}
}
