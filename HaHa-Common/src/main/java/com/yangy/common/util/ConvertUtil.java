package com.yangy.common.util;

import org.springframework.beans.BeanUtils;

import java.util.Objects;

/**
 * @Author: Yangy
 * @Date: 2023/6/8 16:15
 * @Description
 */
public class ConvertUtil<T,R> {
	
	public static <T,R> R convert(T t,R r){
		if(Objects.isNull(t) || Objects.isNull(r)){
			return null;
		}

		BeanUtils.copyProperties(t,r);
		
		return r;
	}
	
	
}
