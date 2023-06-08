package com.yangy.hahauser.bean.DTO;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Yangy
 * @Date: 2023/6/8 16:18
 * @Description
 */
@Data
public class UserInfoDto {
	
    private String userName;

    private String sex;

    private String email;

    private String phone;

    private String loginName;

    private String imgUrl;

    private Date createTime;
	
}
