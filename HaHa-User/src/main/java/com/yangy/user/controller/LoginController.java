package com.yangy.user.controller;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.feign.SendFeignClient;
import com.yangy.common.util.TokenUtil;
import com.yangy.user.bean.DTO.LoginInfoReqDto;
import com.yangy.user.bean.DTO.LoginInfoRespDto;
import com.yangy.user.bean.PO.User;
import com.yangy.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@Resource
	private SendFeignClient sendFeignClient;
	
	@Value("${testContent:}")
	private String testContent;
	
	@PostMapping(value = "/online")
	public ResultBean online(@RequestBody @Valid LoginInfoReqDto loginInfoReqDto){
		User loginUser = userMapper.selectUserByLoginInfo(new User(loginInfoReqDto.getLoginName()));
		if(Objects.nonNull(loginUser)){
			if(!StringUtils.equals(loginUser.getLoginPassword(),loginInfoReqDto.getPassword())){
				return ResultBean.returnResult(ResponseCodeEnum.USER_PASSWORD_ERROR);
			}
			
			//todo 目前假设这里有账户+验证码的登录形式，需要通过feign获取验证码
			ResultBean resultBean = sendFeignClient.sendSMS();
			log.info("获取到的验证码是：{}",resultBean.getData());
//			if(!ResultBean.successResp(resultBean) || Objects.isNull(resultBean.getData())){
//				return ResultBean.returnResult(ResponseCodeEnum.PARAM_ERROR);
//			}
			
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
		}else {

			return ResultBean.returnResult(ResponseCodeEnum.USER_NOT_EXIST_ERROR);
		}
	}

}



