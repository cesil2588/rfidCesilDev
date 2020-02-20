package com.systemk.spyder.Batch.DailyWorkJobBatch.ReleaseScheduleSaveBatch;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.TempDistributionReleaseBox;
import com.systemk.spyder.Repository.Main.TempDistributionReleaseBoxRepository;

@Component
public class ReleaseScheduleLogSaveItemWriterListener implements ItemWriteListener<TempDistributionReleaseBox>{

	private static final Logger log = LoggerFactory.getLogger(ReleaseScheduleLogSaveItemWriterListener.class);

	private TempDistributionReleaseBoxRepository tempDistributionReleaseBoxRepository;

	public ReleaseScheduleLogSaveItemWriterListener(TempDistributionReleaseBoxRepository tempDistributionReleaseBoxRepository){
		this.tempDistributionReleaseBoxRepository = tempDistributionReleaseBoxRepository;
	}

	@Override
	public void beforeWrite(List<? extends TempDistributionReleaseBox> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWrite(List<? extends TempDistributionReleaseBox> items) {

		for(TempDistributionReleaseBox item: items){
			item.setBatchYn("Y");
			item.setBatchDate(new Date());
		}

		tempDistributionReleaseBoxRepository.save(items);
	}

	@Override
	public void onWriteError(Exception exception, List<? extends TempDistributionReleaseBox> items) {
		// TODO Auto-generated method stub

	}
}
