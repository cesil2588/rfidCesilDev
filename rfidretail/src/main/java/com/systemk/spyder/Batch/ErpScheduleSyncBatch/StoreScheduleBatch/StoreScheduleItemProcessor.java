package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreScheduleBatch;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.External.RfidLd02If;
import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.ErpStoreSchedule;
import com.systemk.spyder.Repository.Main.BartagMasterRepository;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Util.CalendarUtil;

@Component
public class StoreScheduleItemProcessor implements ItemProcessor<RfidLd02If, ErpStoreSchedule> {

	private static final Logger log = LoggerFactory.getLogger(StoreScheduleItemProcessor.class);

	private CompanyInfoRepository companyInfoRepository;

	private BartagMasterRepository bartagMasterRepository;

	public StoreScheduleItemProcessor(CompanyInfoRepository companyInfoRepository,
								      BartagMasterRepository bartagMasterRepository){
		this.companyInfoRepository = companyInfoRepository;
		this.bartagMasterRepository = bartagMasterRepository;
	}

    @Override
    public ErpStoreSchedule process(RfidLd02If erpStoreSchedule) throws Exception {

    	ErpStoreSchedule storeSchedule = new ErpStoreSchedule();

    	storeSchedule.setCompleteDate(erpStoreSchedule.getKey().getLd02Mgdt());
    	storeSchedule.setCompleteType(erpStoreSchedule.getKey().getLd02Mggb());
    	storeSchedule.setCompleteSeq(erpStoreSchedule.getKey().getLd02Mgsq());

    	CompanyInfo startCompanyInfo = companyInfoRepository.findByCustomerCodeAndCornerCode(erpStoreSchedule.getKey().getLd02Cgcd(), erpStoreSchedule.getKey().getLd02Cgco());
    	CompanyInfo endCompanyInfo = companyInfoRepository.findByCustomerCodeAndCornerCode(erpStoreSchedule.getKey().getLd02Mjcd(), erpStoreSchedule.getKey().getLd02Mjco());

    	storeSchedule.setStartCompanyInfo(startCompanyInfo);
    	storeSchedule.setEndCompanyInfo(endCompanyInfo);

    	storeSchedule.setStyle(erpStoreSchedule.getKey().getLd02Styl());
    	storeSchedule.setColor(erpStoreSchedule.getLd02It06());
    	storeSchedule.setSize(erpStoreSchedule.getLd02It07());

    	storeSchedule.setAnotherStyle(erpStoreSchedule.getKey().getLd02Stcd());
    	storeSchedule.setBundleCd(erpStoreSchedule.getKey().getLd02Bncd());

    	storeSchedule.setCompleteIfSeq(erpStoreSchedule.getLd02Seqn());
    	storeSchedule.setOrderAmount(erpStoreSchedule.getLd02Jmqt().longValue());
    	storeSchedule.setSortingAmount(erpStoreSchedule.getLd02Sqty().longValue());
    	storeSchedule.setReleaseAmount(erpStoreSchedule.getLd02Chqt().longValue());
    	storeSchedule.setCompleteAmount(Long.valueOf(0));
    	storeSchedule.setReleaseDate(erpStoreSchedule.getLd02Chdt());
    	storeSchedule.setReleaseSeq(erpStoreSchedule.getLd02Chsq().longValue());
    	storeSchedule.setReleaseSerial(erpStoreSchedule.getLd02Chsr().longValue());
    	storeSchedule.setReleaseUserId(erpStoreSchedule.getLd02Chid());
    	storeSchedule.setStat(erpStoreSchedule.getLd02Stat());
    	storeSchedule.setErpRegDate(erpStoreSchedule.getLd02Endt());
    	storeSchedule.setRegUserId(erpStoreSchedule.getLd02Enid());
    	storeSchedule.setRegDate(new Date());
    	storeSchedule.setRegTime(CalendarUtil.convertFormat("HHmm"));
    	storeSchedule.setBatchYn("N");
    	storeSchedule.setErpCompleteYn("N");
    	storeSchedule.setCompleteCheckYn("N");
    	storeSchedule.setReferenceNo(erpStoreSchedule.getKey().getLd02Mgdt() + endCompanyInfo.getCustomerCode() + endCompanyInfo.getCornerCode() + erpStoreSchedule.getKey().getLd02Mgsq());

    	/**
    	 * ERP 출고예정정보에 RFID 부착 스타일인지 확인하기 위한 처리
    	 */
    	List<BartagMaster> bartagList = bartagMasterRepository.findByStyleAndColorAndSize(erpStoreSchedule.getKey().getLd02Styl(), erpStoreSchedule.getLd02It06(), erpStoreSchedule.getLd02It07());

    	storeSchedule.setRfidYn(bartagList.size() > 0 ? "Y" : "N");

    	log.info("Converting (" + erpStoreSchedule.toString() + ") into (" + storeSchedule.toString() + ")");

        return storeSchedule;
    }
}

