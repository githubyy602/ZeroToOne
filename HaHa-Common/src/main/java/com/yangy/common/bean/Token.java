package com.yangy.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yangy
 * @Date: 2023/3/14 15:36
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
	
	private Integer userId;
	//token有效时间戳
	private long time;
	
}
