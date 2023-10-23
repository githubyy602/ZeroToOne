package com.yangy.user.controller;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.design.strategy.EnquiryDto;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.user.bean.DTO.OrderDto;
import com.yangy.user.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Yangy
 * @Date: 2023/5/25 11:00
 * @Description
 */
@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping(value = "/createOrder")
	public ResultBean createOrder(@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult){
		
		if(bindingResult.hasErrors()){
			return ResultBean.returnResult(ResponseCodeEnum.PARAM_ERROR);
		}
		
		return orderService.createOrder(orderDto);
	}
	
	@PostMapping(value = "/getEnquiryPrice")
	public ResultBean getEnquiryPrice(@RequestBody EnquiryDto enquiryDto){
		return orderService.dealEnquiry(enquiryDto);
	}

	public static void main(String[] args) {
		//无界通配符
		List<?> list = new ArrayList<>();
		list.add(null);
		list.clear();
		list.remove(0);
		list.size();
		
		//上界通配符
		List<? extends Number> list1 = new ArrayList<>();
		list1.add(null);
		
		List<? super Integer> list2 = new ArrayList<>();
		list2.add(null);
	}
	
}
