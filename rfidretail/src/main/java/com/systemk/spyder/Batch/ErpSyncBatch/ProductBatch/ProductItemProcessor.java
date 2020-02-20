package com.systemk.spyder.Batch.ErpSyncBatch.ProductBatch;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.External.RfidAc16If;
import com.systemk.spyder.Entity.Main.CodeInfo;
import com.systemk.spyder.Entity.Main.ParentCodeInfo;
import com.systemk.spyder.Entity.Main.ProductMaster;
import com.systemk.spyder.Repository.Main.ParentCodeInfoRepository;

@Component
public class ProductItemProcessor implements ItemProcessor<RfidAc16If, ProductMaster> {

	private static final Logger log = LoggerFactory.getLogger(ProductItemProcessor.class);

	private ParentCodeInfoRepository parentCodeInfoRepository;

	public ProductItemProcessor(ParentCodeInfoRepository parentCodeInfoRepository){
		this.parentCodeInfoRepository = parentCodeInfoRepository;
	}

    @Override
    public ProductMaster process(RfidAc16If erpProduct) throws Exception {

    	ProductMaster productMaster = new ProductMaster();

    	productMaster.setErpKey(erpProduct.getAc16Erpk());
    	productMaster.setStyle(erpProduct.getAc16Styl());
    	productMaster.setAnnotherStyle(erpProduct.getAc16Stcd());
    	productMaster.setProductYy(erpProduct.getAc16Jpyy());
    	productMaster.setProductSeason(erpProduct.getAc16Jpss());

    	ParentCodeInfo seasonCodeInfo = parentCodeInfoRepository.findByCodeValue("10005");

    	String productRfidYySeason = "";

    	for(CodeInfo codeInfo : seasonCodeInfo.getCodeInfo()){
    		if(codeInfo.getErpCodeValue().equals(erpProduct.getAc16Jpss())){
    			productRfidYySeason = erpProduct.getAc16Jpyy().substring(2, 4) + codeInfo.getCodeValue();
    			productMaster.setProductRfidYySeason(productRfidYySeason);
    			break;
    		}
    	}

    	productMaster.setColor(erpProduct.getAc16It06());
    	productMaster.setSize(erpProduct.getAc16It07());
    	productMaster.setStat(erpProduct.getAc16Stat());
    	productMaster.setRegDate(new Date());
    	productMaster.setCreateDate(erpProduct.getKey().getAc16Crdt());
    	productMaster.setCreateNo(erpProduct.getKey().getAc16Crno().longValue());
    	productMaster.setCreateSeq(erpProduct.getKey().getAc16Crsq().longValue());

    	log.info("Converting (" + erpProduct.toString() + ") into (" + productMaster.toString() + ")");

        return productMaster;
    }
}

