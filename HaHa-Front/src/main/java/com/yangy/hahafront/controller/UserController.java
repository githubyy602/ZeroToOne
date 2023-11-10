package com.yangy.hahafront.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Yangy
 * @Date: 2023/11/3 16:59
 * @Description
 */
@Controller
@RequestMapping("/view")
public class UserController {
	
	
	@RequestMapping(value = "/login")
	public void login(){
		//todo 通过feign调用user模块登录
	}
	
}
