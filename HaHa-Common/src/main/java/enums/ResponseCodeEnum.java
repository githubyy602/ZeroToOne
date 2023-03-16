package enums;

/**
 * @Author: Yangy
 * @Date: 2021/5/13 15:08
 * @Description
 */
public enum ResponseCodeEnum {
	SUCCESS(1000,"成功"),
	FAIL(2000,"失败"),
	TOKEN_FAILED(2001,"获取Token失败"),
	TOKEN_ERROR(2002,"Token错误"),
	TOKEN_EXPIRE(2003,"Token失效"),
	SIGN_ERROR(2004,"签名错误"),
	PARAM_ERROR(2005,"签名错误"),;

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
	
	public static String getVal(int code){
		for (ResponseCodeEnum item:ResponseCodeEnum.values()){
			if(item.code == code){
				return item.desc;
			}
		}
		
		return "";
	}
}
