package com.systemk.spyder.Service;

import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Entity.Main.BatchJob;
import com.systemk.spyder.Entity.Main.BatchLog;

public interface BatchLogService {

	public void save(JobExecution jobExecution);
	
	public Page<BatchLog> findAll(String startTime, String endTime, String command, String status, String search, String option, Pageable pageable) throws Exception;
	
	public List<BatchJob> selectAllJobName() throws Exception;
	
}
