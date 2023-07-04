package com.yangy.send.strategy;

import org.springframework.stereotype.Component;

/**
 * @Author: Yangy
 * @Date: 2023/6/13 16:53
 * @Description QQ邮件发送，需要在发件账号的qq邮箱设置中开启smtp功能并获取到授权码作为发件密码使用
 */
@Component("qqConfigStrategy")
public class QQConfigStrategy extends AbstractStrategyConfig {

	private final static String HOST = "smtp.qq.com";
	private final static int PORT = 587;
	private final static String PROTOCOL = "smtp";

	public QQConfigStrategy() {
		super(HOST, PORT, PROTOCOL);
	}
}
