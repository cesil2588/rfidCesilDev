package com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleConfirmBatch;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.Service.DistributionStorageLogService;
import com.systemk.spyder.Service.StorageScheduleLogService;

public class StorageScheduleLogConfirmItemWriter implements ItemWriter<StorageScheduleLog> {

	private static final Logger log = LoggerFactory.getLogger(StorageScheduleLogConfirmItemWriter.class);
	
	private StorageScheduleLogService storageScheduleLogService;
	
	private DistributionStorageLogService distributionStorageLogService;
	
	public StorageScheduleLogConfirmItemWriter(StorageScheduleLogService storageScheduleLogService,
										DistributionStorageLogService distributionStorageLogService){
		this.storageScheduleLogService = storageScheduleLogService;
		this.distributionStorageLogService = distributionStorageLogService;
	}
	
	@Override
	public void write(List<? extends StorageScheduleLog> scheduleLogList) throws Exception {
		
		Date startDate = new Date();
		
		for(StorageScheduleLog scheduleLog : scheduleLogList){
			List<Long> seqList = storageScheduleLogService.storageScheduleLogConfirmBatch(scheduleLog);
			
			distributionStorageLogService.save(seqList, scheduleLog.getUpdUserInfo(), startDate, "1", "2");
		}
    	
		log.info("물류입고예정 배치 종료");
	}
	
}
