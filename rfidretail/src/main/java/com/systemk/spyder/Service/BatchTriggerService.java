package com.systemk.spyder.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Entity.Main.BatchTrigger;
import com.systemk.spyder.Entity.Main.UserInfo;

public interface BatchTriggerService {

	public boolean save(String detail, String type, UserInfo userInfo) throws Exception;
	
	public BatchTrigger findBatchSchedule(String detail, String type, String stat) throws Exception;
	
	public Page<BatchTrigger> findAll(String startDate, String endDate, Long userSeq, String status, String type, Pageable pageable) throws Exception;
}
