package com.systemk.spyder.Batch.DailyWorkJobBatch;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.systemk.spyder.Batch.CustomBatchConfigurer;
import com.systemk.spyder.Batch.DailyWorkJobBatch.ReleaseScheduleSaveBatch.ReleaseScheduleLogSaveItemProcessor;
import com.systemk.spyder.Batch.DailyWorkJobBatch.ReleaseScheduleSaveBatch.ReleaseScheduleLogSaveItemReader;
import com.systemk.spyder.Batch.DailyWorkJobBatch.ReleaseScheduleSaveBatch.ReleaseScheduleLogSaveItemWriter;
import com.systemk.spyder.Batch.DailyWorkJobBatch.ReleaseScheduleSaveBatch.ReleaseScheduleLogSaveItemWriterListener;
import com.systemk.spyder.Batch.ErpSyncBatch.CustomerBatch.CustomerJobCompletionNotificationListener;
import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.TempDistributionReleaseBox;
import com.systemk.spyder.Repository.Main.TempDistributionReleaseBoxRepository;
import com.systemk.spyder.Service.DistributionStorageRfidTagService;

@Configuration
@EnableBatchProcessing
@PropertySource("classpath:application.properties")
public class DailyWorkJobBatch {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private ReleaseScheduleLogSaveItemReader releaseScheduleLogSaveItemReader;

	@Autowired
	private DistributionStorageRfidTagService distributionStorageRfidTagService;

	@Autowired
	private TempDistributionReleaseBoxRepository tempDistributionReleaseBoxRepository;

	@Bean
	public Job dailyWorkJob(JobBuilderFactory jobs,
									 CustomerJobCompletionNotificationListener listener,
									 Step releaseScheduleLogSave) throws Exception {
		return jobBuilderFactory
				.get("dailyWorkJob")
				.incrementer(new RunIdIncrementer()).listener(listener)
				.start(releaseScheduleLogSave)
				.build();
	}

	@Bean
	public Step releaseScheduleLogSave(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("releaseScheduleLogSave").<TempDistributionReleaseBox, TempDistributionReleaseBox>chunk(100)
				.reader(releaseScheduleLogSaveItemReader.releaseScheduleLogSaveReader(mainDataSourceConfig))
				.processor(new ReleaseScheduleLogSaveItemProcessor())
				.writer(new ReleaseScheduleLogSaveItemWriter(distributionStorageRfidTagService))
				.listener(new ReleaseScheduleLogSaveItemWriterListener(tempDistributionReleaseBoxRepository))
				.build();
	}
}
