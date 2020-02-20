package com.systemk.spyder.Service.Impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Entity.Main.BatchTrigger;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.BatchTriggerRepository;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import com.systemk.spyder.Repository.Main.Specification.BatchTriggerSpecification;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.BatchTriggerService;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.SecurityUtil;

@Service
public class BatchTriggerServiceImpl implements BatchTriggerService{
	
	@Autowired
	private BatchTriggerRepository batchTriggerRepository;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Transactional
	@Override
	public boolean save(String detail, String type, UserInfo userInfo) throws Exception{
		
		boolean success = false;
		
		BatchTrigger batchTrigger = new BatchTrigger();
		
		batchTrigger.setCheckYn("N");
		batchTrigger.setScheduleDate(new Date());
		batchTrigger.setStat("1");
		batchTrigger.setType(type);
		batchTrigger.setStatus("SCHEDULE");
		batchTrigger.setExplanatory(detail);
		batchTrigger.setRegUserInfo(userInfo);
		
		batchTriggerRepository.save(batchTrigger);
		
		success = true;
		
		return success;
	}

	@Transactional(readOnly = true)
	@Override
	public BatchTrigger findBatchSchedule(String detail, String type, String stat) throws Exception {
		return batchTriggerRepository.findByExplanatoryAndTypeAndStat(detail, type, stat);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<BatchTrigger> findAll(String startDate, String endDate, Long userSeq, String status, String type, Pageable pageable) throws Exception {
		
		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);
		
		Specifications<BatchTrigger> spec = Specifications.where(BatchTriggerSpecification.scheduleDateBetween(start, end));
		
		if(!status.equals("all")){
			spec = spec.and(BatchTriggerSpecification.statusEqual(status));
		}
		
		if(!type.equals("all")){
			spec = spec.and(BatchTriggerSpecification.typeEqual(type));
		}
		
		if(userSeq != 0) {
			spec = spec.and(BatchTriggerSpecification.userSeqEqual(userSeq));
		}
		
		return batchTriggerRepository.findAll(spec, pageable);
	}
}
