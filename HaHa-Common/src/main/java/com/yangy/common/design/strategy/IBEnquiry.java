package com.yangy.common.design.strategy;

/**
 * @Author: Yangy
 * @Date: 2023/5/24 14:43
 * @Description
 */
public class IBEnquiry implements EnquiryStrategy {

	@Override
	public String enquiryPrice(String bankName) {
		
		if(bankName.equals("投行名称")){
			return "今日询价为：99.80";
		}
		
		return "不是投行，无法获取询价";
	}
}
