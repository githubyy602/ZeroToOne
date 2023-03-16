package interceptor;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Yangy
 * @Date: 2021/5/13 16:45
 * @Description
 */
@Component
public class MyInterceptor extends CommonInterceptor {
	
	@Override
	public boolean check(HttpRequestWrapper httpRequestWrapper, HttpServletResponse res, Object handler) {
		//do some check action
		return true;
	}
}
