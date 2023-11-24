package com.yangy.common.hystrix;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.feign.UserFeignClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Author: Yangy
 * @Date: 2023/11/24 16:26
 * @Description
 */
@Component
public class UserFallBack implements UserFeignClient {
	
	@Override
	public ResultBean getLatestUsers() {
		return ResultBean.returnResult(ResponseCodeEnum.SUCCESS,new ArrayList<>());
	}
}
