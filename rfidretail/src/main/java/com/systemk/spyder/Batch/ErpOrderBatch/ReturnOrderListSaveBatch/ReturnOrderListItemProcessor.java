package com.systemk.spyder.Batch.ErpOrderBatch.ReturnOrderListSaveBatch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.External.RfidSd14If;
import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.ErpStoreReturnInfo;
import com.systemk.spyder.Entity.Main.ErpStoreReturnSubInfo;
import com.systemk.spyder.Entity.Main.ErpStoreSchedule;
import com.systemk.spyder.Repository.Main.BartagMasterRepository;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Util.CalendarUtil;

@Component
public class ReturnOrderListItemProcessor implements ItemProcessor<RfidSd14If, ErpStoreReturnInfo> {

	private static final Logger log = LoggerFactory.getLogger(ReturnOrderListItemProcessor.class);

	public ReturnOrderListItemProcessor(){
	}

    @Override
    public ErpStoreReturnInfo process(RfidSd14If erpStoreReturnInfo) throws Exception {

    	ErpStoreReturnInfo returnInfo = new ErpStoreReturnInfo();
    	ErpStoreReturnSubInfo returnSubInfo = new ErpStoreReturnSubInfo();
    	
    	SimpleDateFormat transformat = new SimpleDateFormat("yyyyMMdd");
    	String erpSd14Key = erpStoreReturnInfo.getKey().getSd14Jsdt() + erpStoreReturnInfo.getKey().getSd14Jssq();
    	erpSd14Key += erpStoreReturnInfo.getKey().getSd14Mggb() + erpStoreReturnInfo.getKey().getSd14Frcd() + erpStoreReturnInfo.getKey().getSd14Rfco();
    	erpSd14Key += erpStoreReturnInfo.getKey().getSd14Styl() + erpStoreReturnInfo.getKey().getSd14Stcd();
    	    	
    	returnInfo.setErpReturnNo(erpStoreReturnInfo.getKey().getSd14Jssq());
    	returnInfo.setErpRegDate(transformat.parse(erpStoreReturnInfo.getKey().getSd14Jsdt()));
    	returnInfo.setReturnType(erpStoreReturnInfo.getKey().getSd14Mggb());
    	returnInfo.setFromCustomerCode(erpStoreReturnInfo.getKey().getSd14Frcd());
    	returnInfo.setFromCornerCode(erpStoreReturnInfo.getKey().getSd14Rfco());
    	returnInfo.setRfidYn(erpStoreReturnInfo.getSd14Rfyn());
    	returnInfo.setReturnTitle(erpStoreReturnInfo.getSd14Note());
    	returnInfo.setToCustomerCode(erpStoreReturnInfo.getSd14Tocd());
    	returnInfo.setToCornerCode(erpStoreReturnInfo.getSd14Toco());
    	returnInfo.setReturnConfirmDate(transformat.parse(erpStoreReturnInfo.getSd14Imdt()));
    	
    	//transient
    	returnInfo.setSku(erpStoreReturnInfo.getKey().getSd14Styl()+ erpStoreReturnInfo.getKey().getSd14Stcd());
    	returnInfo.setErpOrderKey(erpSd14Key);
    	returnInfo.setOrderAmount(erpStoreReturnInfo.getSd14Jsqt());
    	/*
    	returnSubInfo.setReturnStyle(erpStoreReturnInfo.getKey().getSd14Styl());
    	returnSubInfo.setReturnColor(erpStoreReturnInfo.getKey().getSd14Stcd().substring(0, 3));
    	returnSubInfo.setReturnSize(erpStoreReturnInfo.getKey().getSd14Stcd().substring(3));
    	returnSubInfo.setRfidYn(erpStoreReturnInfo.getSd14Rfyn());
    	returnSubInfo.setOrderAmount(erpStoreReturnInfo.getSd14Jsqt());
    	returnSubInfo.setErpSd14Key(erpSd14Key);*/
    	
    	
    	/*
    	/**
    	 * ERP 출고예정정보에 RFID 부착 스타일인지 확인하기 위한 처리
    	 
    	List<BartagMaster> bartagList = bartagMasterRepository.findByStyleAndColorAndSize(erpStoreSchedule.getKey().getLd02Styl(), erpStoreSchedule.getLd02It06(), erpStoreSchedule.getLd02It07());

    	storeSchedule.setRfidYn(bartagList.size() > 0 ? "Y" : "N");

    	log.info("Converting (" + erpStoreSchedule.toString() + ") into (" + storeSchedule.toString() + ")");
*/
        return returnInfo;
    }

	
}

