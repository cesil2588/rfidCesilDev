package com.systemk.spyder.Service.Impl;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systemk.spyder.Service.BatchJobService;
import com.systemk.spyder.Service.BatchLogService;

@Service
public class BatchJobServiceImpl implements BatchJobService{

	@Autowired
    private JobLauncher jobLauncher;

	@Autowired
	private Job erpSyncJob;

	@Autowired
	private Job textUploadJob;

	@Autowired
	private Job inspectionJob;

	@Autowired
	private Job workJob;

	@Autowired
	private Job inventoryJob;

	@Autowired
	private Job erpScheduleSyncJob;

	@Autowired
	private Job dailyWorkJob;
	
	@Autowired
	private Job erpOrderJob;

	@Autowired
	private BatchLogService batchLogService;

	@Override
	public void jobAction(String command) throws Exception {

		JobParameters jobParameters = null;
		JobExecution jobExecution = null;

		switch(command) {

			case "erpSyncJob" :

				jobParameters = new JobParametersBuilder().addLong("currTime", System.currentTimeMillis()).toJobParameters();
				jobExecution = jobLauncher.run(erpSyncJob, jobParameters);

				break;

			case "erpScheduleSyncJob" :

				jobParameters = new JobParametersBuilder().addLong("currTime", System.currentTimeMillis()).toJobParameters();
				jobExecution = jobLauncher.run(erpScheduleSyncJob, jobParameters);

				break;


			case "textUploadJob" :

				jobParameters = new JobParametersBuilder().addLong("currTime", System.currentTimeMillis()).toJobParameters();
				jobExecution = jobLauncher.run(textUploadJob, jobParameters);

				break;

			case "inspectionJob" :

				jobParameters = new JobParametersBuilder().addLong("currTime", System.currentTimeMillis()).toJobParameters();
				jobExecution = jobLauncher.run(inspectionJob, jobParameters);

				break;

			case "workJob" :

				jobParameters = new JobParametersBuilder().addLong("currTime", System.currentTimeMillis()).toJobParameters();
				jobExecution = jobLauncher.run(workJob, jobParameters);

				break;

			case "inventoryJob" :

				jobParameters = new JobParametersBuilder().addLong("currTime", System.currentTimeMillis()).toJobParameters();
				jobExecution = jobLauncher.run(inventoryJob, jobParameters);

				break;

			case "dailyWorkJob" :

				jobParameters = new JobParametersBuilder().addLong("currTime", System.currentTimeMillis()).toJobParameters();
				jobExecution = jobLauncher.run(dailyWorkJob, jobParameters);

				break;
				
			case "erpOrderJob" :

				jobParameters = new JobParametersBuilder().addLong("currTime", System.currentTimeMillis()).toJobParameters();
				jobExecution = jobLauncher.run(erpOrderJob, jobParameters);

				break;
		}

		if(jobParameters != null && jobExecution != null){
			batchLogService.save(jobExecution);
		}
	}
}
