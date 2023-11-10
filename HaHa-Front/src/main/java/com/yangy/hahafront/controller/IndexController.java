package com.yangy.hahafront.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Yangy
 * @Date: 2023/11/3 16:57
 * @Description
 */
@Controller
public class IndexController {
	
	@RequestMapping(value = "/")
	public String defaultPage(){
		return "/index";
	}
	
	@RequestMapping(value = "/index")
	public String index(){
		return "/index";
	}
	
	@RequestMapping(value = "/loginPage")
	public String login(){
		return "/signin";
	}
	
}
