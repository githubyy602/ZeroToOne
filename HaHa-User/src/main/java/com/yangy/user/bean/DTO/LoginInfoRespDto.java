package com.yangy.user.bean.DTO;

import lombok.Data;

/**
 * @Author: Yangy
 * @Date: 2023/3/13 10:34
 * @Description
 */
@Data
public class LoginInfoRespDto {
	
	private Integer userId;
	
	private String accessToken;
	
}
