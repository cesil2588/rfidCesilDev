package com.systemk.spyder.Batch.TextUploadBatch.BartagTextUploadBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.BatchTrigger;

@Component
public class BartagTextUploadItemProcessor implements ItemProcessor<BatchTrigger, BatchTrigger> {

	private static final Logger log = LoggerFactory.getLogger(BartagTextUploadItemProcessor.class);
	
	@Override 
    public BatchTrigger process(BatchTrigger trigger) throws Exception {
        return trigger;
    }
	
}

