package com.systemk.spyder.Batch.DailyWorkJobBatch.ReleaseScheduleSaveBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.TempDistributionReleaseBox;

@Component
public class ReleaseScheduleLogSaveItemProcessor implements ItemProcessor<TempDistributionReleaseBox, TempDistributionReleaseBox> {

	private static final Logger log = LoggerFactory.getLogger(ReleaseScheduleLogSaveItemProcessor.class);

	@Override
    public TempDistributionReleaseBox process(TempDistributionReleaseBox tempReleaseBox) throws Exception {
        return tempReleaseBox;
    }
}

