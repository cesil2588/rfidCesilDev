package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreMoveBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.External.RfidSd02If;

@Component
public class StoreMoveItemProcessor implements ItemProcessor<RfidSd02If, RfidSd02If> {

	private static final Logger log = LoggerFactory.getLogger(StoreMoveItemProcessor.class);

    @Override
    public RfidSd02If process(RfidSd02If erpStoreMove) throws Exception {
        return erpStoreMove;
    }
}

