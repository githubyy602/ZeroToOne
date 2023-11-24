package com.yangy.user.bean.PO;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * tb_login_log
 * @author 
 */
@Data
public class LoginLog implements Serializable {
    private Integer id;

    /**
     * 登录用户id
     */
    private Integer userId;

    /**
     * 登录时间
     */
    private Date loginTime;
    /**
     * 登录结果：1=成功，2=失败
     */
    private Integer loginResult;

    /**
     * 登录端类型：1=web，2=安卓，3=ios
     */
    private Integer clientType;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 登录系统版本
     */
    private String sysVersion;

    /**
     * 登出时间
     */
    private Date logoutTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}