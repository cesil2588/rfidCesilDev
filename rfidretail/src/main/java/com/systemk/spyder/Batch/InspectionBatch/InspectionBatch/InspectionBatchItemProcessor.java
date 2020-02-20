package com.systemk.spyder.Batch.InspectionBatch.InspectionBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.BatchTrigger;

@Component
public class InspectionBatchItemProcessor implements ItemProcessor<BatchTrigger, BatchTrigger> {
 
	private static final Logger log = LoggerFactory.getLogger(InspectionBatchItemProcessor.class);
	 
	@Override 
    public BatchTrigger process(BatchTrigger trigger) throws Exception {
        return trigger;
    }
}

