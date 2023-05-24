package com.yangy.common.design.strategy;

/**
 * @Author: Yangy
 * @Date: 2023/5/24 14:13
 * @Description 策略接口，定义一种行为，后续各种策略类，实现不同的具体行文
 */
public interface EnquiryStrategy {
	
	//询价
	String enquiryPrice(String bankName);
	
}
