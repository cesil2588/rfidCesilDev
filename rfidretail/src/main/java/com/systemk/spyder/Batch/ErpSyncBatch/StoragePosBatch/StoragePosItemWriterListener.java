package com.systemk.spyder.Batch.ErpSyncBatch.StoragePosBatch;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;

import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.External.RfidZa40IfRepository;

public class StoragePosItemWriterListener implements ItemWriteListener<UserInfo>{

	private static final Logger log = LoggerFactory.getLogger(StoragePosItemWriterListener.class);

	private RfidZa40IfRepository rfidZa40IfRepository;

	public StoragePosItemWriterListener(RfidZa40IfRepository rfidZa40IfRepository){
		this.rfidZa40IfRepository = rfidZa40IfRepository;
	}


	@Override
	public void beforeWrite(List<? extends UserInfo> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWrite(List<? extends UserInfo> items) {
		// TODO Auto-generated method stub
		UserInfo chkUser = new UserInfo();

		for(UserInfo userInfo : items){

			// 처리가 되지 않은 경우(userId가 'noAdj'로 세팅되어 있다)
			if(!userInfo.getUserId().equals("noAdj")) {
				rfidZa40IfRepository.updateTryn(new Date(), userInfo.getCompanyInfo().getCustomerCode(), userInfo.getCompanyInfo().getCornerCode());
			}
		}
	}

	@Override
	public void onWriteError(Exception exception, List<? extends UserInfo> items) {
		// TODO Auto-generated method stub

	}

}

