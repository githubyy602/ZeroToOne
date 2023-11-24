package com.yangy.user.service;

import com.yangy.common.bean.ResultBean;
import com.yangy.user.bean.DTO.LoginInfoReqDto;
import com.yangy.user.bean.PO.User;

/**
 * @Author: Yangy
 * @Date: 2021/5/14 12:26
 * @Description
 */
public interface UserService {
	User queryUser(User user);
	
	ResultBean createUser(User user);
	
	ResultBean userLogin(LoginInfoReqDto loginInfoReqDto);
	
	ResultBean userLogout(Integer userId);
}
