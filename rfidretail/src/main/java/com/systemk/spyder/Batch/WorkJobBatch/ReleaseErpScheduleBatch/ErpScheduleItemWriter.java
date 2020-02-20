package com.systemk.spyder.Batch.WorkJobBatch.ReleaseErpScheduleBatch;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.Main.ErpStoreSchedule;
import com.systemk.spyder.Service.ErpStoreScheduleService;

public class ErpScheduleItemWriter implements ItemWriter<ErpStoreSchedule> {

	private static final Logger log = LoggerFactory.getLogger(ErpScheduleItemWriter.class);
	
	private ErpStoreScheduleService erpStoreScheduleService;
	
	public ErpScheduleItemWriter(ErpStoreScheduleService erpStoreScheduleService){
		this.erpStoreScheduleService = erpStoreScheduleService;
	}
	
	@Override
	public void write(List<? extends ErpStoreSchedule> scheduleList) throws Exception {
		
		for(ErpStoreSchedule schedule : scheduleList){
			
			if(schedule.getCompleteCheckYn().equals("Y")) {
				if(erpStoreScheduleService.releaseCompleteAfterErpBatch(schedule)) {
					schedule.setErpCompleteYn("Y");
					schedule.setErpCompleteDate(new Date());
				}
			}
		}
		
		log.info("매장 출고 ERP 배치 종료");
	}
	
}
