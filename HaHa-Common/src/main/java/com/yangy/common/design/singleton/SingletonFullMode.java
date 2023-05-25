package com.yangy.common.design.singleton;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Yangy
 * @Date: 2023/5/24 12:03
 * @Description
 */
public class SingletonFullMode {
	
	//饱汉模式，没有时才new
	private static volatile  SingletonFullMode SINGLETON_MODE ;
	
	private int num;
	
	/**
	* @Author Yangy
	* @Description 
	 * 注意： 使用双重检查微进行初始化的实例必须使volatile关键字修饰
	 * 原因： instance = new Single() 可 以拆解为3步：
	 * 1、分配内存
	 * 2、初始化对象
	 * 3、指向刚分配的地址，若发生重排序，假设A线程执行了1和3，还没有执行2，B线程来创判断NULL, 
	 * B线程就会点接返0还没初始化的instance了。volatile可以避免重排序
	* @Date 12:07 2023/5/25
	* @Param []
	* @return com.yangy.common.design.singleton.SingletonFullMode
	**/
	public static SingletonFullMode getInstance(){
		if(SINGLETON_MODE == null){
			synchronized (SingletonFullMode.class){
				if(SINGLETON_MODE == null){
					try {
						Thread.sleep(1000); //延迟获取
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					SINGLETON_MODE = new SingletonFullMode();
				}
			}
		}
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
			executor.execute(()->SingletonFullMode.getInstance().add());
		}
		
		//测试结果：
		//synchronized内不判空时
		//[14]当前对用对象hash值为：300930647当前数值：1
		//[14]当前对用对象hash值为：300930647当前数值：2
		//[14]当前对用对象hash值为：300930647当前数值：3
		//[14]当前对用对象hash值为：300930647当前数值：4
		//[14]当前对用对象hash值为：300930647当前数值：5
		//[14]当前对用对象hash值为：300930647当前数值：6
		//[14]当前对用对象hash值为：300930647当前数值：7
		//[14]当前对用对象hash值为：300930647当前数值：8
		//[14]当前对用对象hash值为：300930647当前数值：9
		//[14]当前对用对象hash值为：300930647当前数值：10
		//[14]当前对用对象hash值为：300930647当前数值：11
		//[14]当前对用对象hash值为：300930647当前数值：12
		//[14]当前对用对象hash值为：300930647当前数值：13
		//[14]当前对用对象hash值为：300930647当前数值：14
		//[14]当前对用对象hash值为：300930647当前数值：15
		//[14]当前对用对象hash值为：300930647当前数值：16
		//[14]当前对用对象hash值为：300930647当前数值：17
		//[17]当前对用对象hash值为：2078463862当前数值：1
		//[16]当前对用对象hash值为：950754561当前数值：1
		//[15]当前对用对象hash值为：2122674849当前数值：1
		
		//synchronized内判空
		//[14]当前对用对象hash值为：1564823808当前数值：4
		//[16]当前对用对象hash值为：1564823808当前数值：4
		//[14]当前对用对象hash值为：1564823808当前数值：5
		//[16]当前对用对象hash值为：1564823808当前数值：6
		//[17]当前对用对象hash值为：1564823808当前数值：4
		//[15]当前对用对象hash值为：1564823808当前数值：4
		//[17]当前对用对象hash值为：1564823808当前数值：9
		//[16]当前对用对象hash值为：1564823808当前数值：8
		//[14]当前对用对象hash值为：1564823808当前数值：7
		//[16]当前对用对象hash值为：1564823808当前数值：12
		//[17]当前对用对象hash值为：1564823808当前数值：11
		//[15]当前对用对象hash值为：1564823808当前数值：10
		//[17]当前对用对象hash值为：1564823808当前数值：15
		//[16]当前对用对象hash值为：1564823808当前数值：14
		//[14]当前对用对象hash值为：1564823808当前数值：13
		//[16]当前对用对象hash值为：1564823808当前数值：18
		//[17]当前对用对象hash值为：1564823808当前数值：17
		//[15]当前对用对象hash值为：1564823808当前数值：16
		//[16]当前对用对象hash值为：1564823808当前数值：20
		//[14]当前对用对象hash值为：1564823808当前数值：19
		
		executor.shutdown();
	}
	
}
