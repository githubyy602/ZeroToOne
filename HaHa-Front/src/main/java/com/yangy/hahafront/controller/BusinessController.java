package com.yangy.hahafront.controller;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.bean.feign.ArticleVo;
import com.yangy.common.feign.BusinessFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

/**
 * @Author: Yangy
 * @Date: 2023/12/13 11:18
 * @Description
 */
@Controller
public class BusinessController {
	
	@Autowired
	private BusinessFeignClient businessFeignClient;
	
	@RequestMapping(value = {"/edit","/edit.html","/edit.html?id={articleId}"})
	public String goEditPage(@RequestParam(value = "id",required = false) Integer articleId, Model model){
		if(Objects.nonNull(articleId) && articleId >0){
			ResultBean<ArticleVo> resultBean = businessFeignClient.getArticleDetail(articleId);
			if(ResultBean.successResp(resultBean)){
				model.addAttribute("article",resultBean.getData());
			}
		}
		
		return "articleEdit";
	}
}
