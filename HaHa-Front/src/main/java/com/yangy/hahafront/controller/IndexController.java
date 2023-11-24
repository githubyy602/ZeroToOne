package com.yangy.hahafront.controller;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Yangy
 * @Date: 2023/11/3 16:57
 * @Description
 */
@Controller
public class IndexController {
	
	@Autowired
	private UserFeignClient userFeignClient;
	
	@RequestMapping(value = "/")
	public String defaultPage(Model model){
		ResultBean resultBean = userFeignClient.getLatestUsers();
		model.addAttribute("accessUserList",resultBean.getData());
		return "/index";
	}
	
	@RequestMapping(value = "/index")
	public String index(Model model){
		ResultBean resultBean = userFeignClient.getLatestUsers();
		model.addAttribute("accessUserList",resultBean.getData());
		
		return "/index";
	}
	
	@RequestMapping(value = "/index.html")
	public String indexHtml(Model model){
		ResultBean resultBean = userFeignClient.getLatestUsers();
		model.addAttribute("accessUserList",resultBean.getData());
		
		return "/index";
	}
	
	@RequestMapping(value = "/loginPage")
	public String login(){
		return "/signin";
	}

}
