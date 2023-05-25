package com.yangy.common.design.strategy;

import com.yangy.common.bean.ResultBean;

/**
 * @Author: Yangy
 * @Date: 2023/5/24 14:50
 * @Description
 */
public class EnquiryContext{

	private EnquiryStrategy enquiryStrategy;

	public ResultBean dealEnquiry(EnquiryDto enquiryDto) {
		if(enquiryDto.getType() == 1){
			this.enquiryStrategy = new IBStrategy();
		}else {
			this.enquiryStrategy = new PBStrategy();
		}
		return (ResultBean)enquiryStrategy.enquiryPrice(enquiryDto);
	}
	
}
