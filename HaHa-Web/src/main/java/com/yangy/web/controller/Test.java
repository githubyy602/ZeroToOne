package com.yangy.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Yangy
 * @Date: 2023/7/4 11:27
 * @Description
 */
public class Test {
	
	//随机生成100个，10位字符串。字符由a-z,A-Z,0-9随机组成。并将生成的100个字符串存入容器中
	//后通过键盘输入一个字符串，需返回输入的字符串是否在上面的随机字符串中的结果内容
	public static List<String> list() throws UnsupportedEncodingException {
		List<String> ramdomList = new ArrayList<>();
		
		String source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		for (int i = 0; i < 100; i++) {
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < 10; j++) {
				sb.append(source.charAt((int)(Math.random()*62)));
			}
			ramdomList.add(sb.toString());
		}
		
		return ramdomList;
	}
	
	public volatile static int k = 0;
	
	public static final Object lock = new Object();
	
	static void addNum(){
		synchronized (lock){
			k++;
		}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
		
		//通过线程池，让几个线程 按顺序 执行，分别输出不同的内容
		//如线程1输出 t1执行，线程2输出 t2执行 。。。。
		ExecutorService executorService = new ThreadPoolExecutor(10,12,1000,TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>());
//		
//		for (int i = 0; i < 10; i++) {
//		((ThreadPoolExecutor) executorService).execute(new Thread(()->{
//			System.out.println("t1执行。。");
//		}));
//		((ThreadPoolExecutor) executorService).contextStopping();
//		
//		((ThreadPoolExecutor) executorService).execute(new Thread(()->{
//			System.out.println("t2执行。。");
//		}));
//		
//		((ThreadPoolExecutor) executorService).contextStopping();
//		
//		((ThreadPoolExecutor) executorService).execute(new Thread(()->{
//			System.out.println("t3执行。。 \n");
//		}));
//		
//		((ThreadPoolExecutor) executorService).contextStopping();
//		
//		}
		
//		TestThread t = new TestThread(k);
		for (int j = 0; j < 100; j++) {
			executorService.execute(()->{
				addNum();
			});
		}
		
		
//		for (int i = 0; i < 2000; i++) {
//			System.out.println("");
//		}
		
		System.out.println(k);
		
		System.exit(1);
	}
	
	
	
}
