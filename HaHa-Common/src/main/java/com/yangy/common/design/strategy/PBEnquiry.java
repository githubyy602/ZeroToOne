package com.yangy.common.design.strategy;

/**
 * @Author: Yangy
 * @Date: 2023/5/24 14:42
 * @Description
 */
public class PBEnquiry implements EnquiryStrategy{

	@Override
	public String enquiryPrice(String bankName) {
		if(bankName.equals("私行名称")){
			return "今日询价为：98.80";
		}
		
		return "不是私行，无法获取询价";
	}
}
