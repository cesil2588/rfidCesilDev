package com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleConfirmBatch;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.Repository.Main.StorageScheduleLogRepository;

@Component
public class StorageScheduleLogConfirmItemWriterListener implements ItemWriteListener<StorageScheduleLog>{
	
	private static final Logger log = LoggerFactory.getLogger(StorageScheduleLogConfirmItemWriterListener.class);
	
	private StorageScheduleLogRepository storageScheduleLogRepository;
	
	public StorageScheduleLogConfirmItemWriterListener(StorageScheduleLogRepository storageScheduleLogRepository){
		this.storageScheduleLogRepository = storageScheduleLogRepository;
	}

	@Override
	public void beforeWrite(List<? extends StorageScheduleLog> items) {
		// TODO Auto-generated method stub
		
	} 

	@Override
	public void afterWrite(List<? extends StorageScheduleLog> items) {
		
		for(StorageScheduleLog item: items){
			
			// 배치 트리거 결과 업데이트
			item.setBatchYn("Y");
			item.setBatchDate(new Date());
		}
		
		storageScheduleLogRepository.save(items);
	}

	@Override
	public void onWriteError(Exception exception, List<? extends StorageScheduleLog> items) {
		// TODO Auto-generated method stub
		
	}
}
