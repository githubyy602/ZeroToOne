package com.yangy.send.strategy;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @Author: Yangy
 * @Date: 2023/6/13 16:08
 * @Description
 */
public abstract class AbstractStrategyConfig {
	
	protected String host ;
	protected int port ;
	protected String protocol ;

	public AbstractStrategyConfig(String host, int port, String protocol) {
		this.host = host;
		this.port = port;
		this.protocol = protocol;
	}

	public JavaMailSenderImpl initConfig(){
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(host);
		sender.setPort(port);
		sender.setProtocol(protocol);
		sender.setDefaultEncoding("UTF-8");
		
		Properties p = new Properties();
		p.setProperty("mail.smtp.timeout", String.valueOf(60000));//超时单位：毫秒
		p.setProperty("mail.smtp.auth", "true");
		p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.setProperty("mail.smtp.starttls.enable", "true");
		sender.setJavaMailProperties(p);
		
		return sender;
	}
}
