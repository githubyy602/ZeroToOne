package com.yangy.hahauser.bean.PO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class User {
    private Integer id;

    private String userName;

    private String sex;

    private String email;

    private String phone;

    private String loginName;

    private String loginPassword;

    private String imgUrl;

    private Date createTime;

    private Date updateTime;

    public User(String loginName, String loginPassword) {
        this.loginName = loginName;
        this.loginPassword = loginPassword;
    }
}