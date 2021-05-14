package com.yangy.hahauser.bean;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;

    private String userName;

    private String sex;

    private String email;

    private String phone;

    private String loginName;

    private String loginPassword;

    private String salt;

    private Date createTime;

    private Date updateTime;
   
}