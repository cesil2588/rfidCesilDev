package com.systemk.spyder.Service;

public interface MailService {
		
	public boolean sendMail(String to, String subject, String content, String type);
	
}
