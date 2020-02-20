package com.systemk.spyder.Batch.WorkJobBatch.ProductionStorageBatch;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.TempProductionStorageHeader;
import com.systemk.spyder.Repository.Main.TempProductionStorageHeaderRepository;

@Component
public class ProductionStorageItemWriterListener implements ItemWriteListener<TempProductionStorageHeader>{
	
	private static final Logger log = LoggerFactory.getLogger(ProductionStorageItemWriterListener.class);
	
	private TempProductionStorageHeaderRepository tempProductionStorageHeaderRepository;
	
	public ProductionStorageItemWriterListener(TempProductionStorageHeaderRepository tempProductionStorageHeaderRepository){
		this.tempProductionStorageHeaderRepository = tempProductionStorageHeaderRepository;
	}

	@Override
	public void beforeWrite(List<? extends TempProductionStorageHeader> items) {
		// TODO Auto-generated method stub
		
	} 

	@Override
	public void afterWrite(List<? extends TempProductionStorageHeader> items) {
		
		for(TempProductionStorageHeader item: items){
			// 배치 여부 업데이트
			item.setBatchYn("Y");
			item.setBatchDate(new Date());
			item.setUpdDate(new Date());
		}
		
		tempProductionStorageHeaderRepository.save(items);
	}

	@Override
	public void onWriteError(Exception exception, List<? extends TempProductionStorageHeader> items) {
		// TODO Auto-generated method stub
		
	}
}
