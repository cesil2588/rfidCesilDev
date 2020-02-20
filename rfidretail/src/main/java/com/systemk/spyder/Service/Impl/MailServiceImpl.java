package com.systemk.spyder.Service.Impl;

import java.util.Date;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.systemk.spyder.Entity.Main.MailLog;
import com.systemk.spyder.Repository.Main.MailLogRepository;
import com.systemk.spyder.Service.MailService;

@PropertySource("classpath:application.properties")
@Service
public class MailServiceImpl implements MailService{
		
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String fromUser;
	
	@Value("${mail.send.flag}")
	private String mailSendFlag;
	
	@Autowired
	private MailLogRepository mailLogRepository;
	
	public boolean sendMail(String sendUser, String subject, String content, String type) {
		
		boolean success = false;
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(sendUser));
				
				// 보낸메일 변경시 아래 소스 수정
				mimeMessage.setFrom(new InternetAddress(fromUser));
				
				mimeMessage.setSubject(subject);
				mimeMessage.setText(content,"utf-8","html");
			}
		};
		
		if(mailSendFlag.equals("Y")) {
			
			try {
				javaMailSender.send(preparator);
				success = true;
				mailLogRepository.save(new MailLog(fromUser, sendUser, subject, content, new Date(), null, type, "N", "1", null));
			} catch (MailException me) {
				//LOGGER.error("MailException",me);
				me.printStackTrace();
				mailLogRepository.save(new MailLog(fromUser, sendUser, subject, content, new Date(), null, type, "N", "2", me.getMessage()));
			}
		}
		
		return success;
	}
	
}
