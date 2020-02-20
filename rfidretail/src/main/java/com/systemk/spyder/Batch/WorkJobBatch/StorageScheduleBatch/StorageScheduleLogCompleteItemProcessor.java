package com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.StorageScheduleLog;

@Component
public class StorageScheduleLogCompleteItemProcessor implements ItemProcessor<StorageScheduleLog, StorageScheduleLog> {

	private static final Logger log = LoggerFactory.getLogger(StorageScheduleLogCompleteItemProcessor.class);
	
	@Override 
    public StorageScheduleLog process(StorageScheduleLog scheduleLog) throws Exception {
        return scheduleLog;
    }
}

