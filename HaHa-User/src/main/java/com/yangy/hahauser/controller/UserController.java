package com.yangy.hahauser.controller;

import com.alibaba.fastjson.JSON;
import com.yangy.common.bean.ResultBean;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.feign.SendFeignClient;
import com.yangy.common.util.SignUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.TreeMap;

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
		Map<String,Object> param = new TreeMap<>();
		param.put("loginName","admin");
		param.put("password","123456");
		return ResultBean.success(SignUtil.getSign(JSON.toJSONString(param)));
	}
}
