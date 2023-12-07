package com.yangy.common.enums;

/**
 * @Author: Yangy
 * @Date: 2021/5/13 15:08
 * @Description
 */
public enum ResponseCodeEnum {
	SUCCESS(1000,"成功"),
	FAIL(2000,"失败"),
	//系统错误码
	TOKEN_FAILED(2001,"获取Token失败"),
	TOKEN_ERROR(2002,"Token错误"),
	TOKEN_EXPIRE(2003,"Token失效"),
	SIGN_ERROR(2004,"签名错误"),
	PARAM_ERROR(2005,"参数错误"),
	RUNTIME_ERROR(2006,"运行错误"),
	UNKOWN_ERROR(2007,"未知错误"),
	VALID_ERROR(2008,"参数校验错误"),
	FEIGN_ERROR(2009,"Feign调用错误"),
	
	//业务错误码
	//21xx 用户相关
	USER_NOT_EXIST_ERROR(2100,"用户不存在"),
	USER_ALREADY_EXIST_ERROR(2101,"用户已存在"),
	USER_PASSWORD_ERROR(2102,"密码错误"),
	USER_LOGIN_FAILURE_ERROR(2103,"登录失败"),
	USER_LOGOUT_FAILURE_ERROR(2104,"退出登录失败"),
	
	//22xx 文件相关
	FILE_SIZE_ERROR(2201,"文件大小超过限制"),
	FILE_TYPE_ERROR(2202,"文件类型暂不支持");
	
	private int code;
	private String desc;

	ResponseCodeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static ResponseCodeEnum getResp(int code){
		for (ResponseCodeEnum item:ResponseCodeEnum.values()){
			if(item.code == code){
				return item;
			}
		}
		
		return null;
	}
	
	public static String getVal(int code){
		for (ResponseCodeEnum item:ResponseCodeEnum.values()){
			if(item.code == code){
				return item.desc;
			}
		}
		
		return "";
	}
}
