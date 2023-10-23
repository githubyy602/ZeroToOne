package com.yangy.user.controller;

import com.alibaba.fastjson.JSON;
import com.yangy.common.bean.ReqBaseBean;
import com.yangy.common.bean.ResultBean;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.feign.SendFeignClient;
import com.yangy.common.util.ConvertUtil;
import com.yangy.common.util.SignUtil;
import com.yangy.user.bean.DTO.UserInfoDto;
import com.yangy.user.bean.PO.User;
import com.yangy.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.Objects;
import java.util.TreeMap;

/**
 * @Author: Yangy
 * @Date: 2023/3/16 15:21
 * @Description
 */
@RestController
@RequestMapping
public class UserController {
	
	@Resource
	private SendFeignClient sendFeignClient;
	
	@Resource
	private UserService userService;
	
	@PostMapping(value = "/getUserInfo")
	public ResultBean getUserInfo(@RequestBody @Valid ReqBaseBean reqBaseBean, BindingResult result){
		User user = userService.queryUser(new User(reqBaseBean.getUserId()));
		if(Objects.isNull(user)){
			return ResultBean.returnResult(ResponseCodeEnum.USER_NOT_EXIST_ERROR);	
		}

		UserInfoDto userInfoDto = ConvertUtil.convert(user,new UserInfoDto());
		return ResultBean.success(userInfoDto);
	}
	
	@PostMapping(value = "/createUser")
	public ResultBean createUser(@RequestBody @Valid UserInfoDto userInfoDto){
		User user = new User();
		BeanUtils.copyProperties(userInfoDto,user);
		user.setCreateTime(new Date());
//		return ResultBean.success(userMapper.insert(user));
		return userService.createUser(user);
	}
	
	//测试feign调用
	@PostMapping(value = "/getMessage")
	public ResultBean getMessage(){
		return sendFeignClient.sendSMS();
	}
	
	@PostMapping(value = "/getSign")
	public ResultBean testSign(@RequestBody TreeMap<String,Object> param){
		return ResultBean.success(SignUtil.getSign(JSON.toJSONString(param)));
	}
	
}
