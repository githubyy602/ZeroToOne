package com.yangy.common.design.singleton;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Yangy
 * @Date: 2023/5/24 11:31
 * @Description
 */
public class SingletonHungryMode {
	
	//饿汉模式，有没有都new
	private static final SingletonHungryMode SINGLETON_MODE = new SingletonHungryMode();
	
	private int num;
	
	public static SingletonHungryMode getInstance(){
		return SINGLETON_MODE;
	}
	
	private int add(){
		this.num ++;
		System.out.println("["+Thread.currentThread().getId()+"]当前对用对象hash值为："+this.hashCode()+"当前数值："+this.num);
		return this.num;
	}

	public static void main(String[] args) {

		ThreadPoolExecutor executor = new ThreadPoolExecutor(4,10,1000,TimeUnit.MILLISECONDS,
				new LinkedBlockingDeque<>());
		
		//饿汉单例测试
		for (int i = 0; i < 20; i++) {
			executor.execute(()->SingletonHungryMode.getInstance().add());
		}
		
		//测试结果：可见多个线程，使用的是同一个对象
		//[16]当前对用对象hash值为：950754561当前数值：3
		//[14]当前对用对象hash值为：950754561当前数值：3
		//[15]当前对用对象hash值为：950754561当前数值：3
		//[17]当前对用对象hash值为：950754561当前数值：4
		//[17]当前对用对象hash值为：950754561当前数值：5
		//[16]当前对用对象hash值为：950754561当前数值：6
		//[17]当前对用对象hash值为：950754561当前数值：9
		//[15]当前对用对象hash值为：950754561当前数值：8
		//[14]当前对用对象hash值为：950754561当前数值：7
		//[15]当前对用对象hash值为：950754561当前数值：12
		//[17]当前对用对象hash值为：950754561当前数值：11
		//[16]当前对用对象hash值为：950754561当前数值：10
		//[17]当前对用对象hash值为：950754561当前数值：15
		//[15]当前对用对象hash值为：950754561当前数值：14
		//[14]当前对用对象hash值为：950754561当前数值：13
		//[15]当前对用对象hash值为：950754561当前数值：18
		//[17]当前对用对象hash值为：950754561当前数值：17
		//[16]当前对用对象hash值为：950754561当前数值：16
		//[15]当前对用对象hash值为：950754561当前数值：20
		//[14]当前对用对象hash值为：950754561当前数值：19
	
		executor.shutdown();	
	}
	
}
