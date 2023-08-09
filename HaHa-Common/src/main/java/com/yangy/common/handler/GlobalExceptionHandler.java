package com.yangy.common.handler;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.constant.CommonConstant;
import com.yangy.common.enums.ResponseCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * @Author: Yangy
 * @Date: 2023/6/8 15:05
 * @Description
 */
@ControllerAdvice(value = CommonConstant.BASE_PACKAGE)
//@ConditionalOnClass({ResponseBody.class,ServletException.class,SQLException.class})
public class GlobalExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
	public ResultBean runtimeExceptionHandler(RuntimeException ex){
		return ResultBean.returnResult(ResponseCodeEnum.RUNTIME_ERROR);
	}
	
	//未声明异常捕获
	@ExceptionHandler({UndeclaredThrowableException.class})
	public ResultBean undeclaredThrowableExceptionHandler(UndeclaredThrowableException ex){
		return ResultBean.returnResult(ResponseCodeEnum.UNKOWN_ERROR);
	}
	
	@ExceptionHandler
	public ResultBean defaultExceptionHandler(Exception ex){
		return ResultBean.returnResult(ResponseCodeEnum.UNKOWN_ERROR,ex.getMessage());
	}
}
