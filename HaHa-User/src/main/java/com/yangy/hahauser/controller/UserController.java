package com.yangy.hahauser.controller;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.feign.SendFeignClient;
import com.yangy.common.util.SignUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Yangy
 * @Date: 2023/3/16 15:21
 * @Description
 */
@RestController
@RequestMapping
public class UserController {
	
	@Resource
	private SendFeignClient sendFeignClient;
	
	@PostMapping(value = "/getUserInfo")
	public ResultBean getUserInfo(){
		return ResultBean.returnResult(ResponseCodeEnum.SUCCESS);
	}
	
	//测试feign调用
	@PostMapping(value = "/getMessage")
	public ResultBean getMessage(){
		return sendFeignClient.sendSMS();
	}
	
	@PostMapping(value = "/getSign")
	public ResultBean testSign(){
		return ResultBean.success(SignUtil.getSign("{\"loginName\":\"123\",\"password\":\"123456\"}"));
	}
}
