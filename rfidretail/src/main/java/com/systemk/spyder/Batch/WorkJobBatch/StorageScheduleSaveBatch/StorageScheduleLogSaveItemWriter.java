package com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleSaveBatch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.Main.TempProductionReleaseHeader;
import com.systemk.spyder.Service.ProductionStorageRfidTagService;

public class StorageScheduleLogSaveItemWriter implements ItemWriter<TempProductionReleaseHeader> {

	private static final Logger log = LoggerFactory.getLogger(StorageScheduleLogSaveItemWriter.class);
	
	private ProductionStorageRfidTagService productionStorageRfidTagService;
	
	public StorageScheduleLogSaveItemWriter(ProductionStorageRfidTagService productionStorageRfidTagService){
		this.productionStorageRfidTagService = productionStorageRfidTagService;
	}
	
	@Override
	public void write(List<? extends TempProductionReleaseHeader> headerList) throws Exception {
		
		for(TempProductionReleaseHeader header : headerList){
			productionStorageRfidTagService.releaseCompleteBatch(header);
		}
		
		log.info("생산출고작업 배치 종료");
	}
	
}
