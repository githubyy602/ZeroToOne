package com.yangy.send.service;

import com.yangy.send.bean.InboxMailInfo;

/**
 * @Author: Yangy
 * @Date: 2023/6/7 11:28
 * @Description
 */
public interface MailFileDownloadService {
	
	void downloadFile(InboxMailInfo inboxMailInfo);
	
}
