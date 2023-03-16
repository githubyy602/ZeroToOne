package com.yangy.hahauser.controller;

import bean.ResultBean;
import com.yangy.hahauser.bean.DTO.LoginInfoReqDto;
import com.yangy.hahauser.bean.DTO.LoginInfoRespDto;
import com.yangy.hahauser.bean.PO.User;
import com.yangy.hahauser.mapper.UserMapper;
import enums.ResponseCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.TokenUtil;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

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
	private UserMapper userMapper;
	
	@PostMapping(value = "/online")
	public ResultBean online(@RequestBody @Valid LoginInfoReqDto loginInfoReqDto){
		User loginUser = userMapper.selectUserByLoginInfo(new User(loginInfoReqDto.getLoginName(),loginInfoReqDto.getPassword()));
		if(Objects.nonNull(loginUser)){
			LoginInfoRespDto infoDto = new LoginInfoRespDto();
			infoDto.setUserId(loginUser.getId());
			
			//赋值token
			try {
				infoDto.setAccessToken(TokenUtil.buildToken(loginUser.getId()));
				return ResultBean.returnResult(ResponseCodeEnum.SUCCESS,infoDto);
			} catch (Exception e) {
				log.error("{}",e.getMessage(),e);
				return ResultBean.returnResult(ResponseCodeEnum.TOKEN_FAILED);
			}
		}
		return ResultBean.returnResult(ResponseCodeEnum.FAIL);
	}

}



