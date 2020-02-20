package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreReturnScheduleBatch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.ErpStoreReturnSchedule;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.BoxInfoRepository;
import com.systemk.spyder.Repository.Main.ErpStoreReturnScheduleRepository;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import com.systemk.spyder.Service.StorageScheduleLogService;

public class StoreReturnScheduleItemWriter implements ItemWriter<ErpStoreReturnSchedule> {
	
	private static final Logger log = LoggerFactory.getLogger(StoreReturnScheduleItemWriter.class);
	
	private ErpStoreReturnScheduleRepository erpStoreReturnScheduleRepository;
	
	private UserInfoRepository userInfoRepository;
		
	private BoxInfoRepository boxInfoRepository;
		
	private StorageScheduleLogService storageScheduleLogService;
	
	public StoreReturnScheduleItemWriter(ErpStoreReturnScheduleRepository erpStoreReturnScheduleRepository, 
							   BoxInfoRepository boxInfoRepository,
							   StorageScheduleLogService storageScheduleLogService,
							   UserInfoRepository userInfoRepository){
		this.erpStoreReturnScheduleRepository = erpStoreReturnScheduleRepository;
		this.boxInfoRepository = boxInfoRepository;
		this.storageScheduleLogService = storageScheduleLogService;
		this.userInfoRepository = userInfoRepository;
	}
	
	
	@Override
	public void write(List<? extends ErpStoreReturnSchedule> erpStoreReturnList) throws Exception {
		
		List<BoxInfo> boxInfoList = new ArrayList<BoxInfo>();
		UserInfo userInfo = userInfoRepository.findByUserId("admin");
		String returnType = "";
		for(ErpStoreReturnSchedule erpStoreReturn : erpStoreReturnList){

			if(!boxInfoRepository.existsByBarcode(erpStoreReturn.getReturnOrderNo())){		
				
				BoxInfo boxInfo = new BoxInfo();
				boxInfo.setBarcode(erpStoreReturn.getReturnOrderNo());
				boxInfo.setBoxNum(erpStoreReturn.getReturnOrderLiNo());
				boxInfo.setType("03");
				boxInfo.setCreateDate(new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
				boxInfo.setStartCompanyInfo(erpStoreReturn.getStartCompanyInfo());
				boxInfo.setEndCompanyInfo(erpStoreReturn.getEndCompanyInfo());
				boxInfo.setReturnYn("Y");
				boxInfo.setStat("1");
				boxInfo.setRegDate(new Date());
				boxInfo.setArrivalDate(new SimpleDateFormat("yyyyMMdd").parse(erpStoreReturn.getOrderRegDate()));
				returnType = erpStoreReturn.getReturnType();
				boxInfo.setRegUserInfo(userInfo);
				
				boxInfoList.add(boxInfo);
				boxInfoRepository.save(boxInfoList);
				boxInfoRepository.flush();
			}
				erpStoreReturnScheduleRepository.save(erpStoreReturnList);
				erpStoreReturnScheduleRepository.flush();

		}

			storageScheduleLogService.save(boxInfoList, userInfo, 1L, "10-R", returnType);
	
			log.info("ERP RFID 반품예정정보 배치 종료");
	}
}
