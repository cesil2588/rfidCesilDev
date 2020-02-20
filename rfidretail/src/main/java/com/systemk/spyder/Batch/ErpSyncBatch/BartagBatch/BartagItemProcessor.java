package com.systemk.spyder.Batch.ErpSyncBatch.BartagBatch;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.External.RfidAc18If;
import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.ProductMaster;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.ProductMasterRepository;

@Component
public class BartagItemProcessor implements ItemProcessor<RfidAc18If, BartagMaster> {

	private static final Logger log = LoggerFactory.getLogger(BartagItemProcessor.class);

	private ProductMasterRepository productMasterRepository;

	private CompanyInfoRepository companyInfoRepository;

	public BartagItemProcessor(ProductMasterRepository productMasterRepository,
							   CompanyInfoRepository companyInfoRepository){
		this.productMasterRepository = productMasterRepository;
		this.companyInfoRepository = companyInfoRepository;
	}

    @Override
    public BartagMaster process(RfidAc18If erpBartag) throws Exception {

    	BartagMaster bartagMaster = new BartagMaster();

    	bartagMaster.setCreateDate(erpBartag.getKey().getAc18Crdt());

    	bartagMaster.setSeq(erpBartag.getKey().getAc18Crsq().longValue());
    	bartagMaster.setLineSeq(erpBartag.getKey().getAc18Crno().longValue());
    	bartagMaster.setStyle(erpBartag.getAc18Styl());
    	bartagMaster.setColor(erpBartag.getAc18It06());
    	bartagMaster.setSize(erpBartag.getAc18It07());

    	ProductMaster productMaster = productMasterRepository.findByStyleAndColorAndSizeAndStatNot(erpBartag.getAc18Styl(), erpBartag.getAc18It06(), erpBartag.getAc18It07(), "D");
    	bartagMaster.setProductYy(productMaster.getProductYy());
    	bartagMaster.setProductSeason(productMaster.getProductSeason());
    	bartagMaster.setProductRfidYySeason(productMaster.getProductRfidYySeason());
    	bartagMaster.setOrderDegree(erpBartag.getAc18Jjch());
    	bartagMaster.setAnnotherStyle(erpBartag.getAc18Stcd());
    	bartagMaster.setAmount(erpBartag.getAc18Btqt().longValue());
    	bartagMaster.setTotalAmount(erpBartag.getAc18Btqt().longValue());

    	CompanyInfo productionCompanyInfo = companyInfoRepository.findByCustomerCode(erpBartag.getAc18Prod());
    	bartagMaster.setProductionCompanyInfo(productionCompanyInfo);

    	bartagMaster.setErpKey(erpBartag.getAc18Erpk());
    	bartagMaster.setData(erpBartag.getAc18Data());
    	bartagMaster.setStat("1");
    	bartagMaster.setResult("N");
    	bartagMaster.setGenerateSeqYn("N");
    	bartagMaster.setRegDate(new Date());
    	bartagMaster.setReissueRequestYn("N");
    	bartagMaster.setStartFlag("E");

    	// 태그발급업체 우선은 한곳만 가져올수 있도록 처리
    	CompanyInfo publishCompanyInfo = companyInfoRepository.findTopByRoleInfoRole("publish");

    	bartagMaster.setPublishCompanyInfo(publishCompanyInfo);

    	bartagMaster.setDetailProductionCompanyName(erpBartag.getAc18Jgrs());

    	/*
    	if(erpBartag.getAc18Jgrs() != null || !erpBartag.getAc18Jgrs().equals("")){

    		// 세부생산업체 가져오기
        	CompanyInfo detailProductionCompanyInfo = companyInfoRepository.findByNameContaining(erpBartag.getAc18Jgrs().trim());

        	if(detailProductionCompanyInfo != null){
        		bartagMaster.setDetailProductionCompanyInfo(detailProductionCompanyInfo);
        	} else {
        		// 세부생산업체 작업
        		CompanyInfo tempCompanyInfo = companyInfoRepository.findTopByCustomerCodeIsNullOrderByCodeDesc();
        		int codeNum = Integer.parseInt(tempCompanyInfo.getCode());
        		codeNum ++;

        		detailProductionCompanyInfo = new CompanyInfo();
        		detailProductionCompanyInfo.setErpYn("N");
        		detailProductionCompanyInfo.setName(erpBartag.getAc18Jgrs().trim());
        		detailProductionCompanyInfo.setRole("production");
        		detailProductionCompanyInfo.setType("3");
        		detailProductionCompanyInfo.setTelNo("-");
        		detailProductionCompanyInfo.setUseYn("Y");
        		detailProductionCompanyInfo.setRegUserSeq(Long.valueOf(1));
        		detailProductionCompanyInfo.setRegDate(new Date());
        		detailProductionCompanyInfo.setCode(StringUtil.convertCipher("3", codeNum));

        		bartagMaster.setDetailProductionCompanyInfo(companyInfoRepository.save(detailProductionCompanyInfo));
        	}
    	}
    	*/

    	log.info("Converting (" + erpBartag.toString() + ") into (" + bartagMaster.toString() + ")");

        return bartagMaster;
    }
}

