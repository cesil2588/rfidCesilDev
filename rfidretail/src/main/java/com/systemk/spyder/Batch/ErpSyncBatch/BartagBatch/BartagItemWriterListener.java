package com.systemk.spyder.Batch.ErpSyncBatch.BartagBatch;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Repository.External.RfidAc18IfRepository;

@Component
public class BartagItemWriterListener implements ItemWriteListener<BartagMaster>{

	private static final Logger log = LoggerFactory.getLogger(BartagItemWriterListener.class);

	private RfidAc18IfRepository rfidAc18IfRepository;

	public BartagItemWriterListener(RfidAc18IfRepository rfidAc18IfRepository){
		this.rfidAc18IfRepository = rfidAc18IfRepository;
	}

	@Override
	public void beforeWrite(List<? extends BartagMaster> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWrite(List<? extends BartagMaster> items) {

		for(BartagMaster bartagMaster: items){
			rfidAc18IfRepository.updateTryn(new Date(), bartagMaster.getCreateDate(), new BigDecimal(bartagMaster.getSeq()), new BigDecimal(bartagMaster.getLineSeq()));
		}

	}

	@Override
	public void onWriteError(Exception exception, List<? extends BartagMaster> items) {
		// TODO Auto-generated method stub

	}
}
