package com.yangy.common.handler;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.constant.CommonConstant;
import com.yangy.common.enums.ResponseCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * @Author: Yangy
 * @Date: 2023/6/8 15:05
 * @Description
 */
@Slf4j
@ControllerAdvice(basePackages = CommonConstant.BASE_PACKAGE)
public class GlobalExceptionHandler {
	
	@ResponseBody
	@ExceptionHandler(RuntimeException.class)
	public ResultBean runtimeExceptionHandler(RuntimeException ex){
		log.error("GlobalExceptionHandler catch exception:{}",ex.getMessage(),ex);
		return ResultBean.returnResult(ResponseCodeEnum.RUNTIME_ERROR,ex.getMessage());
	}
	
	//未声明异常捕获
	@ResponseBody
	@ExceptionHandler({UndeclaredThrowableException.class})
	public ResultBean undeclaredThrowableExceptionHandler(UndeclaredThrowableException ex){
		log.error("GlobalExceptionHandler catch exception:{}",ex.getMessage(),ex);
		return ResultBean.returnResult(ResponseCodeEnum.UNKOWN_ERROR);
	}
	
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public ResultBean defaultExceptionHandler(Exception ex){
		log.error("GlobalExceptionHandler catch exception:{}",ex.getMessage(),ex);
		return ResultBean.returnResult(ResponseCodeEnum.UNKOWN_ERROR,ex.getMessage());
	}
}
