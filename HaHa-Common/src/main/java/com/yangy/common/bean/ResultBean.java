package com.yangy.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.yangy.common.enums.ResponseCodeEnum;
import lombok.NoArgsConstructor;

/**
 * @Author: Yangy
 * @Date: 2021/5/13 10:29
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultBean {
	
	private int code;
	private String message;
	private Object data;

	public static ResultBean returnResult(ResponseCodeEnum responseCodeEnum){
		return returnResult(responseCodeEnum,null);
	}
	
	public static ResultBean returnResult(ResponseCodeEnum responseCodeEnum,Object data){
		return new ResultBean(responseCodeEnum.getCode(),responseCodeEnum.getDesc(),data);
	}
}
