package com.systemk.spyder.Batch.WorkJobBatch.ReleaseScheduleCompleteBatch;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;
import com.systemk.spyder.Repository.Main.ReleaseScheduleLogRepository;

@Component
public class ReleaseScheduleLogCompleteItemWriterListener implements ItemWriteListener<ReleaseScheduleLog>{
	
	private static final Logger log = LoggerFactory.getLogger(ReleaseScheduleLogCompleteItemWriterListener.class);
	
	private ReleaseScheduleLogRepository releaseScheduleLogRepository;
	
	public ReleaseScheduleLogCompleteItemWriterListener(ReleaseScheduleLogRepository releaseScheduleLogRepository){
		this.releaseScheduleLogRepository = releaseScheduleLogRepository;
	}

	@Override
	public void beforeWrite(List<? extends ReleaseScheduleLog> items) {
		// TODO Auto-generated method stub
		
	} 

	@Override
	public void afterWrite(List<? extends ReleaseScheduleLog> items) {
		
		for(ReleaseScheduleLog item: items){
			
			item.setCompleteBatchYn("Y");
			item.setCompleteBatchDate(new Date());
		}
		
		releaseScheduleLogRepository.save(items);
	}

	@Override
	public void onWriteError(Exception exception, List<? extends ReleaseScheduleLog> items) {
		// TODO Auto-generated method stub
		
	}
}
