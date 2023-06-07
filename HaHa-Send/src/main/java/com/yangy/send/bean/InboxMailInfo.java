package com.yangy.send.bean;

import lombok.Data;

/**
 * @Author: Yangy
 * @Date: 2023/6/7 11:46
 * @Description
 */
@Data
public class InboxMailInfo extends MailConfig {
	
	private String host = "outlook.office365.com";
	
	private int port = 993;
	
}
