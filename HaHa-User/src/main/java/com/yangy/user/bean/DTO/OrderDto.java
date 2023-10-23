package com.yangy.user.bean.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author: Yangy
 * @Date: 2023/5/25 10:43
 * @Description
 */
@Data
public class OrderDto {
	
	@NotNull(message = "订单名称不能为空")
	private String name;
	
	@NotNull(message = "订单金额不能为空")
	private BigDecimal amount;
	
	@NotNull(message = "订单类型不能为空")
	private Integer type;
}
