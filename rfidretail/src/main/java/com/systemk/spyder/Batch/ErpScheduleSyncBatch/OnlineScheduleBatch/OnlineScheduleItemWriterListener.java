package com.systemk.spyder.Batch.ErpScheduleSyncBatch.OnlineScheduleBatch;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.ErpOnlineSchedule;
import com.systemk.spyder.Repository.External.RfidLa11IfRepository;

@Component
public class OnlineScheduleItemWriterListener implements ItemWriteListener<ErpOnlineSchedule>{

	private static final Logger log = LoggerFactory.getLogger(OnlineScheduleItemWriterListener.class);

	private RfidLa11IfRepository rfidLa11IfRepository;

	public OnlineScheduleItemWriterListener(RfidLa11IfRepository rfidLa11IfRepository){
		this.rfidLa11IfRepository = rfidLa11IfRepository;
	}

	@Override
	public void beforeWrite(List<? extends ErpOnlineSchedule> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWrite(List<? extends ErpOnlineSchedule> items) {
		for(ErpOnlineSchedule onlineSchedule: items){
			rfidLa11IfRepository.updateTryn(new Date(), onlineSchedule.getOrderDate(), onlineSchedule.getOrderNo(), new BigDecimal(onlineSchedule.getOrderSeq()));
		}

	}

	@Override
	public void onWriteError(Exception exception, List<? extends ErpOnlineSchedule> items) {
		// TODO Auto-generated method stub

	}
}

