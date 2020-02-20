package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreInventoryBatch;

import com.systemk.spyder.Entity.External.RfidMd14If;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.InventoryScheduleHeader;
import com.systemk.spyder.Entity.Main.ProductMaster;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.ProductMasterRepository;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class StoreInventoryItemProcessor implements ItemProcessor<RfidMd14If, InventoryScheduleHeader> {

	private static final Logger log = LoggerFactory.getLogger(StoreInventoryItemProcessor.class);

	private CompanyInfoRepository companyInfoRepository;

	private ProductMasterRepository productMasterRepository;

	private UserInfoRepository userInfoRepository;

	public StoreInventoryItemProcessor(CompanyInfoRepository companyInfoRepository,
									   ProductMasterRepository productMasterRepository,
									   UserInfoRepository userInfoRepository){
		this.companyInfoRepository = companyInfoRepository;
		this.productMasterRepository = productMasterRepository;
		this.userInfoRepository = userInfoRepository;
	}

    @Override
    public InventoryScheduleHeader process(RfidMd14If rfidMd14If) throws Exception {

		// ERP I/F 정보 각 정보별로 구분
		String style = rfidMd14If.getKey().getMd14Styl();
		String color = rfidMd14If.getKey().getMd14Stcd().substring(0, 3);
		String size = rfidMd14If.getKey().getMd14Stcd().substring(3, 6);
		String customerCode = rfidMd14If.getKey().getMd14Mjcd();
		String cornerCode = rfidMd14If.getKey().getMd14Corn();

		// 업체 정보 가져오기
		CompanyInfo companyInfo = companyInfoRepository.findByCustomerCodeAndCornerCode(customerCode,
																						cornerCode);

		// 제품 정보 가져오기
		ProductMaster productMaster = productMasterRepository.findByStyleAndColorAndSize(style, color, size);

		// 관리자 정보 가져오기
		UserInfo userInfo = userInfoRepository.findOne(1L);

		// 생성자로 리턴
        return new InventoryScheduleHeader(rfidMd14If, productMaster, companyInfo, userInfo);
    }
}

