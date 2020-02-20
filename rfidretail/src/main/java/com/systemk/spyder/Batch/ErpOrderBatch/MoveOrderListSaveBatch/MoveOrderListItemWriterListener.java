package com.systemk.spyder.Batch.ErpOrderBatch.MoveOrderListSaveBatch;

import java.util.Date;
import java.util.List;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.ErpStoreMove;
import com.systemk.spyder.Repository.External.RfidSd06IfRepository;

@Component
public class MoveOrderListItemWriterListener implements ItemWriteListener<ErpStoreMove>{

	private RfidSd06IfRepository rfidSd06IfRepository;

	public MoveOrderListItemWriterListener(RfidSd06IfRepository rfidSd06IfRepository){
		this.rfidSd06IfRepository = rfidSd06IfRepository;
	}

	@Override
	public void beforeWrite(List<? extends ErpStoreMove> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWrite(List<? extends ErpStoreMove> items) {

		for(ErpStoreMove storeMove: items){

			rfidSd06IfRepository.updateTryn(new Date(),
											storeMove.getOrderRegDate(),
											storeMove.getOrderSeq(),
											storeMove.getOrderSerial());
		}

	}

	@Override
	public void onWriteError(Exception exception, List<? extends ErpStoreMove> items) {
		// TODO Auto-generated method stub

	}
}

