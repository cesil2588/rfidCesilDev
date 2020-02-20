package com.systemk.spyder.Batch.ErpScheduleSyncBatch.OnlineReturnBatch;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.Main.ErpOnlineReturn;
import com.systemk.spyder.Repository.Main.ErpOnlineReturnRepository;

public class OnlineReturnItemWriter implements ItemWriter<ErpOnlineReturn> {

	private static final Logger log = LoggerFactory.getLogger(OnlineReturnItemWriter.class);
	
	private ErpOnlineReturnRepository erpOnlineReturnRepository;
	
	public OnlineReturnItemWriter(ErpOnlineReturnRepository erpOnlineReturnRepository){
		this.erpOnlineReturnRepository = erpOnlineReturnRepository;
	}
	
	@Override
	public void write(List<? extends ErpOnlineReturn> onlineReturnList) throws Exception {
		
		List<ErpOnlineReturn> tempOnlineReturnList = new ArrayList<ErpOnlineReturn>();
		
		for(ErpOnlineReturn onlineReturn : onlineReturnList){
			tempOnlineReturnList.add(onlineReturn);
		}
		
		erpOnlineReturnRepository.save(tempOnlineReturnList);
		erpOnlineReturnRepository.flush();
		
		log.info("ERP RFID 온라인반품예정정보 배치 종료");
	}
}
