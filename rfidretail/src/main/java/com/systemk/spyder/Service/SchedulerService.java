package com.systemk.spyder.Service;

import com.systemk.spyder.Entity.Main.BatchJob;

public interface SchedulerService {

	public void triggerScheduler(BatchJob batchJob);
	
	public void delScheduler(BatchJob batchJob);
	
	public void addScheduler(BatchJob batchJob);
	
	public void updateScheduler(BatchJob batchJob);
	
	public void restartScheduler(BatchJob batchJob);
	
	public void stopScheduler(BatchJob batchJob);
}

