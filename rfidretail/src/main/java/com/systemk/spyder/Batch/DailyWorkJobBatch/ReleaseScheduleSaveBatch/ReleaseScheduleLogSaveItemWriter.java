package com.systemk.spyder.Batch.DailyWorkJobBatch.ReleaseScheduleSaveBatch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.Main.TempDistributionReleaseBox;
import com.systemk.spyder.Service.DistributionStorageRfidTagService;

public class ReleaseScheduleLogSaveItemWriter implements ItemWriter<TempDistributionReleaseBox> {

	private static final Logger log = LoggerFactory.getLogger(ReleaseScheduleLogSaveItemWriter.class);

	private DistributionStorageRfidTagService distributionStorageRfidTagService;

	public ReleaseScheduleLogSaveItemWriter(DistributionStorageRfidTagService distributionStorageRfidTagService){
		this.distributionStorageRfidTagService = distributionStorageRfidTagService;
	}

	@Override
	public void write(List<? extends TempDistributionReleaseBox> boxList) throws Exception {

		for(TempDistributionReleaseBox tempReleaseBox : boxList){
			distributionStorageRfidTagService.releaseCompleteAfterBatch(tempReleaseBox);
		}

		log.info("매장 출고 배치 종료");
	}

}
