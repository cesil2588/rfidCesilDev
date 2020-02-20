package com.systemk.spyder.Batch.ErpSyncBatch.BartagBatch;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Repository.Main.BartagMasterRepository;
import com.systemk.spyder.Service.BartagService;
import com.systemk.spyder.Util.StringUtil;

public class BartagItemWriter implements ItemWriter<BartagMaster> {

	private static final Logger log = LoggerFactory.getLogger(BartagItemWriter.class);
	
	private BartagMasterRepository bartagMasterRepository;
	
	private BartagService bartagService;
	
	public BartagItemWriter(BartagMasterRepository bartagMasterRepository, BartagService bartagService){
		this.bartagMasterRepository = bartagMasterRepository;
		this.bartagService = bartagService;
	}
	
	@Override
	public void write(List<? extends BartagMaster> bartagList) throws Exception {
		
		List<BartagMaster> bartagInsertList = new ArrayList<BartagMaster>();
		
		for(BartagMaster tempBartag : bartagList){
			
			tempBartag = bartagService.bartagSerialGenerate(tempBartag);
			
			for(BartagMaster bartag : bartagInsertList){
			
				if(bartag.getStyle().equals(tempBartag.getStyle()) &&
				   bartag.getColor().equals(tempBartag.getColor()) &&
				   bartag.getSize().equals(tempBartag.getSize()) &&
				   bartag.getErpKey().equals(tempBartag.getErpKey()) &&
				   bartag.getOrderDegree().equals(tempBartag.getOrderDegree()) &&
				   bartag.getAdditionOrderDegree().equals(tempBartag.getAdditionOrderDegree()) &&
				   bartag.getStartRfidSeq().equals(tempBartag.getStartRfidSeq())){
					
					int start = Integer.parseInt(bartag.getEndRfidSeq()) + 1;
					int end = (start + tempBartag.getAmount().intValue()) - 1;
					int additionOrderDegree = Integer.parseInt(bartag.getAdditionOrderDegree()) + 1;
					
					tempBartag.setStartRfidSeq(StringUtil.convertCipher("5", start));
					tempBartag.setEndRfidSeq(StringUtil.convertCipher("5", end));
					tempBartag.setAdditionOrderDegree(Integer.toString(additionOrderDegree));
				}
			}
			bartagInsertList.add(tempBartag);
		}
		
		bartagMasterRepository.save(bartagInsertList);
		bartagMasterRepository.flush();
		
		log.info("바택 마스터 배치 종료");
	}
}
