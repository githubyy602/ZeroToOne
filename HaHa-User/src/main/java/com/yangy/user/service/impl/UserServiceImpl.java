package com.yangy.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.common.bean.ResultBean;
import com.yangy.common.constant.CommonConstant;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.common.feign.SendFeignClient;
import com.yangy.common.util.IpUtils;
import com.yangy.common.util.TokenUtil;
import com.yangy.user.bean.DTO.LoginInfoReqDto;
import com.yangy.user.bean.DTO.LoginInfoRespDto;
import com.yangy.user.bean.PO.LoginLog;
import com.yangy.user.bean.PO.User;
import com.yangy.user.mapper.LoginLogMapper;
import com.yangy.user.mapper.UserMapper;
import com.yangy.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: Yangy
 * @Date: 2021/5/14 12:26
 * @Description
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
	
	@Autowired
	private SendFeignClient sendFeignClient;
	
	@Autowired
	private LoginLogMapper loginLogMapper;
	@Autowired
	private HttpServletRequest request;
	//todo 后续可通过版本管理表来实现动态版本更新
	public static final String VERSION = "1.0.0";
	
	@Override
	public User queryUser(User user) {
		return baseMapper.selectById(user.getId());
	}

	@Override
	public ResultBean createUser(User user) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("login_name",user.getLoginName());
		List<User> users = baseMapper.selectList(queryWrapper);
		if(CollectionUtil.isNotEmpty(users)){
			return ResultBean.returnResult(ResponseCodeEnum.USER_ALREADY_EXIST_ERROR);
		}
		
		user.setSex(StringUtils.isNotEmpty(user.getSex()) ? user.getSex() : "1");
		return ResultBean.success(baseMapper.insert(user));
	}

	@Override
	public ResultBean userLogin(LoginInfoReqDto loginInfoReqDto) {
		LoginLog loginLog = new LoginLog();
		try {
			User loginUser = baseMapper.selectUserByLoginInfo(new User(loginInfoReqDto.getLoginName()));
			if(Objects.nonNull(loginUser)){
				if(!StringUtils.equals(loginUser.getLoginPassword(),loginInfoReqDto.getPassword())){
					return ResultBean.returnResult(ResponseCodeEnum.USER_PASSWORD_ERROR);
				}
				
				assembleLog(loginUser.getId(),loginLog);
				
				//todo 目前假设这里有账户+验证码的登录形式，需要通过feign获取验证码
				ResultBean resultBean = sendFeignClient.sendSMS();
				log.info("获取到的验证码是：{}",resultBean.getData());
	//			if(!ResultBean.successResp(resultBean) || Objects.isNull(resultBean.getData())){
	//				return ResultBean.returnResult(ResponseCodeEnum.PARAM_ERROR);
	//			}
				
				LoginInfoRespDto infoDto = new LoginInfoRespDto();
				infoDto.setUserId(loginUser.getId());
				
				//赋值token
				infoDto.setAccessToken(TokenUtil.buildToken(loginUser.getId()));
				loginLog.setLoginResult(CommonConstant.SUCCESS);
				return ResultBean.returnResult(ResponseCodeEnum.SUCCESS,infoDto);
				
			}else {
				loginLog.setLoginResult(CommonConstant.FAIL);
				return ResultBean.returnResult(ResponseCodeEnum.USER_NOT_EXIST_ERROR);
			}
		}catch (Exception e){
			log.error("",e);
			loginLog.setLoginResult(CommonConstant.FAIL);
		}finally {
			//记录登录日志
			loginLogMapper.insertSelective(loginLog);
		}
		
		return ResultBean.returnResult(ResponseCodeEnum.USER_LOGIN_FAILURE_ERROR);
	}
	
	private void assembleLog(Integer userId,LoginLog loginLog){
		loginLog.setUserId(userId);
		loginLog.setClientType(CommonConstant.CLIENT_TYPE_WEB);
		loginLog.setLoginTime(new Date());
		loginLog.setSysVersion(VERSION);
		loginLog.setLoginIp(IpUtils.getClientIpAddr(request));
	}

	@Override
	public ResultBean userLogout(Integer userId) {
		LoginLog loginLog= loginLogMapper.selectLatestLog(userId);
		if(Objects.isNull(loginLog)){
			return ResultBean.returnResult(ResponseCodeEnum.USER_LOGOUT_FAILURE_ERROR);
		}
		
		loginLog.setLogoutTime(new Date());
		loginLog.setUpdateTime(new Date());
		int result = loginLogMapper.updateByPrimaryKeySelective(loginLog);
		if(result >0){
			return ResultBean.success(null);
		}
		
		return ResultBean.returnResult(ResponseCodeEnum.USER_LOGOUT_FAILURE_ERROR);
	}
}
