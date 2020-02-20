package com.systemk.spyder.Batch.ErpSyncBatch.BartagSerialBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.BartagMaster;

@Component
public class BartagSerialItemProcessor implements ItemProcessor<BartagMaster, BartagMaster> {

	private static final Logger log = LoggerFactory.getLogger(BartagSerialItemProcessor.class);
	
	@Override 
    public BartagMaster process(BartagMaster bartag) throws Exception {
        return bartag;
    }
}

