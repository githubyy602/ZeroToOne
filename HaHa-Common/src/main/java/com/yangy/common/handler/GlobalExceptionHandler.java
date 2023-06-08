package com.yangy.common.handler;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.constant.CommonConstant;
import com.yangy.common.enums.ResponseCodeEnum;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Yangy
 * @Date: 2023/6/8 15:05
 * @Description
 */
@ControllerAdvice(value = CommonConstant.BASE_PACKAGE)
@ConditionalOnClass(ResponseBody.class)
public class GlobalExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
	public ResultBean runtimeExceptionHandler(RuntimeException ex){
		return ResultBean.returnResult(ResponseCodeEnum.RUNTIME_ERROR);
	}
	
}
