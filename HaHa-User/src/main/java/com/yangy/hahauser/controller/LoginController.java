package com.yangy.hahauser.controller;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.feign.SendFeignClient;
import com.yangy.common.util.TokenUtil;
import com.yangy.hahauser.bean.DTO.LoginInfoReqDto;
import com.yangy.hahauser.bean.DTO.LoginInfoRespDto;
import com.yangy.hahauser.bean.PO.User;
import com.yangy.hahauser.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
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
	
	@Value("${testContent}")
	private String testContent;
	
	@Value("${token.except.url}")
	private List<String> urlList;
	
	@PostMapping(value = "/online")
	public ResultBean online(@RequestBody @Valid LoginInfoReqDto loginInfoReqDto){
		User loginUser = userMapper.selectUserByLoginInfo(new User(loginInfoReqDto.getLoginName(),loginInfoReqDto.getPassword()));
		if(Objects.nonNull(loginUser)){
			//todo 目前假设这里有账户+验证码的登录形式，需要通过feign获取验证码
//			ResultBean resultBean = sendFeignClient.sendSMS();
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
		}
		return ResultBean.returnResult(ResponseCodeEnum.FAIL);
	}

}



