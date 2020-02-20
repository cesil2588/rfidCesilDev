package com.systemk.spyder.Batch.WorkJobBatch.ReleaseErpScheduleBatch;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.ErpStoreSchedule;
import com.systemk.spyder.Repository.Main.ErpStoreScheduleRepository;

@Component
public class ErpScheduleItemWriterListener implements ItemWriteListener<ErpStoreSchedule>{
	
	private static final Logger log = LoggerFactory.getLogger(ErpScheduleItemWriterListener.class);
	
	private ErpStoreScheduleRepository erpStoreScheduleRepository;
	
	public ErpScheduleItemWriterListener(ErpStoreScheduleRepository erpStoreScheduleRepository){
		this.erpStoreScheduleRepository = erpStoreScheduleRepository;
	}

	@Override
	public void beforeWrite(List<? extends ErpStoreSchedule> items) {
		// TODO Auto-generated method stub
		
	} 

	@Override
	public void afterWrite(List<? extends ErpStoreSchedule> items) {
		
		for(ErpStoreSchedule item : items) {
			if(item.getCompleteCheckYn().equals("Y") && item.getErpCompleteYn().equals("Y")) {
				item.setBatchYn("Y");
				item.setBatchDate(new Date());
			}
		}
		erpStoreScheduleRepository.save(items);
	}

	@Override
	public void onWriteError(Exception exception, List<? extends ErpStoreSchedule> items) {
		// TODO Auto-generated method stub
		
	}
}
