package com.systemk.spyder.Batch.WorkJobBatch.ProductionStorageBatch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.Main.TempProductionStorageHeader;
import com.systemk.spyder.Service.ProductionStorageRfidTagService;

public class ProductionStorageItemWriter implements ItemWriter<TempProductionStorageHeader> {

	private static final Logger log = LoggerFactory.getLogger(ProductionStorageItemWriter.class);
	
	private ProductionStorageRfidTagService productionStorageRfidTagService;
	
	public ProductionStorageItemWriter(ProductionStorageRfidTagService productionStorageRfidTagService){
		this.productionStorageRfidTagService = productionStorageRfidTagService;
	}
	
	@Override
	public void write(List<? extends TempProductionStorageHeader> headerList) throws Exception {
		
		for(TempProductionStorageHeader header : headerList){
			productionStorageRfidTagService.storageCompleteBatch(header);
		}
    	
		log.info("생산태그입고 배치 종료");
	}
	
}
