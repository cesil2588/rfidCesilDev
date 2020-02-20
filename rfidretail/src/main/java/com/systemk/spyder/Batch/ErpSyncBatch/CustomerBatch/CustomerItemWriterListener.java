package com.systemk.spyder.Batch.ErpSyncBatch.CustomerBatch;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Repository.External.RfidZa11IfRepository;

@Component
public class CustomerItemWriterListener implements ItemWriteListener<CompanyInfo>{

	private static final Logger log = LoggerFactory.getLogger(CustomerItemWriterListener.class);

	private RfidZa11IfRepository rfidZa11IfRepository;

	public CustomerItemWriterListener(RfidZa11IfRepository rfidZa11IfRepository){
		this.rfidZa11IfRepository = rfidZa11IfRepository;
	}


	@Override
	public void beforeWrite(List<? extends CompanyInfo> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWrite(List<? extends CompanyInfo> items) {
		for(CompanyInfo company : items){
			rfidZa11IfRepository.updateTryn(new Date(), company.getCreateDate(), new BigDecimal(company.getCreateNo()));
		}
	}

	@Override
	public void onWriteError(Exception exception, List<? extends CompanyInfo> items) {
		// TODO Auto-generated method stub

	}

}
