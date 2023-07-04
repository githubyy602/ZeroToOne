package com.yangy.send.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yangy.send.constants.MailServerTypeEnum;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @Author: Yangy
 * @Date: 2023/6/16 11:35
 * @Description
 */
@Data
public class SendMailInfo {
	
	//发送者(必填)
	@NotNull(message = "发件人不能为空")
    private String sender;

    //发件邮箱密码,也可能是授权码(必填)
	@NotNull(message = "密码不能为空")
    private String password;

    //邮件发送源邮箱（可选填，不填则默认为sender）
    private String from;

    //收件人（收件邮箱有多个则以 ; 拼接）(必填)
	@NotNull(message = "收件人不能为空")
    private String recipient;

    //邮件主题（选填）
    private String subject;

    //邮件内容(必填)
	@NotNull(message = "邮件内容不能为空")
    private String mailContent;

    //抄送（抄送邮箱有多个则以 ; 拼接）（选填）
    private String cc;

    //密送（密送邮箱有多个则以 ; 拼接）（选填）
    private String bcc;

    //是否是html格式的邮件内容（不传则默认为非html格式）（选填）
    private boolean ifHtml;

    //邮件附件（选填）
    @JsonIgnore
    private MultipartFile[] multipartFiles;
    
    private MailServerTypeEnum type;
}
