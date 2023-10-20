package com.yangy.hahauser.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.common.bean.ResultBean;
import com.yangy.common.enums.ResponseCodeEnum;
import com.yangy.hahauser.bean.PO.User;
import com.yangy.hahauser.mapper.UserMapper;
import com.yangy.hahauser.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

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
	public ResultBean createUser(User user) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("login_name",user.getLoginName());
		List<User> users = baseMapper.selectList(queryWrapper);
		if(CollectionUtil.isNotEmpty(users)){
			return ResultBean.returnResult(ResponseCodeEnum.USER_ALREADY_EXIST_ERROR);
		}
		
		return ResultBean.success(baseMapper.insert(user));
	}
}
