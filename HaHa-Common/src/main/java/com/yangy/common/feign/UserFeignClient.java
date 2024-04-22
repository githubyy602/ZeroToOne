package com.yangy.common.feign;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.hystrix.UserFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: Yangy
 * @Date: 2023/11/24 16:25
 * @Description url = "http://localhost:20011"
 */
@FeignClient(name = "${sys.serviceName.user}",path = "${sys.serviceContext.user}",fallback = UserFallBack.class)
public interface UserFeignClient {
	
	@RequestMapping(value = "/getLatestUsers",method = RequestMethod.POST)
	public ResultBean getLatestUsers();
	
}
