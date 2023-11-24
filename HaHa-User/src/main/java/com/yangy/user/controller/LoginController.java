package com.yangy.user.controller;

import com.yangy.common.bean.ReqBaseBean;
import com.yangy.common.bean.ResultBean;
import com.yangy.user.bean.DTO.LoginInfoReqDto;
import com.yangy.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author: Yangy
 * @Date: 2021/5/13 10:28
 * @Description
 */
@Slf4j
@RequestMapping("/login")
@RestController
public class LoginController {
	
	@Resource
	private UserService userService;
	
	
	@PostMapping(value = "/online")
	public ResultBean online(@RequestBody @Valid LoginInfoReqDto loginInfoReqDto){
		return userService.userLogin(loginInfoReqDto);
	}
	
	@PostMapping(value = "/out")
	public ResultBean out(@RequestBody ReqBaseBean reqBaseBean){
		return userService.userLogout(reqBaseBean.getUserId());
	}
	
}



