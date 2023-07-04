package com.yangy.send.helper;

import com.alibaba.fastjson.JSON;
import com.yangy.send.bean.SendMailInfo;
import com.yangy.send.strategy.StrategyContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author: Yangy
 * @Date: 2023/6/16 11:30
 * @Description
 */
@Slf4j
public class MailHelper {
	
	private final JavaMailSender mailSender;

    private String graphApiVersion;

    private String graphClientId;

    private String graphClientSecret;

    private String graphUserEmail;
    
    public MailHelper(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

//    public void sendEmailByGraph(String toAddress, String subject, String body) throws Exception {
//        // Create the MIME message using JavaMail
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//        messageHelper.setTo(toAddress);
//        messageHelper.setSubject(subject);
//        messageHelper.setText(body, true);
//
//        // Get the access token for Microsoft Graph
//		
//        // 使用 Azure Identity 库创建 TokenCredential 对象
//		ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
//				.clientId(graphClientId)
//				.clientSecret(graphClientSecret)
//				.tenantId(tenantId)
//				.build();
//		
//		// 创建 TokenCredentialAuthProvider
//		TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(clientSecretCredential, scopes) {
//			@Override
//			public void authenticateRequest(IHttpRequest request) {
//				// 在身份验证请求时进行自定义操作（可选）
//				super.authenticateRequest(request);
//			}
//		};
//        
//        String accessToken = authProvider.getAccessToken().get();
//
//        // Use Microsoft Graph API to send the email
//        GraphServiceClient<Request> graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).buildClient();
//        UserSendMailRequestBuilder requestBuilder = graphClient.users(graphUserEmail).sendMail(mimeMessage);
//        requestBuilder.header("Authorization", "Bearer " + accessToken);
//        requestBuilder.version(graphApiVersion);
//
//        try {
//            requestBuilder.post();
//        } catch (Exception e) {
//            throw new Exception("Failed to send email using Microsoft Graph: " + e.getMessage());
//        }
//    }
	
	@Resource
	private StrategyContext strategyContext;

	public boolean sendMailByDefault(SendMailInfo info) throws MessagingException {
		Optional<SendMailInfo> optional = Optional.of(info);
		if(optional.filter(o->Objects.isNull(o) 
				|| StringUtils.isEmpty(o.getSender()) 
				|| StringUtils.isEmpty(o.getPassword())
				|| StringUtils.isEmpty(o.getRecipient()) 
				|| StringUtils.isEmpty(o.getMailContent())).isPresent()){
			log.warn("MailHelper.sendMail the mail info has error!!!\n",JSON.toJSONString(info));
			return false;
		}
		
		JavaMailSenderImpl javaMailSender = strategyContext.getStrategy(info.getType()).initConfig();
		MimeMessage mimeMessage = this.constructMailMessage(info,javaMailSender);

		try {
			javaMailSender.send(mimeMessage);
		} catch (MailException e) {
			log.error("Mail server : {} ,port :{},from : {} ,userName :{},pwd :{}",javaMailSender.getHost(),javaMailSender.getPort(),
					info.getFrom(),
					javaMailSender.getUsername(),javaMailSender.getPassword());
			throw e;
		}
		return true;
	}

	public MimeMessage constructMailMessage(SendMailInfo mailInfo,JavaMailSenderImpl javaMailSender) throws MessagingException, MailException {
		//设置发送邮件的账号信息
		javaMailSender.setUsername(mailInfo.getSender());
		javaMailSender.setPassword(mailInfo.getPassword());
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		// 设置utf-8或GBK编码，否则邮件会有乱码
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		if(StringUtils.isNotEmpty(mailInfo.getFrom())){
			messageHelper.setFrom(mailInfo.getFrom());
		}else{
			messageHelper.setFrom(mailInfo.getSender());
		}
		messageHelper.setSubject(StringUtils.isNotEmpty(mailInfo.getSubject()) ? mailInfo.getSubject() : "暂无主题");
		
		//收件人解析
		String recipient = mailInfo.getRecipient();
		if(recipient.contains(";")){
			//多收件人
			String [] recAry = recipient.split(";");
			messageHelper.setTo(recAry);
		}else{
			messageHelper.setTo(recipient);
		}
		
		//抄送
		String cc = mailInfo.getCc();
		if(StringUtils.isNotEmpty(cc)){
			if(cc.contains(";")){
				//多收件人
				String [] ccAry = cc.split(";");
				messageHelper.setCc(ccAry);
			}else{
				messageHelper.setCc(cc);
			}
		}
		
		//密送
		String bcc = mailInfo.getBcc();
		if(StringUtils.isNotEmpty(bcc)){
			if(bcc.contains(";")){
				//多收件人
				String [] bccAry = bcc.split(";");
				messageHelper.setBcc(bccAry);
			}else{
				messageHelper.setBcc(bcc);
			}
		}
		
		//是否使用html格式内容
		boolean ifHtml = mailInfo.isIfHtml();
		if(ifHtml){
			messageHelper.setText(mailInfo.getMailContent(),ifHtml);
		}else{
			messageHelper.setText(mailInfo.getMailContent());
		}
		
		//设置附件
		MultipartFile[] multipartFiles = mailInfo.getMultipartFiles();
		if(ArrayUtils.isNotEmpty(multipartFiles)){
			for (MultipartFile multipartFile : multipartFiles) {
                messageHelper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
            }
		}
		
		return mimeMessage;
	}
	
}
