package com.systemk.spyder.Batch.InventoryBatch.DistributionInventoryBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.InventoryScheduleHeader;

@Component
public class DistributionInventoryItemProcessor implements ItemProcessor<InventoryScheduleHeader, InventoryScheduleHeader> {

	private static final Logger log = LoggerFactory.getLogger(DistributionInventoryItemProcessor.class);

    @Override
    public InventoryScheduleHeader process(InventoryScheduleHeader schedule) throws Exception {
        return schedule;
    }
}

