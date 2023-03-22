package com.yangy.common.feign;

import com.yangy.common.bean.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: Yangy
 * @Date: 2023/3/22 14:13
 * @Description
 */
@FeignClient(name = "${sys.serviceName.send}",path = "${sys.serviceContext.send}")
public interface SendFeignClient {
	
	@RequestMapping(value = "/sms/sendMessage",method = RequestMethod.POST)
	public ResultBean sendSMS();
	
}
