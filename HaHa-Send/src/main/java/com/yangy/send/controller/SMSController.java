package com.yangy.send.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.yangy.common.bean.ResultBean;
import com.yangy.common.enums.ResponseCodeEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yangy
 * @Date: 2023/3/22 12:30
 * @Description
 */
@RequestMapping(value = "/sms")
@RestController
public class SMSController {
	
	
	@PostMapping(value = "/sendMessage")
	@HystrixCommand(fallbackMethod = "failResult",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),  //是否开启断路器，默认true
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "100"),   //时间窗口时间内最大并发请求次数，默认20次
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",value = "2000"),   //时间窗口时长,默认10s
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "5000"),  //熔断后恢复间隔时间范围，默认5s
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "0"),//失败率达到多少后跳闸，默认50
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")//响应超时时间，默认1000毫秒
    })
	public ResultBean sendSMS(){
		//todo 实现短信发送功能
		return ResultBean.returnResult(ResponseCodeEnum.SUCCESS,"123456");
	}
	
	//测试自定义的注解熔断方式
	@HystrixCommand(fallbackMethod = "failResult",commandProperties = 
			{@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
			@HystrixProperty(name = "fallback.enabled", value = "true")})
	@PostMapping(value = "/testHystrix")
	public ResultBean testHystrix() throws InterruptedException {
		Thread.sleep(2000);
		return ResultBean.returnResult(ResponseCodeEnum.SUCCESS,"send服务正常。。。");
	}
	
	private ResultBean failResult(){
		return ResultBean.returnResult(ResponseCodeEnum.FAIL,"send服务暂不可用。。。");
	}
	
}
