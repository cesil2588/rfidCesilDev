package com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleConfirmBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.StorageScheduleLog;

@Component
public class StorageScheduleLogConfirmItemProcessor implements ItemProcessor<StorageScheduleLog, StorageScheduleLog> {

	private static final Logger log = LoggerFactory.getLogger(StorageScheduleLogConfirmItemProcessor.class);
	
	@Override 
    public StorageScheduleLog process(StorageScheduleLog scheduleLog) throws Exception {
        return scheduleLog;
    }
}

