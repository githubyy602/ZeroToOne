package com.yangy.hahauser.controller;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.enums.ResponseCodeEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yangy
 * @Date: 2023/3/16 15:21
 * @Description
 */
@RestController
@RequestMapping
public class UserController {
	
	@PostMapping(value = "/getUserInfo")
	public ResultBean getUserInfo(){
		return ResultBean.returnResult(ResponseCodeEnum.SUCCESS);
	}
	
}
