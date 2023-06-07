package com.yangy.send.service.impl;

import com.yangy.send.bean.InboxMailInfo;
import com.yangy.send.service.MailFileDownloadService;
import org.springframework.stereotype.Service;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * @Author: Yangy
 * @Date: 2023/6/7 11:50
 * @Description
 */
@Service
public class MailFileDownloadServiceImpl implements MailFileDownloadService {
	
	private static final String DOWNLOAD_PATH = "D:/QMDownload/mailDownload/";
	
	/**
	* @Author Yangy
	* @Description 自动下载邮件附件
	* @Date 12:27 2023/6/7
	* @Param [inboxMailInfo]
	* @return void
	**/
	@Override
	public void downloadFile(InboxMailInfo inboxMailInfo) {
		try {
			System.setProperty("https.protocols", "TLSv1.2");

			// 邮箱登录信息
            String username = "yy339452**@outlook.com";
            String password = "**";

            // 邮箱服务器信息
            String host = inboxMailInfo.getHost();
            int port = inboxMailInfo.getPort();

            // 目标文件夹路径
            String downloadFolder = DOWNLOAD_PATH;

            // 创建会话
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imaps");
            props.setProperty("mail.imaps.host", host);
            props.setProperty("mail.imaps.port", Integer.toString(port));
            props.setProperty("mail.imaps.connectiontimeout", "5000");
            props.setProperty("mail.imaps.timeout", "5000");
            props.setProperty("mail.imaps.ssl.protocols", "TLSv1.2");
            Session session = Session.getInstance(props);
            Store store = session.getStore("imaps");
            store.connect(username, password);

            // 打开收件箱
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // 获取收件箱中的所有邮件
            Message[] messages = inbox.getMessages();

            // 遍历邮件并下载附件
            for (Message message : messages) {
                if (message.isMimeType("multipart/mixed")) {
                    MimeMultipart multipart = (MimeMultipart) message.getContent();
                    int count = multipart.getCount();
                    for (int i = 0; i < count; i++) {
                        MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
                        if (bodyPart.getDisposition() != null && bodyPart.getDisposition().equalsIgnoreCase("attachment")) {
                            String rawFilename = bodyPart.getFileName();
                            String decodedFilename = MimeUtility.decodeText(rawFilename);
                            File file = new File(downloadFolder + decodedFilename);
                            try (FileOutputStream fos = new FileOutputStream(file)) {
                                bodyPart.saveFile(file);
                                System.out.println("Downloaded attachment: " + decodedFilename);
                            }
                        }
                    }
                }
            }

            // 关闭连接
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public static void main(String[] args) {
		MailFileDownloadServiceImpl downloadService = new MailFileDownloadServiceImpl();
		downloadService.downloadFile(new InboxMailInfo());
	}
}
