package com.systemk.spyder.Batch.TextUploadBatch.BartagTextUploadBatch;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.BatchTrigger;
import com.systemk.spyder.Repository.Main.BatchTriggerRepository;
import com.systemk.spyder.Service.UserNotiService;

@Component
public class BartagTextUploadItemReaderListener implements ItemReadListener<BatchTrigger>{
	
	private static final Logger log = LoggerFactory.getLogger(BartagTextUploadItemReaderListener.class);
	
	private BatchTriggerRepository batchTriggerRepository;
	
	private UserNotiService userNotiService;
	
	public BartagTextUploadItemReaderListener(BatchTriggerRepository batchTriggerRepository,
											  UserNotiService userNotiService){
		this.batchTriggerRepository = batchTriggerRepository;
		this.userNotiService = userNotiService;
	}

	@Override
	public void beforeRead() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void afterRead(BatchTrigger trigger) {
		
		// 배치 트리거 결과 업데이트
		trigger.setStatus("START");
		trigger.setStartDate(new Date());
		batchTriggerRepository.save(trigger);
					
		// 배치 트리거 사용자 noti
		userNotiService.save("배치 업로드 작업이 시작 되었습니다.", trigger.getRegUserInfo(), "textUploadBatch", Long.valueOf(0));
	}


	@Override
	public void onReadError(Exception ex) {
		// TODO Auto-generated method stub
		
	}
}
