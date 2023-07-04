package com.yangy.send.constants;

import lombok.Getter;

/**
 * @Author: Yangy
 * @Date: 2023/6/13 17:01
 * @Description
 */
public enum MailServerTypeEnum {
	OUT_LOOK_MAIL(1,"微软outlook邮箱","outLookConfigStrategy"),
	G_MAIL(2,"谷歌gmail邮箱","gmailConfigStrategy"),
	QQ_MAIL(3,"qq邮箱","qqConfigStrategy");

	MailServerTypeEnum(int type, String desc, String strategy) {
		this.type = type;
		this.desc = desc;
		this.strategy = strategy;
	}
	
	@Getter
	private int type;
	
	@Getter
	private String desc;
	
	@Getter
	private String strategy;
	
}
