package bean;

import lombok.Data;
import Enum.ResponseCodeEnum;

/**
 * @Author: Yangy
 * @Date: 2021/5/13 10:29
 * @Description
 */
@Data
public class ResultBean {
	
	private int code;
	private String message;
	private Object data;

	public ResultBean() {
	}

	public ResultBean(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public static ResultBean returnResultNoData(ResponseCodeEnum responseCodeEnum){
		return returnResultWithData(responseCodeEnum,null);
	}
	
	public static ResultBean returnResultWithData(ResponseCodeEnum responseCodeEnum,Object data){
		return new ResultBean(responseCodeEnum.getCode(),responseCodeEnum.getDesc(),data);
	}
}
