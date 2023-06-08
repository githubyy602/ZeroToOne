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
	
	//业务错误码
	USER_NOT_EXIST_ERROR(2100,"用户不存在"),;
	
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
