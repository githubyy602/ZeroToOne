package com.yangy.hahaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @Author: Yangy
 * @Date: 2023/5/11 14:28
 * @Description
 */
@Controller
public class IndexController {
	
	@RequestMapping(value = "/")
	public String defaultPage(){
		return "/index";
	}
	
	@RequestMapping("/testIndex")
	public String testIndex(){
		return "index-test";
	}
	
	@RequestMapping(value = "/index")
	public String index(){
		return "/index";
	}
	
	@RequestMapping(value = "/login")
	public String login(){
		return "/login-new";
	}
	
	@RequestMapping(value = "/detail1")
	public String detail1(){
		return "/movie-single";
	}

}
