package com.systemk.spyder.Controller.Api.Web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Entity.Main.MailLog;
import com.systemk.spyder.Repository.Main.MailLogRepository;
import com.systemk.spyder.Service.MailService;

@RestController
public class MailController {

	@Autowired
	private MailLogRepository mailLogRepository;
	
	@Autowired
	MailService mailService;
	
	@RequestMapping(value="/mail/mailSendAction", method=RequestMethod.POST)
	public ResponseEntity<MailLog> mailSendAction(@RequestBody MailLog mailLog) throws Exception{
		
		System.out.println("=========mailLog========"+mailLog.toString());
		
		boolean mailSn = false;
		mailSn = mailService.sendMail(mailLog.getMailSend(), mailLog.getMailTitle(),mailLog.getMailContents(), "1");
		
		mailLog.setSendDate(new Date());
		if(mailSn == true) {
//			mailLogRepository.save(mailLog);
			return new ResponseEntity<MailLog>(mailLog, HttpStatus.OK);
		} else {
			return new ResponseEntity<MailLog>(mailLog, HttpStatus.NOT_FOUND);
		}
		
		
	}
	
}
