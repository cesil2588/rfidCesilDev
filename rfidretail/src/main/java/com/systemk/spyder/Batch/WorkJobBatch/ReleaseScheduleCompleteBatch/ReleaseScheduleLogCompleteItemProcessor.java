package com.systemk.spyder.Batch.WorkJobBatch.ReleaseScheduleCompleteBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;

@Component
public class ReleaseScheduleLogCompleteItemProcessor implements ItemProcessor<ReleaseScheduleLog, ReleaseScheduleLog> {

	private static final Logger log = LoggerFactory.getLogger(ReleaseScheduleLogCompleteItemProcessor.class);
	
	@Override 
    public ReleaseScheduleLog process(ReleaseScheduleLog scheduleLog) throws Exception {
        return scheduleLog;
    }
}

