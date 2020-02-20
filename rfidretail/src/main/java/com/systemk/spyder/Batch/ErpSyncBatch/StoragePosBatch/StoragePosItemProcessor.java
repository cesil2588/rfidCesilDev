package com.systemk.spyder.Batch.ErpSyncBatch.StoragePosBatch;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.systemk.spyder.Entity.External.RfidZa40If;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;


public class StoragePosItemProcessor implements ItemProcessor<RfidZa40If, UserInfo> {

	private static final Logger log = LoggerFactory.getLogger(StoragePosItemProcessor.class);

	private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	private CompanyInfoRepository companyInfoRepository;

	public StoragePosItemProcessor(CompanyInfoRepository companyInfoRepository){
		this.companyInfoRepository = companyInfoRepository;
	}

    @Override
    public UserInfo process(RfidZa40If erpStorePos) throws Exception {

    	UserInfo userInfo = new UserInfo();

    	CompanyInfo companyInfo = companyInfoRepository.findByCustomerCodeAndCornerCode(erpStorePos.getZa40Gras(), erpStorePos.getZa40Corn());

    	//erp에서 넘겨준 customer_code, corner_code값이 존재하는 회사정보가 있을때만 수행
    	if(companyInfo != null) {

    		companyInfo.setErpStat(erpStorePos.getZa40Stat());
    		userInfo.setUserId(erpStorePos.getKey().getZa40Usid());
    		userInfo.setPassword(bCryptPasswordEncoder.encode("1234"));	//모든 POS사용 ID에 동일 비번으로 설정하기로 협의
    		userInfo.setUseYn(erpStorePos.getZa40Slyn());
			userInfo.setCheckYn("Y");
			userInfo.setUpdDate(new Date());
			userInfo.setCompanyInfo(companyInfo);

    	}

    	log.info("Converting (" + erpStorePos.toString() + ") into (" + userInfo.toString() + ")");

        return userInfo;
}


}
