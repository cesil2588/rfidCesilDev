package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreScheduleBatch;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.jpa.domain.Specifications;

import com.systemk.spyder.Entity.Main.ErpStoreSchedule;
import com.systemk.spyder.Repository.Main.ErpStoreScheduleRepository;
import com.systemk.spyder.Repository.Main.Specification.ErpStoreScheduleSpecification;

public class StoreScheduleItemWriter implements ItemWriter<ErpStoreSchedule> {

	private static final Logger log = LoggerFactory.getLogger(StoreScheduleItemWriter.class);
	
	private ErpStoreScheduleRepository erpStoreScheduleRepository;
	
	public StoreScheduleItemWriter(ErpStoreScheduleRepository erpStoreScheduleRepository){
		this.erpStoreScheduleRepository = erpStoreScheduleRepository;
	}
	
	@Override
	public void write(List<? extends ErpStoreSchedule> scheduleList) throws Exception {
		
		ArrayList<ErpStoreSchedule> tempScheduleList = new ArrayList<ErpStoreSchedule>();
		
		for(ErpStoreSchedule schedule : scheduleList){
			
			Specifications<ErpStoreSchedule> erpStoreScheduleSpec = Specifications.where(ErpStoreScheduleSpecification.completeDateEqual(schedule.getCompleteDate()))
																				  .and(ErpStoreScheduleSpecification.completeTypeEqual(schedule.getCompleteType()))
																				  .and(ErpStoreScheduleSpecification.completeSeqEqual(schedule.getCompleteSeq()))
																				  .and(ErpStoreScheduleSpecification.endCompanySeqEqual(schedule.getEndCompanyInfo().getCompanySeq()))
																				  .and(ErpStoreScheduleSpecification.styleEqual(schedule.getStyle()))
																				  .and(ErpStoreScheduleSpecification.colorEqual(schedule.getColor()))
																				  .and(ErpStoreScheduleSpecification.sizeEqual(schedule.getSize()))
																				  .and(ErpStoreScheduleSpecification.completeIfSeqEqual(schedule.getCompleteIfSeq()));
			
			// 등록
			if (schedule.getStat().equals("I")) {
				
				ErpStoreSchedule tempSchedule = erpStoreScheduleRepository.findOne(erpStoreScheduleSpec);
				if (tempSchedule == null) {
					tempScheduleList.add(schedule);
				}

			// 수정, 삭제
			} else if (schedule.getStat().equals("U")) {
				
				ErpStoreSchedule tempSchedule = erpStoreScheduleRepository.findOne(erpStoreScheduleSpec);
				tempSchedule.CopyData(schedule);
				tempScheduleList.add(tempSchedule);
				
			} else if(schedule.getStat().equals("D")){
				
			}
			
		}
		
		erpStoreScheduleRepository.save(tempScheduleList);
		erpStoreScheduleRepository.flush();
		
		log.info("ERP RFID 매장 출고예정정보 배치 종료");
	}
}
