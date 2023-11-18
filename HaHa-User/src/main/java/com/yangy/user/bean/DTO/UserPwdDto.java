package com.yangy.user.bean.DTO;

import lombok.Data;

/**
 * @Author: Yangy
 * @Date: 2023/11/17 17:26
 * @Description
 */
@Data
public class UserPwdDto {
	
	private Integer userId;
	
	private String pwd;
	
	private String newPwd;
	
}
