package com.yangy.hahauser.controller;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.hahauser.bean.DTO.OrderDto;
import com.yangy.hahauser.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
	
}
