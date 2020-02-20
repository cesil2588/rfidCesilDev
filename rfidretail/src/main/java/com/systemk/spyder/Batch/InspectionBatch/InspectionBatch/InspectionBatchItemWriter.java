package com.systemk.spyder.Batch.InspectionBatch.InspectionBatch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.Main.BatchTrigger;
import com.systemk.spyder.Service.ProductionStorageRfidTagService;

public class InspectionBatchItemWriter implements ItemWriter<BatchTrigger> {

	private static final Logger log = LoggerFactory.getLogger(InspectionBatchItemWriter.class);
	
	private ProductionStorageRfidTagService productionStorageRfidTagService;
	
	public InspectionBatchItemWriter(ProductionStorageRfidTagService productionStorageRfidTagService){
		this.productionStorageRfidTagService = productionStorageRfidTagService;
	}
	
	@Override
	public void write(List<? extends BatchTrigger> triggerList) throws Exception {
		
		for(BatchTrigger trigger : triggerList){
			productionStorageRfidTagService.inspectionBatch(trigger);
		}
		
		log.info("생산 입고 배치 종료");
	}
}
