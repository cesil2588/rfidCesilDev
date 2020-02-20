package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreScheduleBatch;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.ErpStoreSchedule;
import com.systemk.spyder.Repository.External.RfidLd02IfRepository;
import com.systemk.spyder.Util.CalendarUtil;

@Component
public class StoreScheduleItemWriterListener implements ItemWriteListener<ErpStoreSchedule>{

	private RfidLd02IfRepository rfidLd02IfRepository;

	public StoreScheduleItemWriterListener(RfidLd02IfRepository rfidLd02IfRepository){
		this.rfidLd02IfRepository = rfidLd02IfRepository;
	}

	@Override
	public void beforeWrite(List<? extends ErpStoreSchedule> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWrite(List<? extends ErpStoreSchedule> items) {

		for(ErpStoreSchedule storeSchedule: items){

			rfidLd02IfRepository.updateTryn(CalendarUtil.convertFormat("yyyyMMddHHmmss"),
											storeSchedule.getCompleteDate(),
											storeSchedule.getCompleteType(),
											storeSchedule.getCompleteSeq(),
											storeSchedule.getEndCompanyInfo().getCustomerCode(),
											storeSchedule.getEndCompanyInfo().getCornerCode(),
											storeSchedule.getStartCompanyInfo().getCustomerCode(),
											storeSchedule.getStartCompanyInfo().getCornerCode(),
											storeSchedule.getStyle(),
											storeSchedule.getAnotherStyle(),
											storeSchedule.getBundleCd(),
											storeSchedule.getCompleteIfSeq());
		}

	}

	@Override
	public void onWriteError(Exception exception, List<? extends ErpStoreSchedule> items) {
		// TODO Auto-generated method stub

	}
}

