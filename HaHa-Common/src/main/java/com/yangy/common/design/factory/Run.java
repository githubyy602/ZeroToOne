package com.yangy.common.design.factory;

/**
 * @Author: Yangy
 * @Date: 2023/5/24 18:22
 * @Description
 */
public class Run {

	public static void main(String[] args) {
		OrderFactory stockFactory = new StockOrderFactory();
		System.out.println(stockFactory.getOrder().createOrder());
		
		OrderFactory spFactory = new SpOrderFactory();
		System.out.println(spFactory.getOrder().createOrder());
	}
	
}
