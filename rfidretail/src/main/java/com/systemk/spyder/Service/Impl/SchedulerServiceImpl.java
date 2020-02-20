package com.systemk.spyder.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systemk.spyder.Entity.Main.BatchJob;
import com.systemk.spyder.Scheduler.JobSchedulerModelGenerator;
import com.systemk.spyder.Scheduler.QuartzScheduler;
import com.systemk.spyder.Service.SchedulerService;

@Service
public class SchedulerServiceImpl implements SchedulerService {
	
	@Autowired
	private QuartzScheduler quartzScheduler;
	
	@Override
	public void triggerScheduler(BatchJob batchJob){
		
		String jobName = batchJob.getCommand();
		
		try {
			quartzScheduler.schedulerTrigger(jobName, JobSchedulerModelGenerator.GROUP_NAME);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	@Override
	public void delScheduler(BatchJob batchJob){
		
		try {
			
			String jobName = batchJob.getCommand();
			
			quartzScheduler.schedulerDelete(jobName, JobSchedulerModelGenerator.GROUP_NAME);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void addScheduler(BatchJob batchJob){
		try {
			quartzScheduler.schedulerAdd(batchJob.getBatchJobSeq());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	@Override
	public void updateScheduler(BatchJob batchJob){
		try {
			String jobName = batchJob.getCommand();
			
			quartzScheduler.schedulerDelete(jobName, JobSchedulerModelGenerator.GROUP_NAME);
			quartzScheduler.schedulerAdd(batchJob.getBatchJobSeq());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void restartScheduler(BatchJob batchJob){
		
		String jobName = batchJob.getCommand();
		
		try {
			quartzScheduler.schedulerRestart(jobName, JobSchedulerModelGenerator.GROUP_NAME);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void stopScheduler(BatchJob batchJob){
		
		String jobName = batchJob.getCommand() + ":" + batchJob.getBatchJobSeq();
		
		try {
			quartzScheduler.schedulerStop(jobName, JobSchedulerModelGenerator.GROUP_NAME);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
