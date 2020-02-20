package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreStorageBatch;

import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.ReleaseScheduleDetailLog;
import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;
import com.systemk.spyder.Repository.External.RfidLe10IfRepository;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public class StoreStorageItemWriterListener implements ItemWriteListener<ReleaseScheduleLog>{

	private RfidLe10IfRepository rfidLe10IfRepository;

	public StoreStorageItemWriterListener(RfidLe10IfRepository rfidLe10IfRepository){
		this.rfidLe10IfRepository = rfidLe10IfRepository;
	}

	@Override
	public void beforeWrite(List<? extends ReleaseScheduleLog> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWrite(List<? extends ReleaseScheduleLog> items) {

		for(ReleaseScheduleLog scheduleLog : items){
			for(ReleaseScheduleDetailLog detailLog : scheduleLog.getReleaseScheduleDetailLog()) {
				rfidLe10IfRepository.batchUpdate(new Date(),
												scheduleLog.getErpReleaseDate(),
												new BigDecimal(scheduleLog.getErpReleaseSeq()),
												scheduleLog.getBoxInfo().getEndCompanyInfo().getCustomerCode(),
												scheduleLog.getBoxInfo().getEndCompanyInfo().getCornerCode(),
												detailLog.getStyle(),
												detailLog.getColor() + detailLog.getSize());
			}

		}

	}

	@Override
	public void onWriteError(Exception exception, List<? extends ReleaseScheduleLog> items) {
		// TODO Auto-generated method stub

	}
}

