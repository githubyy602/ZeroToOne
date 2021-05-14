package com.yangy.hahauser.controller;

import Enum.ResponseCodeEnum;
import bean.ResultBean;
import com.yangy.hahauser.bean.User;
import com.yangy.hahauser.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yangy
 * @Date: 2021/5/13 10:28
 * @Description
 */

@RequestMapping("/login")
@RestController
public class LoginController {
	
	@Autowired
	private UserMapper userMapper;
	
	@PostMapping(value = "/online")
	public ResultBean online(){
		User user = 
		return ResultBean.returnResultNoData(ResponseCodeEnum.SUCCESS);
	}

}



