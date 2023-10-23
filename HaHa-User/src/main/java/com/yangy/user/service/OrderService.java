package com.yangy.user.service;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.design.strategy.EnquiryDto;
import com.yangy.user.bean.DTO.OrderDto;

/**
 * @Author: Yangy
 * @Date: 2023/5/25 10:42
 * @Description
 */
public interface OrderService {
	
	ResultBean createOrder(OrderDto order);
	
	ResultBean updateOrder(OrderDto order);
	
	ResultBean orderDetail(OrderDto order);
	
	ResultBean dealEnquiry(EnquiryDto enquiryDto);
}
