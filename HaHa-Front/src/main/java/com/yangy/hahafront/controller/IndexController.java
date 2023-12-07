package com.yangy.hahafront.controller;

import com.github.pagehelper.PageInfo;
import com.yangy.common.bean.PageQuery;
import com.yangy.common.bean.ResultBean;
import com.yangy.common.bean.feign.ArticleVo;
import com.yangy.common.constant.UrlConstant;
import com.yangy.common.feign.BusinessFeignClient;
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
	
	@Autowired
	private BusinessFeignClient businessFeignClient;
	
	private void getIndexData(Model model){
		ResultBean resultBean = userFeignClient.getLatestUsers();
		model.addAttribute("accessUserList",resultBean.getData());
		model.addAttribute("fileDomain",UrlConstant.FILE_SERVICE_URL);

		PageQuery pageQuery = new PageQuery();
		ResultBean<PageInfo<ArticleVo>> articleResult = businessFeignClient.getArticleList(pageQuery);
		if(ResultBean.successResp(articleResult)){
			PageInfo<ArticleVo> articleVoPageInfo = articleResult.getData();
			model.addAttribute("articleList",articleVoPageInfo.getList()); 
		}
	}
	
	@RequestMapping(value = {"/","/index","/index.html"})
	public String defaultPage(Model model){
		this.getIndexData(model);
		return "/index";
	}
	
	@RequestMapping(value = "/loginPage")
	public String login(){
		return "/signin";
	}

}
