package com.systemk.spyder.Batch.ErpSyncBatch.BartagSerialBatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.UserEmailInfo;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.BartagMasterRepository;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import com.systemk.spyder.Service.BartagLogService;
import com.systemk.spyder.Service.MailService;
import com.systemk.spyder.Service.UserNotiService;

@Component
public class BartagSerialItemWriterListener implements ItemWriteListener<BartagMaster>{
	
	private static final Logger log = LoggerFactory.getLogger(BartagSerialItemWriterListener.class);
	
	private BartagMasterRepository bartagMasterRepository;
	
	private MailService mailService;
	
	private UserInfoRepository userInfoRepository;
	
	private UserNotiService userNotiService;
	
	private BartagLogService bartagLogService;
	
	public BartagSerialItemWriterListener(BartagMasterRepository bartagMasterRepository,
										  MailService mailService,
										  UserInfoRepository userInfoRepository,
										  UserNotiService userNotiService,
										  BartagLogService bartagLogService){
		this.bartagMasterRepository = bartagMasterRepository;
		this.mailService = mailService;
		this.userInfoRepository = userInfoRepository;
		this.userNotiService = userNotiService;
		this.bartagLogService = bartagLogService;
	}

	@Override
	public void beforeWrite(List<? extends BartagMaster> items) {
		// TODO Auto-generated method stub
		
	} 

	@Override
	public void afterWrite(List<? extends BartagMaster> items) {
		
		List<BartagMaster> bartagList = new ArrayList<BartagMaster>();
		
		for(BartagMaster bartagMaster: items){
			
			Date startDate = new Date();
			
			if(bartagMaster.getProductionCompanyInfo() == null){
				continue;
			}
			
			bartagMaster.setGenerateSeqYn("Y");
			bartagMaster.setStat("2");
			
			// 태그 상태별 수량 추가
			bartagMaster.setStat1Amount(bartagMaster.getAmount());
			bartagMaster.setStat2Amount(Long.valueOf(0));
			bartagMaster.setStat3Amount(Long.valueOf(0));
			bartagMaster.setStat4Amount(Long.valueOf(0));
			bartagMaster.setStat5Amount(Long.valueOf(0));
			bartagMaster.setStat6Amount(Long.valueOf(0));
			bartagMaster.setStat7Amount(Long.valueOf(0));
			bartagMaster.setTotalAmount(bartagMaster.getAmount());
			
			bartagList.add(bartagMaster);
			
			// 태그 상태별 수량 로그 추가
			bartagLogService.init(bartagMaster, startDate);
		}
		
		bartagMasterRepository.save(bartagList);
		
		if(items.size() > 0){
			
			// SOSIT 바택발행 메일
			UserInfo emailInfo = userInfoRepository.findTop1ByCompanyInfoRoleInfoRole("publish");

			if (emailInfo != null) {
				for (UserEmailInfo email : emailInfo.getUserEmailInfo()) {
					mailService.sendMail(email.getEmail(), "바택발행완료", "바택발행이 완료되었습니다.<br /><a href='http://spyderrfid.co.kr/'>U의류관리시스템</a>에 접속하여 바택발행을 확인해주세요.", "2");
				}

				// 알림 추가
				userNotiService.save("바택발행이 완료되었습니다.", emailInfo, "bartag", Long.valueOf(0));
			}

			// 에일리언 바택발행 메일
			UserInfo publishAdminInfo = userInfoRepository.findTop1ByCompanyInfoRoleInfoRole("publishAdmin");

			if (publishAdminInfo != null) {
				for (UserEmailInfo email : publishAdminInfo.getUserEmailInfo()) {
					mailService.sendMail(email.getEmail(), "바택발행완료", "바택발행이 완료되었습니다.<br /><a href='http://spyderrfid.co.kr/'>U의류관리시스템</a>에 접속하여 바택발행을 확인해주세요.", "2");
				}

				// 알림 추가
				userNotiService.save("바택발행이 완료되었습니다.", publishAdminInfo, "bartag", Long.valueOf(0));
			}
		}
	}

	@Override
	public void onWriteError(Exception exception, List<? extends BartagMaster> items) {
		// TODO Auto-generated method stub
		
	}
}
