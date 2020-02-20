package com.systemk.spyder.Batch.ErpSyncBatch.ProductBatch;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.ProductMaster;
import com.systemk.spyder.Repository.External.RfidAc16IfRepository;

@Component
public class ProductItemWriterListener implements ItemWriteListener<ProductMaster>{

	private static final Logger log = LoggerFactory.getLogger(ProductItemWriterListener.class);

	private RfidAc16IfRepository rfidAc16IfRepository;

	public ProductItemWriterListener(RfidAc16IfRepository rfidAc16IfRepository){
		this.rfidAc16IfRepository = rfidAc16IfRepository;
	}

	@Override
	public void beforeWrite(List<? extends ProductMaster> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWrite(List<? extends ProductMaster> items) {
		for(ProductMaster item : items){
			rfidAc16IfRepository.updateTryn(new Date(), item.getCreateDate(), new BigDecimal(item.getCreateSeq()), new BigDecimal(item.getCreateNo()));
		}
	}

	@Override
	public void onWriteError(Exception exception, List<? extends ProductMaster> items) {
		// TODO Auto-generated method stub

	}

}
