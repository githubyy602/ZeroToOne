package com.yangy.common.design.strategy;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: Yangy
 * @Date: 2023/5/25 17:05
 * @Description
 */
@Data
public class EnquiryDto {
	
	private Integer type;
	
	private BigDecimal price;
	
}
