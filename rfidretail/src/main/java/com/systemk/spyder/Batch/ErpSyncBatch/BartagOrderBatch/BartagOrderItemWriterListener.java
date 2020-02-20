package com.systemk.spyder.Batch.ErpSyncBatch.BartagOrderBatch;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.BartagOrder;
import com.systemk.spyder.Repository.External.RfidAa06IfRepository;

@Component
public class BartagOrderItemWriterListener implements ItemWriteListener<BartagOrder>{

	private static final Logger log = LoggerFactory.getLogger(BartagOrderItemWriterListener.class);

	private RfidAa06IfRepository rfidAa06IfRepository;

	public BartagOrderItemWriterListener(RfidAa06IfRepository rfidAa06IfRepository){
		this.rfidAa06IfRepository = rfidAa06IfRepository;
	}

	@Override
	public void beforeWrite(List<? extends BartagOrder> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWrite(List<? extends BartagOrder> items) {

		for(BartagOrder bartagOrder: items){

			// 업체정보가 맵핑 안됐을 경우 인터페이스에서 가져오지 않도록 수정
			if(bartagOrder.getProductionCompanyInfo() == null) {
				continue;
			}
			rfidAa06IfRepository.updateTryn(new Date(), bartagOrder.getCreateDate(), new BigDecimal(bartagOrder.getCreateSeq()), new BigDecimal(bartagOrder.getCreateNo()));
		}

	}

	@Override
	public void onWriteError(Exception exception, List<? extends BartagOrder> items) {
		// TODO Auto-generated method stub

	}
}
