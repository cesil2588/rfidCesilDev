package com.systemk.spyder.Batch.ErpScheduleSyncBatch.OnlineReturnBatch;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.External.RfidLa12If;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.ErpOnlineReturn;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;

@Component
public class OnlineReturnItemProcessor implements ItemProcessor<RfidLa12If, ErpOnlineReturn> {

	private static final Logger log = LoggerFactory.getLogger(OnlineReturnItemProcessor.class);

	private CompanyInfoRepository companyInfoRepository;

	public OnlineReturnItemProcessor(CompanyInfoRepository companyInfoRepository){
		this.companyInfoRepository = companyInfoRepository;
	}

    @Override
    public ErpOnlineReturn process(RfidLa12If erp) throws Exception {

    	ErpOnlineReturn onlineReturn = new ErpOnlineReturn();

    	onlineReturn.setOrderDate(erp.getKey().getLa12Jmdt());
    	onlineReturn.setOrderNo(erp.getKey().getLa12Jmid());
    	onlineReturn.setOrderSeq(erp.getKey().getLa12Jmsr().longValue());
    	onlineReturn.setReturnType(erp.getLa12Crgb());
    	onlineReturn.setStat(erp.getLa12Stat());
    	onlineReturn.setStyle(erp.getLa12Styl());
    	onlineReturn.setColor(erp.getLa12Stcd().substring(0, 3));
    	onlineReturn.setSize(erp.getLa12Stcd().substring(3));
    	onlineReturn.setAnnotherStyle(erp.getLa12Stcd());
    	onlineReturn.setReturnAmount(erp.getLa12Bpqt().longValue());
    	onlineReturn.setSalePrice(erp.getLa12Amps().longValue());
    	onlineReturn.setUseMileage(erp.getLa12Milg().longValue());
    	onlineReturn.setUseCoupon(erp.getLa12Cpam().longValue());
    	onlineReturn.setTotalPrice(erp.getLa12Gamt().longValue());
    	onlineReturn.setConfirmAmount(erp.getLa12Cfqt().longValue());
    	onlineReturn.setCustomerName(erp.getLa12Cnam());
    	onlineReturn.setPhoneNo(erp.getLa12Smsn());
    	onlineReturn.setCellPhoneNo(erp.getLa12Teln());
    	onlineReturn.setZipCode(erp.getLa12Zipc());
    	onlineReturn.setAddress1(erp.getLa12Adr1());
    	onlineReturn.setAddress2(erp.getLa12Adr2());
    	onlineReturn.setReturnDate(erp.getLa12Bpdt());
    	onlineReturn.setReturnNo(erp.getLa12Bpsq().longValue());
    	onlineReturn.setReturnReason(erp.getLa12Bpsy());
    	onlineReturn.setDeliveryNo(erp.getLa12Bpsj());
    	onlineReturn.setDeliveryCompanyName(erp.getLa12Bpco());
    	onlineReturn.setPurchaseReturnDate(erp.getLa12Pmdt());
    	onlineReturn.setPurchaseReturnReceiptNo(erp.getLa12Ysno().longValue());
    	onlineReturn.setReleaseDate(erp.getLa12Chdt());
    	onlineReturn.setReleaseDeliveryNo(erp.getLa12Chsj());
    	onlineReturn.setReleaseDeliveryCompanyName(erp.getLa12Chco());
    	onlineReturn.setExplanatory(erp.getLa12Bigo());

    	CompanyInfo distributionCompanyInfo = companyInfoRepository.findByCustomerCodeAndCornerCode(erp.getLa12Cgcd(), erp.getLa12Cgco());
    	CompanyInfo storeCompanyInfo = companyInfoRepository.findByCustomerCodeAndCornerCode(erp.getLa12Mjcd(), erp.getLa12Corn());

    	onlineReturn.setDistributionCompanyInfo(distributionCompanyInfo);
    	onlineReturn.setStoreCompanyInfo(storeCompanyInfo);

    	onlineReturn.setRequestDate(erp.getLa12Rqdt());
    	onlineReturn.setRequestNo(erp.getLa12Rqsq().longValue());
    	onlineReturn.setRequestSeq(erp.getLa12Rqsr().longValue());
    	onlineReturn.setRegType(erp.getLa12Engb());
    	onlineReturn.setOriOrderDate(erp.getLa12JmdtO());
    	onlineReturn.setOriOrderNo(erp.getLa12JmidO());
    	onlineReturn.setOriOrderSeq(erp.getLa12JmsrO().longValue());
    	onlineReturn.setRegDate(new Date());

    	log.info("Converting (" + erp.toString() + ") into (" + onlineReturn.toString() + ")");

        return onlineReturn;
    }
}

