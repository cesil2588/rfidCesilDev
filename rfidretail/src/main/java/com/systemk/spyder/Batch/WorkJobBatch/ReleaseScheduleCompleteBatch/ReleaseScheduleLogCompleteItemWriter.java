package com.systemk.spyder.Batch.WorkJobBatch.ReleaseScheduleCompleteBatch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleBatch.StorageScheduleLogCompleteItemWriter;
import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;
import com.systemk.spyder.Service.ReleaseScheduleLogService;

public class ReleaseScheduleLogCompleteItemWriter implements ItemWriter<ReleaseScheduleLog> {

	private static final Logger log = LoggerFactory.getLogger(StorageScheduleLogCompleteItemWriter.class);
	
	private ReleaseScheduleLogService releaseScheduleLogService;
	
	public ReleaseScheduleLogCompleteItemWriter(ReleaseScheduleLogService releaseScheduleLogService){
		this.releaseScheduleLogService = releaseScheduleLogService;
	}
	
	@Override
	public void write(List<? extends ReleaseScheduleLog> scheduleLogList) throws Exception {
		
		for(ReleaseScheduleLog scheduleLog : scheduleLogList){
			releaseScheduleLogService.storeScheduleCompleteBatch(scheduleLog);
		}
		
		log.info("매장입고실적 배치 종료");
	}
	
}
