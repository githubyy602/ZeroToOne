package Enum;

/**
 * @Author: Yangy
 * @Date: 2021/5/13 15:08
 * @Description
 */
public enum ResponseCodeEnum {
	SUCCESS(1000,"成功"),
	FAIL(2000,"失败");

	private int code;
	private String desc;

	ResponseCodeEnum() {
	}

	ResponseCodeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
