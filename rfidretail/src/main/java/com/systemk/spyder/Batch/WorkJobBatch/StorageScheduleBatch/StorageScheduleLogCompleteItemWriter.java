package com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleBatch;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.Service.DistributionStorageLogService;
import com.systemk.spyder.Service.StorageScheduleLogService;

public class StorageScheduleLogCompleteItemWriter implements ItemWriter<StorageScheduleLog> {

	private static final Logger log = LoggerFactory.getLogger(StorageScheduleLogCompleteItemWriter.class);
	
	private StorageScheduleLogService storageScheduleLogService;
	
	private DistributionStorageLogService distributionStorageLogService;
	
	public StorageScheduleLogCompleteItemWriter(StorageScheduleLogService storageScheduleLogService,
										DistributionStorageLogService distributionStorageLogService){
		this.storageScheduleLogService = storageScheduleLogService;
		this.distributionStorageLogService = distributionStorageLogService;
	}
	
	@Override
	public void write(List<? extends StorageScheduleLog> scheduleLogList) throws Exception {
		
		Date startDate = new Date();
		
		for(StorageScheduleLog scheduleLog : scheduleLogList){
			List<Long> seqList = storageScheduleLogService.storageScheduleLogCompleteBatch(scheduleLog);
			
			distributionStorageLogService.save(seqList, scheduleLog.getUpdUserInfo(), startDate, "2", "1");
		}
		
		log.info("물류입고실적 배치 종료");
	}
	
}
