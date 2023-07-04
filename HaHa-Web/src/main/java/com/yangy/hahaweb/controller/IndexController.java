package com.yangy.hahaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @Author: Yangy
 * @Date: 2023/5/11 14:28
 * @Description
 */
@Controller
@RequestMapping
public class IndexController {
	
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String defaultPage(){
		return "/index";
	}
	
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public String index(){
		return "/index";
	}
	
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public String login(){
		return "/login";
	}

}
