package com.yangy.hahauser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.hahauser.bean.PO.User;
import com.yangy.hahauser.mapper.UserMapper;
import com.yangy.hahauser.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author: Yangy
 * @Date: 2021/5/14 12:26
 * @Description
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

	@Override
	public User queryUser(User user) {
		return baseMapper.selectById(user.getId());
	}

	@Override
	public int createUser(User user) {
		return baseMapper.insertSelective(user);
	}
}
