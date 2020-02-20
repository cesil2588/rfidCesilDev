package com.systemk.spyder.Batch.ErpOrderBatch.ReturnOrderListSaveBatch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.ErpStoreReturnInfo;
import com.systemk.spyder.Entity.Main.ErpStoreSchedule;
import com.systemk.spyder.Repository.External.RfidSd14IfRepository;
import com.systemk.spyder.Util.CalendarUtil;

@Component
public class ReturnOrderListItemWriterListener implements ItemWriteListener<ErpStoreReturnInfo>{

	private RfidSd14IfRepository rfidSd14IfRepository;

	public ReturnOrderListItemWriterListener(RfidSd14IfRepository rfidSd14IfRepository){
		this.rfidSd14IfRepository = rfidSd14IfRepository;
	}

	@Override
	public void beforeWrite(List<? extends ErpStoreReturnInfo> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWrite(List<? extends ErpStoreReturnInfo> items) {

		SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
		for(ErpStoreReturnInfo returnInfo: items){

			rfidSd14IfRepository.updateTryn(new Date(),
											sFormat.format(returnInfo.getErpRegDate()),
											returnInfo.getErpReturnNo(),
											returnInfo.getReturnType(),
											returnInfo.getFromCustomerCode(),
											returnInfo.getFromCornerCode(),
											returnInfo.getSku().substring(0,12),
											returnInfo.getSku().substring(12));
		}

	}

	@Override
	public void onWriteError(Exception exception, List<? extends ErpStoreReturnInfo> items) {
		// TODO Auto-generated method stub

	}
}

