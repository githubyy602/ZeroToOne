package com.yangy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @Author: Yangy
 * @Date: 2023/5/11 14:28
 * @Description
 */
@Controller
public class IndexController {

	@RequestMapping("/toIndex")
	public String index(){
		return "/index";
	}

}
