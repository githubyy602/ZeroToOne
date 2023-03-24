package com.yangy.common.hystrix;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.feign.SendFeignClient;
import org.springframework.stereotype.Component;

/**
 * @Author: Yangy
 * @Date: 2023/3/24 10:11
 * @Description
 */
@Component
public class SendFallBack implements SendFeignClient {
	@Override
	public ResultBean sendSMS() {
		//todo 可以在对应的方法内写入使用统一降级策略，或是指定的降级策略处理
		return ResultBean.returnResult(ResponseCodeEnum.SUCCESS,"发送服务暂时断开连接，请稍后。。。");
	}
}
