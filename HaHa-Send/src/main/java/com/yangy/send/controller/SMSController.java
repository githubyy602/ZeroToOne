package com.yangy.send.controller;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.enums.ResponseCodeEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yangy
 * @Date: 2023/3/22 12:30
 * @Description
 */
@RequestMapping(value = "/sms")
@RestController
public class SMSController {
	
	@PostMapping(value = "/sendMessage")
	public ResultBean sendSMS(){
		//todo 实现短信发送功能
		return ResultBean.returnResult(ResponseCodeEnum.SUCCESS,"123456");
	}
	
}
