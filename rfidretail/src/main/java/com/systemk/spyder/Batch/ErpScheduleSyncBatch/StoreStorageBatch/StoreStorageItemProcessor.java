package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreStorageBatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.External.RfidLe10If;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.ProductMaster;
import com.systemk.spyder.Entity.Main.ReleaseScheduleDetailLog;
import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.ProductMasterRepository;
import com.systemk.spyder.Repository.Main.ReleaseScheduleLogRepository;

@Component
public class StoreStorageItemProcessor implements ItemProcessor<RfidLe10If, ReleaseScheduleLog> {

	private static final Logger log = LoggerFactory.getLogger(StoreStorageItemProcessor.class);

	private CompanyInfoRepository companyInfoRepository;

	private ReleaseScheduleLogRepository releaseScheduleLogRepository;

	private ProductMasterRepository productMasterRepository;

	public StoreStorageItemProcessor(CompanyInfoRepository companyInfoRepository,
									 ReleaseScheduleLogRepository releaseScheduleLogRepository,
									 ProductMasterRepository productMasterRepository){
		this.companyInfoRepository = companyInfoRepository;
		this.releaseScheduleLogRepository = releaseScheduleLogRepository;
		this.productMasterRepository = productMasterRepository;
	}

    @Override
    public ReleaseScheduleLog process(RfidLe10If storeStorage) throws Exception {

    	ReleaseScheduleLog releaseSchedule = releaseScheduleLogRepository.findByBoxInfoBarcode(storeStorage.getKey().getLe10Bxno());

    	String style = storeStorage.getKey().getLe10Styl();
    	String color = storeStorage.getKey().getLe10Stcd().substring(0, 3);
    	String size = storeStorage.getKey().getLe10Stcd().substring(3, 6);

    	// 출고예정정보에 해당 바코드 값이 없을 경우 아래 로직 수행
    	if(releaseSchedule == null) {

    		// 관리자 계정 셋팅
    		UserInfo userInfo = new UserInfo();
			userInfo.setUserSeq(1L);

    		// TODO 현재는 물류센터 하드코딩, 추가 생성된 물류센터가 있다면 동작으로 변경
    		CompanyInfo startCompanyInfo = companyInfoRepository.findByCustomerCodeAndCornerCode("100000", "0");
        	CompanyInfo endCompanyInfo = companyInfoRepository.findByCustomerCodeAndCornerCode(storeStorage.getKey().getLe10Gras(), storeStorage.getKey().getLe10Grco());

    		// erpKey 조회
    		ProductMaster productMaster = productMasterRepository.findByStyleAndColorAndSize(style, color, size);

    		releaseSchedule = new ReleaseScheduleLog(storeStorage, new BoxInfo(storeStorage.getKey().getLe10Bxno(), startCompanyInfo, endCompanyInfo, userInfo), userInfo, productMaster);

    	} else {

    		// 컨베이어 통과 제품 > ERP 출고 일자 업데이트
    		if(releaseSchedule.getMappingYn().equals("Y")) {
    			releaseSchedule.setErpReleaseDate(storeStorage.getKey().getLe10Chdt());
				releaseSchedule.setErpReleaseSeq(storeStorage.getKey().getLe10Chsq().longValue());

        	// 이전 배치로 작업된 내역이 있다면 스타일 추가
    		} else {

    			// erpKey 조회
        		ProductMaster productMaster = productMasterRepository.findByStyleAndColorAndSize(style, color, size);

    			releaseSchedule.getReleaseScheduleDetailLog().add(new ReleaseScheduleDetailLog(storeStorage, productMaster));
    		}
    	}


    	log.info("Converting (" + storeStorage.toString() + ") into (" + releaseSchedule.toString() + ")");

        return releaseSchedule;
    }
}

