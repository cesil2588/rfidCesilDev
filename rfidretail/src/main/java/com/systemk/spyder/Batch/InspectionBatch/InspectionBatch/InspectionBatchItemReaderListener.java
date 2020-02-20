package com.systemk.spyder.Batch.InspectionBatch.InspectionBatch;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.BatchTrigger;
import com.systemk.spyder.Repository.Main.BatchTriggerRepository;
import com.systemk.spyder.Service.UserNotiService;

@Component
public class InspectionBatchItemReaderListener implements ItemReadListener<BatchTrigger>{
	
	private static final Logger log = LoggerFactory.getLogger(InspectionBatchItemReaderListener.class);
	 
	private BatchTriggerRepository batchTriggerRepository;
	
	private UserNotiService userNotiService;
	
	public InspectionBatchItemReaderListener(BatchTriggerRepository batchTriggerRepository,
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

		// 배치 트리거 시작 업데이트
		trigger.setStatus("START");
		trigger.setStartDate(new Date());
		batchTriggerRepository.save(trigger);
					
		// 배치 트리거 사용자 noti
		userNotiService.save("생산 입고 배치 작업이 시작되었습니다.", trigger.getRegUserInfo(), "inspectionBatch", Long.valueOf(0));
	}

	@Override
	public void onReadError(Exception ex) {
		// TODO Auto-generated method stub
		
	}
}
