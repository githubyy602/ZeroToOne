package com.yangy.hahauser.bean.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @Author: Yangy
 * @Date: 2023/6/8 16:18
 * @Description
 */
@Data
public class UserInfoDto {
	
	@NotEmpty(message = "用户名不允许为空")
    private String userName;

	@NotEmpty(message = "性别不允许为空")
    private String sex;

    private String email;

    private String phone;

    @NotEmpty(message = "登录名不允许为空")
    private String loginName;
    
    @NotEmpty(message = "登录密码不允许为空")
    private String loginPassword;

    private String imgUrl;

    private Date createTime;
	
}
