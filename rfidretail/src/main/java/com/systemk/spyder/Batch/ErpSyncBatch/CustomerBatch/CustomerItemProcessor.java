package com.systemk.spyder.Batch.ErpSyncBatch.CustomerBatch;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.External.RfidZa11If;
import com.systemk.spyder.Entity.Main.CodeInfo;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.ParentCodeInfo;
import com.systemk.spyder.Entity.Main.RoleInfo;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.ParentCodeInfoRepository;
import com.systemk.spyder.Repository.Main.RoleInfoRepository;
import com.systemk.spyder.Util.StringUtil;

@Component
public class CustomerItemProcessor implements ItemProcessor<RfidZa11If, CompanyInfo> {

	private static final Logger log = LoggerFactory.getLogger(CustomerItemProcessor.class);

	private ParentCodeInfoRepository parentCodeInfoRepository;

	private RoleInfoRepository roleInfoRepository;

	public CustomerItemProcessor(CompanyInfoRepository companyInfoRepository,
								 ParentCodeInfoRepository parentCodeInfoRepository,
								 RoleInfoRepository roleInfoRepository){
		this.parentCodeInfoRepository = parentCodeInfoRepository;
		this.roleInfoRepository = roleInfoRepository;
	}

    @Override
    public CompanyInfo process(RfidZa11If erpCustomer) throws Exception {

    	CompanyInfo companyInfo = new CompanyInfo();
    	int codeNum = 0;

    	companyInfo.setCustomerCode(erpCustomer.getZa11Gras());
    	companyInfo.setCornerCode(erpCustomer.getZa11Corn());
    	companyInfo.setName(erpCustomer.getZa11Grnm());
    	companyInfo.setCornerName(erpCustomer.getZa11Cnam());

    	ParentCodeInfo typeParentCodeInfo = parentCodeInfoRepository.findByCodeValue("10002");

    	for(CodeInfo codeInfo : typeParentCodeInfo.getCodeInfo()){
    		if(codeInfo.getErpCodeValue().equals(erpCustomer.getZa11Grac())){
    			companyInfo.setType(codeInfo.getCodeValue());
    			break;
    		}
    	}

    	ParentCodeInfo roleParentCodeInfo = parentCodeInfoRepository.findByCodeValue("10003");

    	for(CodeInfo codeInfo : roleParentCodeInfo.getCodeInfo()){
    		if(codeInfo.getErpCodeValue().equals(erpCustomer.getZa11Grac())){

    			RoleInfo roleInfo = roleInfoRepository.findOne(codeInfo.getCodeValue());
    			companyInfo.setRoleInfo(roleInfo);
    			break;
    		}
    	}

    	/*
    	if(erpCustomer.getZa11Stat().equals("C")){
    		CompanyInfo topCompany = companyInfoRepository.findTopByTypeOrderByCodeDesc(type);
    		if(topCompany != null){
    			codeNum = Integer.parseInt(topCompany.getCode());
    			codeNum ++;
    		} else {
    			if(erpCustomer.getZa11Grac().equals("10")){
    	    		codeNum = 100;
    	    	} else if(erpCustomer.getZa11Grac().equals("20") || erpCustomer.getZa11Grac().equals("30")){
    	    		codeNum = 500;
    	    	} else if(erpCustomer.getZa11Grac().equals("40")){
    	    		codeNum = 200;
    	    	}
    		}
    	}
    	*/

    	companyInfo.setCode(StringUtil.convertCipher("3", codeNum));
    	companyInfo.setCloseYn(erpCustomer.getZa11Grst());
    	companyInfo.setTelNo(erpCustomer.getZa11Teln());
    	companyInfo.setAddress1(erpCustomer.getZa11Adr1());
    	companyInfo.setAddress2(erpCustomer.getZa11Adr2());
    	companyInfo.setCreateDate(erpCustomer.getKey().getZa11Crdt());
    	companyInfo.setCreateNo(erpCustomer.getKey().getZa11Crno().longValue());
    	companyInfo.setErpStat(erpCustomer.getZa11Stat());
    	companyInfo.setRegUserSeq(Long.valueOf(1));
    	companyInfo.setRegDate(new Date());
    	companyInfo.setUseYn("Y");
    	companyInfo.setErpYn("Y");

    	log.info("Converting (" + erpCustomer.toString() + ") into (" + companyInfo.toString() + ")");

        return companyInfo;
    }
}
