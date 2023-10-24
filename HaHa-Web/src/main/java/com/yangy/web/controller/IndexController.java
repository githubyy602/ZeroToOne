package com.yangy.web.controller;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.bean.feign.Articles;
import com.yangy.common.feign.BusinessFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @Author: Yangy
 * @Date: 2023/5/11 14:28
 * @Description
 */
@Controller
public class IndexController {
	
	@Autowired
	private BusinessFeignClient businessFeignClient;
	
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
		return "/login";
	}
	
	@RequestMapping(value = "/detail/{id}")
	public String detail1(@PathVariable Integer id, Model model){
		ResultBean<Articles> articlesResultBean = businessFeignClient.getArticleDetail(id);
		model.addAttribute("detail",articlesResultBean.getData());
		return "/articleDetail";
	}

}
