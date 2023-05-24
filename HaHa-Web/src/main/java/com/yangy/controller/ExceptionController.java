package com.yangy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Yangy
 * @Date: 2023/5/11 14:33
 * @Description
 */
@Controller
public class ExceptionController {
	
	@RequestMapping("/error")
	public String error(){
		return "tips";
	}
	
}
