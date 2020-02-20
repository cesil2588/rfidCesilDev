package com.systemk.spyder.Batch.ErpScheduleSyncBatch.OnlineReturnBatch;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.ErpOnlineReturn;
import com.systemk.spyder.Repository.External.RfidLa12IfRepository;

@Component
public class OnlineReturnItemWriterListener implements ItemWriteListener<ErpOnlineReturn>{

	private static final Logger log = LoggerFactory.getLogger(OnlineReturnItemWriterListener.class);

	private RfidLa12IfRepository rfidLa12IfRepository;

	public OnlineReturnItemWriterListener(RfidLa12IfRepository rfidLa12IfRepository){
		this.rfidLa12IfRepository = rfidLa12IfRepository;
	}

	@Override
	public void beforeWrite(List<? extends ErpOnlineReturn> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWrite(List<? extends ErpOnlineReturn> items) {
		for(ErpOnlineReturn onlineReturn: items){
			rfidLa12IfRepository.updateTryn(new Date(), onlineReturn.getOrderDate(), onlineReturn.getOrderNo(), new BigDecimal(onlineReturn.getOrderSeq()));
		}

	}

	@Override
	public void onWriteError(Exception exception, List<? extends ErpOnlineReturn> items) {
		// TODO Auto-generated method stub

	}
}

