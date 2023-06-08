package com.yangy.common.bean;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author: Yangy
 * @Date: 2023/6/8 15:02
 * @Description
 */
@Data
public class ReqBaseBean {
	
	@NotNull(message = "用户id不能为空")
	private Integer userId;
	
	@NotEmpty(message = "用户Token不能为空")
	private String token;
	
}
