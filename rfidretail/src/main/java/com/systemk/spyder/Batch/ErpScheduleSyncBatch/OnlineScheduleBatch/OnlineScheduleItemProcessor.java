package com.systemk.spyder.Batch.ErpScheduleSyncBatch.OnlineScheduleBatch;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.External.RfidLa11If;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.ErpOnlineSchedule;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;

@Component
public class OnlineScheduleItemProcessor implements ItemProcessor<RfidLa11If, ErpOnlineSchedule> {

	private static final Logger log = LoggerFactory.getLogger(OnlineScheduleItemProcessor.class);

	private CompanyInfoRepository companyInfoRepository;

	public OnlineScheduleItemProcessor(CompanyInfoRepository companyInfoRepository){
		this.companyInfoRepository = companyInfoRepository;
	}

    @Override
    public ErpOnlineSchedule process(RfidLa11If erp) throws Exception {

    	ErpOnlineSchedule onlineSchedule = new ErpOnlineSchedule();

    	onlineSchedule.setOrderDate(erp.getKey().getLa11Jmdt());
    	onlineSchedule.setOrderNo(erp.getKey().getLa11Jmid());
    	onlineSchedule.setOrderSeq(erp.getKey().getLa11Jmsr().longValue());
    	onlineSchedule.setOrderType(erp.getLa11Jmgb());
    	onlineSchedule.setExchangeSeq(erp.getLa11Exsq().longValue());
    	onlineSchedule.setStyle(erp.getLa11Styl());
    	onlineSchedule.setColor(erp.getLa11Stcd().substring(0, 3));
    	onlineSchedule.setSize(erp.getLa11Stcd().substring(3));
    	onlineSchedule.setAnnotherStyle(erp.getLa11Stcd());
    	onlineSchedule.setSalePrice(erp.getLa11Amps().longValue());
    	onlineSchedule.setUseMileage(erp.getLa11Milg().longValue());
    	onlineSchedule.setUseCoupon(erp.getLa11Cpam().longValue());
    	onlineSchedule.setTotalPrice(erp.getLa11Gamt().longValue());
    	onlineSchedule.setConfirmAmount(erp.getLa11Cfqt().longValue());
    	onlineSchedule.setMarginPercent(erp.getLa11Majp().longValue());
    	onlineSchedule.setStat(erp.getLa11Stat());
    	onlineSchedule.setStockAddType(erp.getLa11Bogb());
    	onlineSchedule.setCustomerName(erp.getLa11Cnam());
    	onlineSchedule.setPhoneNo(erp.getLa11Smsn());
    	onlineSchedule.setCellPhoneNo(erp.getLa11Teln());
    	onlineSchedule.setZipCode(erp.getLa11Zipc());
    	onlineSchedule.setAddress1(erp.getLa11Adr1());
    	onlineSchedule.setAddress2(erp.getLa11Adr2());
    	onlineSchedule.setDeliveryNo(erp.getLa11Sjno());
    	onlineSchedule.setDeliveryCompanyName(erp.getLa11Comp());
    	onlineSchedule.setExplanatory(erp.getLa11Bigo());
    	onlineSchedule.setReleaseDate(erp.getLa11Chdt());
    	onlineSchedule.setReleaseNo(erp.getLa11Chsq().longValue());
    	onlineSchedule.setReleaseSeq(erp.getLa11Chsr().longValue());
    	onlineSchedule.setArrearageDate(erp.getLa11Mgdt());
    	onlineSchedule.setArrearageNo(erp.getLa11Mgsq().longValue());
    	onlineSchedule.setArrearageSeq(erp.getLa11Mgsr().longValue());
    	onlineSchedule.setSaleDate(erp.getLa11Pmdt());
    	onlineSchedule.setReceiptNo(erp.getLa11Ysno().longValue());
    	onlineSchedule.setCancelReason(erp.getLa11Clsy());
    	onlineSchedule.setReleaseStockAddType(erp.getLa11Bst1());
    	onlineSchedule.setReleaseStockAddDate(erp.getLa11Bdt1());
    	onlineSchedule.setReleaseStockAddTime(erp.getLa11Btm1());
    	onlineSchedule.setReleaseStockAddUserId(erp.getLa11Bid1());
    	onlineSchedule.setMoveStockAddType(erp.getLa11Bst2());
    	onlineSchedule.setMoveStockAddDate(erp.getLa11Bdt2());
    	onlineSchedule.setMoveStockAddTime(erp.getLa11Btm2());
    	onlineSchedule.setMoveStockAddUserId(erp.getLa11Bid2());
    	onlineSchedule.setRequestCount(erp.getLa11Rqno().longValue());
    	onlineSchedule.setRequestDate(erp.getLa11Rqdt());
    	onlineSchedule.setRequestNo(erp.getLa11Rqsq().longValue());
    	onlineSchedule.setRequestSeq(erp.getLa11Rqsr().longValue());
    	onlineSchedule.setRequestWmsYn(erp.getLa11Ykyn());
    	onlineSchedule.setReasonType(erp.getLa11Sygb());
    	onlineSchedule.setExchangeReason(erp.getLa11Sayu());
    	onlineSchedule.setRegType(erp.getLa11Engb());
    	onlineSchedule.setOriOrderDate(erp.getLa11JmdtO());
    	onlineSchedule.setOriOrderNo(erp.getLa11JmidO());
    	onlineSchedule.setOriOrderSeq(erp.getLa11JmsrO().longValue());

    	CompanyInfo distributionCompanyInfo = companyInfoRepository.findByCustomerCodeAndCornerCode(erp.getLa11Cgcd(), erp.getLa11Cgco());
    	CompanyInfo storeCompanyInfo = companyInfoRepository.findByCustomerCodeAndCornerCode(erp.getLa11Mjcd(), erp.getLa11Corn());
    	CompanyInfo startCompanyInfo = companyInfoRepository.findByCustomerCodeAndCornerCode(erp.getLa11Frcd(), erp.getLa11Frco());

    	onlineSchedule.setDistributionCompanyInfo(distributionCompanyInfo);
    	onlineSchedule.setStoreCompanyInfo(storeCompanyInfo);
    	onlineSchedule.setStartCompanyInfo(startCompanyInfo);

    	onlineSchedule.setRegDate(new Date());

    	log.info("Converting (" + erp.toString() + ") into (" + onlineSchedule.toString() + ")");

        return onlineSchedule;
    }
}

