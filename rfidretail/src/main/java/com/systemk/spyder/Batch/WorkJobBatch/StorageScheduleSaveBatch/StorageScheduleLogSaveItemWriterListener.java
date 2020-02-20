package com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleSaveBatch;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.TempProductionReleaseHeader;
import com.systemk.spyder.Repository.Main.TempProductionReleaseHeaderRepository;

@Component
public class StorageScheduleLogSaveItemWriterListener implements ItemWriteListener<TempProductionReleaseHeader>{
	
	private static final Logger log = LoggerFactory.getLogger(StorageScheduleLogSaveItemWriterListener.class);
	
	private TempProductionReleaseHeaderRepository tempProductionReleaseHeaderRepository;
	
	public StorageScheduleLogSaveItemWriterListener(TempProductionReleaseHeaderRepository tempProductionReleaseHeaderRepository){
		this.tempProductionReleaseHeaderRepository = tempProductionReleaseHeaderRepository;
	}

	@Override
	public void beforeWrite(List<? extends TempProductionReleaseHeader> items) {
		// TODO Auto-generated method stub
		
	} 

	@Override
	public void afterWrite(List<? extends TempProductionReleaseHeader> items) {
		
		for(TempProductionReleaseHeader item: items){
			
			// 배치 여부 업데이트
			item.setBatchYn("Y");
			item.setBatchDate(new Date());
			item.setUpdDate(new Date());
		}
		
		tempProductionReleaseHeaderRepository.save(items);
	}

	@Override
	public void onWriteError(Exception exception, List<? extends TempProductionReleaseHeader> items) {
		// TODO Auto-generated method stub
		
	}
}
