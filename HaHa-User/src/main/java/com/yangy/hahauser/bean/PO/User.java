package com.yangy.hahauser.bean.PO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yangy.hahauser.annotation.EncryptEntity;
import com.yangy.hahauser.annotation.EncryptField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "tb_user",autoResultMap = true)
@EncryptEntity
public class User{
	
//	@TableId(value = "id",type = IdType.AUTO)
    private Integer id;
	
//    @TableField(value = "user_name",typeHandler = EncryptTypeHandler.class)
	@EncryptField
    private String userName;

    private String sex;
	
    @EncryptField
    private String email;

    @EncryptField
    private String phone;

    private String loginName;

    private String loginPassword;

    private String imgUrl;

    private Date createTime;

    private Date updateTime;

	public User(Integer id) {
		this.id = id;
	}

	public User(String loginName, String loginPassword) {
        this.loginName = loginName;
        this.loginPassword = loginPassword;
    }
}