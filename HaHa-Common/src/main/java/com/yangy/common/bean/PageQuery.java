package com.yangy.common.bean;

import lombok.Data;

/**
 * @Author: Yangy
 * @Date: 2023/10/23 11:56
 * @Description
 */
@Data
public class PageQuery {
	
	//页码
	private int pageIndex = 0;
	
	//分页内容条数
	private int pageSize = 20;
	
}
