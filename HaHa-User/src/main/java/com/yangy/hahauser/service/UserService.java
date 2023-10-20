package com.yangy.hahauser.service;

import com.yangy.common.bean.ResultBean;
import com.yangy.hahauser.bean.PO.User;

/**
 * @Author: Yangy
 * @Date: 2021/5/14 12:26
 * @Description
 */
public interface UserService {
	User queryUser(User user);
	
	ResultBean createUser(User user);
}
