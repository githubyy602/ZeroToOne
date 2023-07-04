package com.yangy.send.strategy;

/**
 * @Author: Yangy
 * @Date: 2023/6/13 16:13
 * @Description
 */
public class GmailConfigStrategy extends AbstractStrategyConfig {

	private final static String HOST = "smtp.gmail.com";
	private final static int PORT = 587;
	private final static String PROTOCOL = "smtp";

	public GmailConfigStrategy() {
		super(HOST, PORT, PROTOCOL);
	}
}
