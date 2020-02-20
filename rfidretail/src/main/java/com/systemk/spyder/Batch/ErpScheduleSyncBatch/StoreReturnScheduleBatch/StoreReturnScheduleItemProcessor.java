package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreReturnScheduleBatch;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreScheduleBatch.StoreScheduleItemProcessor;
import com.systemk.spyder.Entity.External.RfidSd02If;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.ErpStoreReturnSchedule;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;


@Component
public class StoreReturnScheduleItemProcessor implements ItemProcessor<RfidSd02If, ErpStoreReturnSchedule> {

	private static final Logger log = LoggerFactory.getLogger(StoreScheduleItemProcessor.class);

	private CompanyInfoRepository companyInfoRepository;

	public StoreReturnScheduleItemProcessor(CompanyInfoRepository companyInfoRepository){
		this.companyInfoRepository = companyInfoRepository;
	}

    @Override
    public ErpStoreReturnSchedule process(RfidSd02If returnLog) throws Exception {

    	ErpStoreReturnSchedule returnSchedule = new ErpStoreReturnSchedule();

    	CompanyInfo startCompanyInfo = companyInfoRepository.findByCustomerCodeAndCornerCode(returnLog.getSd02Frcd(), returnLog.getSd02Frco());
    	CompanyInfo endCompanyInfo = companyInfoRepository.findByCustomerCodeAndCornerCode(returnLog.getSd02Tocd(), returnLog.getSd02Toco());

    	returnSchedule.setReturnOrderNo(returnLog.getKey().getSd02Orno());
    	returnSchedule.setReturnOrderLiNo(returnLog.getKey().getSd02Lino());
    	returnSchedule.setOrderRegDate(returnLog.getSd02Mgdt());
    	returnSchedule.setStartCompanyInfo(startCompanyInfo);
    	returnSchedule.setStartCompanyCornerCode(returnLog.getSd02Frco());
    	returnSchedule.setReturnReleaseSeq(BigDecimal.valueOf(returnLog.getSd02Mgsq().longValue()));
    	returnSchedule.setReturnOrderSerial(BigDecimal.valueOf(returnLog.getSd02Mgsr().longValue()));
    	returnSchedule.setReturnType(returnLog.getSd02Mggb());
    	returnSchedule.setEndCompanyInfo(endCompanyInfo);
    	returnSchedule.setEndCompanyCornerCode(returnLog.getSd02Toco());
    	returnSchedule.setStyle(returnLog.getSd02Styl());
    	returnSchedule.setReturnAnotherCode(returnLog.getSd02Stcd());
    	returnSchedule.setReturnReportYn(returnLog.getSd02Mtag());
    	returnSchedule.setReturnReportNo(returnLog.getSd02Mkrn().longValue());
    	returnSchedule.setReturnAmount(returnLog.getSd02Mqty().longValue());
    	returnSchedule.setTransYn(returnLog.getSd02Tryn());
    	returnSchedule.setTransDate(returnLog.getSd02Sbdt());
    	returnSchedule.setCompleteYn(returnLog.getSd02Cfyn());
    	returnSchedule.setCompleteDate(returnLog.getSd02Cfdt());
    	returnSchedule.setCompleteUserId(returnLog.getSd02Cfid());
    	returnSchedule.setTransRfidYn("N");
    	returnSchedule.setTransRfidDate("");

    	log.info("Converting (" + returnSchedule.toString() + ") into (" + returnSchedule.toString() + ")");

        return returnSchedule;
    }
}
