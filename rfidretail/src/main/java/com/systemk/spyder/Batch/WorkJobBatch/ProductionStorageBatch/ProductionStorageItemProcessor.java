package com.systemk.spyder.Batch.WorkJobBatch.ProductionStorageBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.TempProductionStorageHeader;

@Component
public class ProductionStorageItemProcessor implements ItemProcessor<TempProductionStorageHeader, TempProductionStorageHeader> {

	private static final Logger log = LoggerFactory.getLogger(ProductionStorageItemProcessor.class);
	
	@Override 
    public TempProductionStorageHeader process(TempProductionStorageHeader header) throws Exception {
        return header;
    }
}

