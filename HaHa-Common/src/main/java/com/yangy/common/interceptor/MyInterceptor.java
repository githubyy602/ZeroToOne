package com.yangy.common.interceptor;

import com.yangy.common.bean.ResultBean;
import com.yangy.common.constant.CommonConstant;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.exception.CustomException;
import com.yangy.common.util.ResponseUtil;
import com.yangy.common.util.SignUtil;
import com.yangy.common.util.TokenUtil;
import com.yangy.common.wrapper.HttpRequestWrapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
@Slf4j
public class MyInterceptor implements HandlerInterceptor {
	
	private List<String> urlList;
	
	private List<String> signExceptUrlList;

	public MyInterceptor(List<String> urlList,List<String> signList) {
		this.urlList = urlList;
		this.signExceptUrlList=signList;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		//跨域请求会首先发一个option请求，直接返回正常状态并通过拦截器
        if (request.getMethod().equals(CommonConstant.METHOD_TYPE_OPTIONS)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        
        if(request.getMethod().equals(CommonConstant.METHOD_TYPE_GET)){
        	//GET类型放行
        	return true;
		}
		
		String feignFlag = request.getHeader(CommonConstant.HEADER_CHECK_REQ);
		if(StringUtils.equals(CommonConstant.HEADER_FROM_FEIGN,feignFlag)){
			return true;
		}
        
		try {

        	//免token校验放行
			String url = request.getRequestURI();
			if(url.endsWith("/error") || url.contains("/static")){
				return true;
			}
			
			//转换request对象，因为要校验json类型的参数，必须转换后才能获取到入参
			HttpRequestWrapper requestWrapper;
				if (request instanceof HttpRequestWrapper) {
				requestWrapper = (HttpRequestWrapper) request;
			} else {
				requestWrapper = new HttpRequestWrapper(request);
			}
			
			if(!CollectionUtils.isEmpty(urlList) && urlList.stream().filter(u->url.endsWith(u) || url.startsWith(u)).findAny().isPresent()){
				//免登陆
				return true;
			}
			//校验token
			TokenUtil.checkToken(requestWrapper);

			//校验签名
			if(CollectionUtils.isEmpty(signExceptUrlList) || !signExceptUrlList.stream().filter(u->url.endsWith(u)).findAny().isPresent()){
				SignUtil.checkSign(requestWrapper);
			}

			return true;
		} catch (Exception e) {
        	if(e instanceof CustomException){
				ResponseUtil.responseOutWithJson(response, ResultBean.returnResult(((CustomException)e).getCode()));
			}else {
        		log.error("",e);
        		ResponseUtil.responseOutWithJson(response, ResultBean.returnResult(ResponseCodeEnum.RUNTIME_ERROR));
			}
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
