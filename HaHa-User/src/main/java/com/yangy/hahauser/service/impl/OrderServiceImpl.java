package com.yangy.hahauser.service.impl;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.design.factory.OrderFactory;
import com.yangy.hahauser.bean.DTO.OrderDto;
import com.yangy.hahauser.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: Yangy
 * @Date: 2023/5/25 10:44
 * @Description
 */
@Service
public class OrderServiceImpl implements OrderService {
	
	@Resource
	private Map<String,OrderFactory> factoryMap;

	@Override
	public ResultBean createOrder(OrderDto order) {
		
		OrderFactory orderFactory;
		if(1 == order.getType()){
			orderFactory = factoryMap.get("StockOrderFactory");
		}else{
			orderFactory = factoryMap.get("SpOrderFactory");
		}
		
		if(null == orderFactory){
			return null;
		}
		
		String result = orderFactory.getOrder().createOrder();
		
		return ResultBean.success(result);
	}

	@Override
	public ResultBean updateOrder(OrderDto order) {
		return null;
	}

	@Override
	public ResultBean orderDetail(OrderDto order) {
		return null;
	}
}
