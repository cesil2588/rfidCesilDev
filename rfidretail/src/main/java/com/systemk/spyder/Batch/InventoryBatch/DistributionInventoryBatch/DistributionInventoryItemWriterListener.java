package com.systemk.spyder.Batch.InventoryBatch.DistributionInventoryBatch;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.InventoryScheduleHeader;
import com.systemk.spyder.Repository.Main.InventoryScheduleHeaderRepository;

@Component
public class DistributionInventoryItemWriterListener implements ItemWriteListener<InventoryScheduleHeader>{

	private static final Logger log = LoggerFactory.getLogger(DistributionInventoryItemWriterListener.class);

	private InventoryScheduleHeaderRepository inventoryScheduleHeaderRepository;

	public DistributionInventoryItemWriterListener(InventoryScheduleHeaderRepository inventoryScheduleHeaderRepository){
		this.inventoryScheduleHeaderRepository = inventoryScheduleHeaderRepository;
	}

	@Override
	public void beforeWrite(List<? extends InventoryScheduleHeader> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWrite(List<? extends InventoryScheduleHeader> items) {

		for(InventoryScheduleHeader item: items){

			// 諛곗튂 �듃由ш굅 寃곌낵 �뾽�뜲�씠�듃
			item.setBatchYn("Y");
			item.setBatchDate(new Date());
		}

		inventoryScheduleHeaderRepository.save(items);
	}

	@Override
	public void onWriteError(Exception exception, List<? extends InventoryScheduleHeader> items) {
		// TODO Auto-generated method stub

	}
}
