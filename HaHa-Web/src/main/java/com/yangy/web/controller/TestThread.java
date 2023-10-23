package com.yangy.web.controller;

import lombok.Data;

/**
 * @Author: Yangy
 * @Date: 2023/7/5 10:03
 * @Description
 */
@Data
public class TestThread implements Runnable{
	private int i;
		
	public static final Object b = new Object();

	public TestThread(int i) {
		this.i = i;
	}

	@Override
	public void run() {
		synchronized (b){
			i++;
		}
	}
}
