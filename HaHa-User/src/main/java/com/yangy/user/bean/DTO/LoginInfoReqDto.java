package com.yangy.user.bean.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: Yangy
 * @Date: 2023/3/14 17:57
 * @Description
 */
@Data
public class LoginInfoReqDto {
	
	@NotEmpty(message = "登录名不能为空")
	private String loginName;
	
	@NotEmpty(message = "密码不能为空")
	private String password;
}
