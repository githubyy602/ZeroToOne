package com.yangy.gateway.encrypt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: Yangy
 * @Date: 2023/3/23 14:12
 * @Description
 */
@Component
public class EncryptHelper {
	
	@Value("${test}")
	private String name;
	
	private void encrypt(){
		//todo 加密处理
		//自出暂做nacos配置读取
		System.out.println("test = "+name);
	}
	
}
