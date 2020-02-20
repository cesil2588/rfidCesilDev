package com.systemk.spyder.Batch.TextUploadBatch.BartagTextUploadBatch;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.BatchTrigger;
import com.systemk.spyder.Repository.Main.BatchTriggerRepository;
import com.systemk.spyder.Service.UserNotiService;
import com.systemk.spyder.Util.StringUtil;

@Component
public class BartagTextUploadItemWriterListener implements ItemWriteListener<BatchTrigger>{
	
	private static final Logger log = LoggerFactory.getLogger(BartagTextUploadItemWriterListener.class);
	
	private BatchTriggerRepository batchTriggerRepository;
	
	private UserNotiService userNotiService;
	
	public BartagTextUploadItemWriterListener(BatchTriggerRepository batchTriggerRepository,
											  UserNotiService userNotiService){
		this.batchTriggerRepository = batchTriggerRepository;
		this.userNotiService = userNotiService;
	}

	@Override
	public void beforeWrite(List<? extends BatchTrigger> items) {
		// TODO Auto-generated method stub
		
	} 

	@Override
	public void afterWrite(List<? extends BatchTrigger> items) {
		
		for(BatchTrigger trigger: items){
			
			// 배치 트리거 결과 업데이트
			trigger.setEndDate(new Date());
			trigger.setStat("2");
			trigger.setStatus("COMPLETE");
			
			// 배치 트리거 사용자 noti
			userNotiService.save("배치 업로드 작업이 완료되었습니다.", trigger.getRegUserInfo(), "textUploadBatch", Long.valueOf(0));
		}
		
		batchTriggerRepository.save(items);
	}

	@Override
	public void onWriteError(Exception exception, List<? extends BatchTrigger> items) {
		for(BatchTrigger trigger: items){
			
			// 배치 트리거 결과 업데이트
			trigger.setStatus("ERROR");
			trigger.setErrorMessage(StringUtil.getPrintStackTrace(exception));
			
			// 배치 트리거 사용자 noti
			userNotiService.save("배치 업로드 작업이 실패하였습니다.", trigger.getRegUserInfo(), "textUploadBatch", Long.valueOf(0));
		}
		
		batchTriggerRepository.save(items);
	}
}
