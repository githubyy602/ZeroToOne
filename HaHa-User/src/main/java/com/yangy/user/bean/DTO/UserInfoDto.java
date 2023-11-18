package com.yangy.user.bean.DTO;

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
	
	private Integer userId;
	
	@NotEmpty(message = "用户名不允许为空")
    private String userName;

    private String sex;

    private String email;

    private String phone;

    @NotEmpty(message = "登录名不允许为空")
    private String loginName;
    
    @NotEmpty(message = "登录密码不允许为空")
    private String loginPassword;

    private String imgUrl;

    private Date createTime;
	
    //用户职称
    private String userTitle;
    //头像文件id
    private Integer imgId;
}
