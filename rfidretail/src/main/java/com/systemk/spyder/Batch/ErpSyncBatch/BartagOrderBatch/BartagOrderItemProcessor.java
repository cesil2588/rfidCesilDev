package com.systemk.spyder.Batch.ErpSyncBatch.BartagOrderBatch;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.External.RfidAa06If;
import com.systemk.spyder.Entity.Main.BartagOrder;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.ProductMaster;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.ProductMasterRepository;

@Component
public class BartagOrderItemProcessor implements ItemProcessor<RfidAa06If, BartagOrder> {

	private static final Logger log = LoggerFactory.getLogger(BartagOrderItemProcessor.class);

	private CompanyInfoRepository companyInfoRepository;

	private ProductMasterRepository productMasterRepository;

	public BartagOrderItemProcessor(CompanyInfoRepository companyInfoRepository, ProductMasterRepository productMasterRepository){
		this.companyInfoRepository = companyInfoRepository;
		this.productMasterRepository = productMasterRepository;
	}

    @Override
    public BartagOrder process(RfidAa06If erpBartagOrder) throws Exception {

    	BartagOrder bartagOrder = new BartagOrder();
    	bartagOrder.setCreateDate(erpBartagOrder.getKey().getAa06Crdt());
    	bartagOrder.setCreateSeq(erpBartagOrder.getKey().getAa06Crsq().longValue());
    	bartagOrder.setCreateNo(erpBartagOrder.getKey().getAa06Crno().longValue());

    	ProductMaster productMaster = productMasterRepository.findByStyleAndColorAndSizeAndStatNot(erpBartagOrder.getAa06Styl(), erpBartagOrder.getAa06It06(), erpBartagOrder.getAa06It07(), "D");

    	bartagOrder.setProductYy(productMaster.getProductYy());
    	bartagOrder.setProductSeason(productMaster.getProductSeason());
    	bartagOrder.setErpKey(productMaster.getErpKey());

    	bartagOrder.setStyle(erpBartagOrder.getAa06Styl());
    	bartagOrder.setColor(erpBartagOrder.getAa06It06());
    	bartagOrder.setSize(erpBartagOrder.getAa06It07());
    	bartagOrder.setAnotherStyle(erpBartagOrder.getAa06Stcd());
    	bartagOrder.setOrderAmount(erpBartagOrder.getAa06Cfqt().longValue());
    	bartagOrder.setOrderDegree(erpBartagOrder.getAa06Jjch());
    	bartagOrder.setOrderDate(erpBartagOrder.getAa06Jmdt());
    	bartagOrder.setOrderTime(erpBartagOrder.getAa06Time());
    	bartagOrder.setOrderSeq(erpBartagOrder.getAa06Jmno().longValue());
    	bartagOrder.setCompleteAmount(Long.valueOf(0));

    	CompanyInfo productionCompanyInfo = companyInfoRepository.findByCustomerCode(erpBartagOrder.getAa06Prod());
    	bartagOrder.setProductionCompanyInfo(productionCompanyInfo);

    	bartagOrder.setDetailProductionCompanyName(erpBartagOrder.getAa06Jgrs());

    	bartagOrder.setErpStat(erpBartagOrder.getAa06Stat());
    	bartagOrder.setRegDate(new Date());

    	bartagOrder.setStat("1");
    	bartagOrder.setAdditionAmount(Long.valueOf(0));
    	bartagOrder.setAdditionOrderDegree("0");
    	bartagOrder.setTempAdditionAmount(Long.valueOf(0));
    	bartagOrder.setCompleteYn("N");
    	bartagOrder.setNonCheckAdditionAmount(Long.valueOf(0));
    	bartagOrder.setNonCheckCompleteAmount(Long.valueOf(0));

    	return bartagOrder;
    }
}

