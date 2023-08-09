package com.yangy.hahauser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.hahauser.bean.PO.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User selectUserByLoginInfo(User user);
    
    User selectById(Integer id);
}