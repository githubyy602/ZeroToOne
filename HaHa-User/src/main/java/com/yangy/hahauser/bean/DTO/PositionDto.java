package com.yangy.hahauser.bean.DTO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: Yangy
 * @Date: 2023/5/25 16:18
 * @Description
 */
@Data
public class PositionDto {
	
	//1=datafeed;2=manual
	private Integer type;
	
	private BigDecimal quantity;
	
	private String bankName;
}
