package com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleSaveBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.TempProductionReleaseHeader;

@Component
public class StorageScheduleLogSaveItemProcessor implements ItemProcessor<TempProductionReleaseHeader, TempProductionReleaseHeader> {

	private static final Logger log = LoggerFactory.getLogger(StorageScheduleLogSaveItemProcessor.class);
	
	@Override 
    public TempProductionReleaseHeader process(TempProductionReleaseHeader header) throws Exception {
        return header;
    }
}

