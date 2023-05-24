package com.yangy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @Author: Yangy
 * @Date: 2023/5/11 14:28
 * @Description
 */
@Controller
@RequestMapping
public class IndexController {

	@GetMapping("/toIndex")
	public String index(){
		return "/index";
	}

}
