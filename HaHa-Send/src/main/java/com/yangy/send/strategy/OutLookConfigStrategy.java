package com.yangy.send.strategy;

/**
 * @Author: Yangy
 * @Date: 2023/6/13 16:13
 * @Description
 */
public class OutLookConfigStrategy extends AbstractStrategyConfig {

	private final static String HOST = "smtp.office365.com";
	private final static int PORT = 587;
	private final static String PROTOCOL = "smtp";

	public OutLookConfigStrategy() {
		super(HOST, PORT, PROTOCOL);
	}
}
