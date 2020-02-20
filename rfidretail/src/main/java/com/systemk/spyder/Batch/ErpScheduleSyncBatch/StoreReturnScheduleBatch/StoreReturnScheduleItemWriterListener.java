package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreReturnScheduleBatch;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.ErpStoreReturnSchedule;
import com.systemk.spyder.Repository.External.RfidSd02IfRepository;

@Component
public class StoreReturnScheduleItemWriterListener implements ItemWriteListener<ErpStoreReturnSchedule>{

	private RfidSd02IfRepository rfidSd02IfRepository;

	public StoreReturnScheduleItemWriterListener(RfidSd02IfRepository rfidSd02IfRepository){
		this.rfidSd02IfRepository = rfidSd02IfRepository;
	}

	@Override
	public void beforeWrite(List<? extends ErpStoreReturnSchedule> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWrite(List<? extends ErpStoreReturnSchedule> items) {
		for(ErpStoreReturnSchedule erpStoreReturnSchedule: items){
			rfidSd02IfRepository.updateTryn(erpStoreReturnSchedule.getReturnOrderNo(), erpStoreReturnSchedule.getReturnOrderLiNo());
		}

	}

	@Override
	public void onWriteError(Exception exception, List<? extends ErpStoreReturnSchedule> items) {
		// TODO Auto-generated method stub

	}
}

