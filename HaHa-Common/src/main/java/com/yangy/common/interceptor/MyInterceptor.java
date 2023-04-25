package com.yangy.common.interceptor;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.constant.CommonConstant;
import com.yangy.common.exception.CustomException;
import com.yangy.common.util.ResponseUtil;
import com.yangy.common.util.SignUtil;
import com.yangy.common.util.TokenUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: Yangy
 * @Date: 2021/5/13 16:45
 * @Description
 */
@RefreshScope
@Data
@NoArgsConstructor
public class MyInterceptor implements HandlerInterceptor {
	
	private List<String> urlList;

	public MyInterceptor(List<String> urlList) {
		this.urlList = urlList;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		//跨域请求会首先发一个option请求，直接返回正常状态并通过拦截器
        if (request.getMethod().equals(CommonConstant.METHOD_TYPE_OPTIONS)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
		
		try {

        	//免token校验放行，如登录接口
			String url = request.getRequestURI();
			if(CollectionUtils.isEmpty(urlList) || !urlList.stream().filter(u->url.endsWith(u)).findAny().isPresent()){
				String token = request.getHeader(CommonConstant.HEADER_ACCESS_TOKEN);
				//校验token
				TokenUtil.checkToken(token);
			}

			//校验签名
			SignUtil.checkSign(request);

			return true;
		} catch (CustomException e) {
			ResponseUtil.responseOutWithJson(response, ResultBean.returnResult(e.getCode()));
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
