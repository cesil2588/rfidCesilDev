package com.systemk.spyder.Batch.TextUploadBatch.BartagTextUploadReissueBatch;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.Main.BatchTrigger;
import com.systemk.spyder.Entity.Main.BatchTriggerDetail;
import com.systemk.spyder.Service.RfidTagService;

public class BartagTextUploadReissueItemWriter implements ItemWriter<BatchTrigger> {

	private static final Logger log = LoggerFactory.getLogger(BartagTextUploadReissueItemWriter.class);
	
	private RfidTagService rfidTagService;
	
	public BartagTextUploadReissueItemWriter(RfidTagService rfidTagService){
		this.rfidTagService = rfidTagService;
	}
	
	@Override
	public void write(List<? extends BatchTrigger> triggerList) throws Exception {
		
		for(BatchTrigger trigger : triggerList){
			Set<BatchTriggerDetail> detailSet = rfidTagService.textLoadReissueBatch(trigger);
			
			trigger.setDetailSet(detailSet);
		}
		
		log.info("바택 텍스트 업로드 재발행 배치 종료");
	}
	
}
