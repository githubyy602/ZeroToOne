package com.yangy.common.design.strategy;

import com.yangy.common.bean.ResultBean;

import java.util.Objects;

/**
 * @Author: Yangy
 * @Date: 2023/5/24 14:42
 * @Description
 */
public class PBStrategy implements EnquiryStrategy<EnquiryDto,ResultBean>{

	@Override
	public ResultBean enquiryPrice(EnquiryDto enquiry) {
		if(Objects.nonNull(enquiry) && 2 == enquiry.getType()){
			return ResultBean.success("私行今日价格为：98");
		}
		return ResultBean.success("错误类型，不显示价格");
	}
}
