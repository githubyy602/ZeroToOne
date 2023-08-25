package com.yangy.hahauser.bean.PO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yangy.hahauser.annotation.EncryptEntity;
import com.yangy.hahauser.annotation.EncryptField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "tb_user",autoResultMap = true)
@EncryptEntity
public class User implements Serializable {
	
//	@TableId(value = "id",type = IdType.AUTO)
    private Integer id;
	
//    @TableField(typeHandler = EncryptTypeHandler.class)
	@EncryptField
    private String userName;
	
    private String sex;
    
//	@TableField(typeHandler = EncryptTypeHandler.class)
    @EncryptField
    private String email;
	
//	@TableField(typeHandler = EncryptTypeHandler.class)
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