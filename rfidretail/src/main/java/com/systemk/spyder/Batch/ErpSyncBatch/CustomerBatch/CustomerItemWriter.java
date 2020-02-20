package com.systemk.spyder.Batch.ErpSyncBatch.CustomerBatch;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Service.CompanyService;
import com.systemk.spyder.Util.StringUtil;

public class CustomerItemWriter implements ItemWriter<CompanyInfo> {

	private static final Logger log = LoggerFactory.getLogger(CustomerItemWriter.class);
	
	private CompanyInfoRepository companyInfoRepository;
	
	private CompanyService companyService;
	
	public CustomerItemWriter(CompanyInfoRepository companyInfoRepository,
							  CompanyService companyService){
		this.companyInfoRepository = companyInfoRepository;
		this.companyService = companyService;
	}
	
	@Override
	public void write(List<? extends CompanyInfo> companyList) throws Exception {
		
		for(CompanyInfo company : companyList){
			// 등록
			if(company.getErpStat().equals("C")){
				
				CompanyInfo topCompany = companyInfoRepository.findTopByTypeOrderByCodeDesc(company.getType());
				int codeNum = 0;
				if(topCompany != null){
	    			codeNum = Integer.parseInt(topCompany.getCode());
	    			codeNum ++;
	    		} else {
	    			if(company.getType().equals("4")){
	    	    		codeNum = 100;
	    	    	} else if(company.getType().equals("5") || company.getType().equals("6")){
	    	    		codeNum = 500;
	    	    	} else if(company.getType().equals("3")){
	    	    		codeNum = 200;
	    	    	}
	    		}
				
		    	company.setCode(StringUtil.convertCipher("3", codeNum));
				companyInfoRepository.save(company);
				
			// 수정
			} else if(company.getErpStat().equals("U")){
				CompanyInfo tempCompanyInfo = companyInfoRepository.findByCustomerCode(company.getCustomerCode());
				company.setCompanySeq(tempCompanyInfo.getCompanySeq());
				company.setCode(tempCompanyInfo.getCode());
				company.setUpdDate(new Date());
				companyInfoRepository.save(company);
				
			// 삭제
			} else {
				CompanyInfo tempCompanyInfo = companyInfoRepository.findByCustomerCode(company.getCustomerCode());
				tempCompanyInfo.setUseYn("N");
				tempCompanyInfo.setUpdDate(new Date());
				companyInfoRepository.save(tempCompanyInfo);
			}
		}
		
		companyInfoRepository.flush();
		companyService.saveAll();
		
		log.info("거래선 마스터 배치 종료");
		
	}
}
