package com.yangy.hahauser.service;

import com.yangy.common.bean.ResultBean;
import com.yangy.hahauser.bean.DTO.OrderDto;

/**
 * @Author: Yangy
 * @Date: 2023/5/25 10:42
 * @Description
 */
public interface OrderService {
	
	ResultBean createOrder(OrderDto order);
	
	ResultBean updateOrder(OrderDto order);
	
	ResultBean orderDetail(OrderDto order);
}
