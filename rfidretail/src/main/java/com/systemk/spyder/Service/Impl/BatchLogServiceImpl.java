package com.systemk.spyder.Service.Impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Entity.Main.BatchJob;
import com.systemk.spyder.Entity.Main.BatchLog;
import com.systemk.spyder.Entity.Main.BatchLogStep;
import com.systemk.spyder.Repository.Main.BatchJobRepository;
import com.systemk.spyder.Repository.Main.BatchLogRepository;
import com.systemk.spyder.Repository.Main.Specification.BatchLogSpecification;
import com.systemk.spyder.Service.BatchLogService;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.StringUtil;

@Service
public class BatchLogServiceImpl implements BatchLogService{
	
	@Autowired
	private BatchLogRepository batchLogRepository;
	
	@Autowired
	private BatchJobRepository batchJobRepository;

	@Transactional
	@Override
	public void save(JobExecution jobExecution) {
		
		BatchLog batchLog = new BatchLog();
		batchLog.setCreateTime(jobExecution.getCreateTime());
		batchLog.setStartTime(jobExecution.getStartTime());
		batchLog.setEndTime(jobExecution.getEndTime());
		batchLog.setStatus(jobExecution.getStatus().name());
		batchLog.setJobName(jobExecution.getJobInstance().getJobName());
		
		Set<BatchLogStep> batchLogStepList = new HashSet<BatchLogStep>();
		
		for(StepExecution step : jobExecution.getStepExecutions()){
			BatchLogStep batchLogStep = new BatchLogStep();
			batchLogStep.setName(step.getStepName());
			batchLogStep.setStatus(step.getStatus().name());
			batchLogStep.setExitStatus(step.getExitStatus().getExitCode());
			batchLogStep.setReadCount(step.getReadCount());
			batchLogStep.setFilterCount(step.getFilterCount());
			batchLogStep.setWriteCount(step.getWriteCount());
			batchLogStep.setReadSkipCount(step.getReadSkipCount());
			batchLogStep.setWriteSkipCount(step.getWriteSkipCount());
			batchLogStep.setProcessSkipCount(step.getProcessSkipCount());
			batchLogStep.setCommitCount(step.getCommitCount());
			batchLogStep.setRollbackCount(step.getRollbackCount());
			batchLogStep.setStartTime(step.getStartTime());
			batchLogStep.setEndTime(step.getEndTime());
			
			String errorMessage = "";
			for(Throwable throwData : step.getFailureExceptions()){
				errorMessage += StringUtil.getPrintStackTrace(throwData) + "\n";
			}
			
			batchLogStep.setErrorMessage(errorMessage);
			
			batchLogStepList.add(batchLogStep);
		}
		
		batchLog.setCheckYn("N");
		batchLog.setBatchLogStep(batchLogStepList);
		
		batchLogRepository.save(batchLog);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<BatchLog> findAll(String startTime, String endTime, String command, String status, String search, String option, Pageable pageable) throws Exception {

		Date startDate = CalendarUtil.convertStartDate(startTime);
		Date endDate = CalendarUtil.convertEndDate(endTime);
		
		Page<BatchLog> page = null;
		Specifications<BatchLog> spec = Specifications.where(BatchLogSpecification.createTimeBetween(startDate, endDate));
		
		if(!command.equals("")) {
			spec = spec.and(BatchLogSpecification.jobNameContain(command));
		}
		
		if(!status.equals("all") && !status.equals("")){
			spec = spec.and(BatchLogSpecification.statusContain(status));
		} 
		
		page = batchLogRepository.findAll(spec, pageable);
		
		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public List<BatchJob> selectAllJobName() throws Exception {
		return batchJobRepository.findAll();
	}

}
