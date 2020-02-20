package com.systemk.spyder.Batch.ErpSyncBatch.StoragePosBatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.UserInfoRepository;


public class StoragePosItemWriter implements ItemWriter<UserInfo>{
	
	private static final Logger log = LoggerFactory.getLogger(StoragePosItemWriter.class);

	private UserInfoRepository userInfoRepository;

	public StoragePosItemWriter(UserInfoRepository userInfoRepository){
		this.userInfoRepository = userInfoRepository;
	}

	@Override
	public void write(List<? extends UserInfo> userList) throws Exception {
		
		List<UserInfo> inUserList= new ArrayList<UserInfo>();
		
		if(userList.size()>0) {
			
			for(UserInfo user : userList) {
				UserInfo tempUser = userInfoRepository.findByUserId(user.getUserId());
				
				//erp stat값 'C'이면 신규 생성, 또는 강제 업데이트
				if((user.getCompanyInfo().getErpStat()).equals("C")) {
					
					if(tempUser != null) {
						user.setUserSeq(tempUser.getUserSeq());
						user.setRegDate(tempUser.getRegDate());
						user.setUserEmailInfo(tempUser.getUserEmailInfo());
					}	
					user.setRegDate(new Date());
					inUserList.add(user);
	
				//erp stat가 'U'이면 아이디 존재여부 확인 후 존재하면 업데이트
				}else if(user.getCompanyInfo().getErpStat().equals("U") && tempUser != null) {	
					
						user.setUserSeq(tempUser.getUserSeq());
						user.setRegDate(tempUser.getRegDate());
						user.setUserEmailInfo(tempUser.getUserEmailInfo());
						inUserList.add(user);
		
				//erp stat가 'D'이면 아이디 존재여부 확인후 해당 아이디를 사용안함 처리
				}else if(user.getCompanyInfo().getErpStat().equals("D") && tempUser != null) {
					
						user.setUserSeq(tempUser.getUserSeq());
						user.setRegDate(tempUser.getRegDate());
						user.setUserEmailInfo(tempUser.getUserEmailInfo());
						user.setUseYn("N");
						inUserList.add(user);
				
				}else {
					//해당 customer_code, corner_code에 해당하는 company_info가 없는 경우
					//stat가 'U', 'D'인데 사용자 정보가 없는 경우 rfid_za40_if 테이블 업데이트 하지 않기 위해 아이디에 미적용 의미로 세팅
					user.setUserId("noAdj");
					inUserList.add(user);
				}
	
			}	
			
		}
		
		userInfoRepository.save(inUserList);
		userInfoRepository.flush();
		log.info("매장 인터페이스 배치 종료");
		
	}


}
