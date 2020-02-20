package com.systemk.spyder.Batch.ErpScheduleSyncBatch.OnlineScheduleBatch;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.Main.ErpOnlineSchedule;
import com.systemk.spyder.Repository.Main.ErpOnlineScheduleRepository;

public class OnlineScheduleItemWriter implements ItemWriter<ErpOnlineSchedule> {

	private static final Logger log = LoggerFactory.getLogger(OnlineScheduleItemWriter.class);
	
	private ErpOnlineScheduleRepository erpOnlineScheduleRepository;
	
	public OnlineScheduleItemWriter(ErpOnlineScheduleRepository erpOnlineScheduleRepository){
		this.erpOnlineScheduleRepository = erpOnlineScheduleRepository;
	}
	
	@Override
	public void write(List<? extends ErpOnlineSchedule> onlineReturnList) throws Exception {
		
		List<ErpOnlineSchedule> tempOnlineScheduleList = new ArrayList<ErpOnlineSchedule>();
		
		for(ErpOnlineSchedule onlineSchedule : onlineReturnList){
			tempOnlineScheduleList.add(onlineSchedule);
		}
		
		erpOnlineScheduleRepository.save(tempOnlineScheduleList);
		erpOnlineScheduleRepository.flush();
		
		log.info("ERP RFID 온라인출고예정정보 배치 종료");
	}
}
