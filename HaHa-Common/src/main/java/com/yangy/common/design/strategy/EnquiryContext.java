package com.yangy.common.design.strategy;

/**
 * @Author: Yangy
 * @Date: 2023/5/24 14:50
 * @Description
 */
public class EnquiryContext{

	private EnquiryStrategy enquiryStrategy;

	//这里的构造选取策略类，其实可以根据spring的@resource加载到map里，然后通过key获取,但是策略类得用注解加载到spring容器之中
	public EnquiryContext(EnquiryStrategy enquiryStrategy) {
		this.enquiryStrategy = enquiryStrategy;
	}

	public String dealEnquiry(String bankName) {
		return this.enquiryStrategy.enquiryPrice(bankName);
	}


	public static void main(String[] args) {
		
		EnquiryContext context = new EnquiryContext(new IBEnquiry());
		System.out.println(context.dealEnquiry("投行名称"));
		
		context = new EnquiryContext(new PBEnquiry());
		System.out.println(context.dealEnquiry("私行名称"));
	}
	
}
