package com.yangy.common.exception;

import com.yangy.common.enums.ResponseCodeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Yangy
 * @Date: 2023/3/13 16:16
 * @Description
 */
@Data
@NoArgsConstructor
public class CustomException extends Exception{
	
	private int code;
	
	private String message;

	public CustomException(int code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}
	
	public static CustomException custom(int code,String message){
		return new CustomException(code,message);
	}

	public static CustomException custom(int code) {
		return new CustomException(code,ResponseCodeEnum.getVal(code));
	}
}
