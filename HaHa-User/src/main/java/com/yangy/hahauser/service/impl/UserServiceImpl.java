package com.yangy.hahauser.service.impl;

import com.yangy.hahauser.bean.PO.User;
import com.yangy.hahauser.mapper.UserMapper;
import com.yangy.hahauser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Yangy
 * @Date: 2021/5/14 12:26
 * @Description
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public User queryUser(User user) {
		return userMapper.selectById(user.getId());
	}

	@Override
	public int createUser(User user) {
		return userMapper.insertSelective(user);
	}
}
