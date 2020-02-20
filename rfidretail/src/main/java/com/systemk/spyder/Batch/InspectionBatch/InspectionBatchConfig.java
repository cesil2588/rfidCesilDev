package com.systemk.spyder.Batch.InspectionBatch;

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
import com.systemk.spyder.Batch.ErpSyncBatch.CustomerBatch.CustomerJobCompletionNotificationListener;
import com.systemk.spyder.Batch.InspectionBatch.InspectionBatch.InspectionBatchItemProcessor;
import com.systemk.spyder.Batch.InspectionBatch.InspectionBatch.InspectionBatchItemReader;
import com.systemk.spyder.Batch.InspectionBatch.InspectionBatch.InspectionBatchItemReaderListener;
import com.systemk.spyder.Batch.InspectionBatch.InspectionBatch.InspectionBatchItemWriter;
import com.systemk.spyder.Batch.InspectionBatch.InspectionBatch.InspectionBatchItemWriterListener;
import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.BatchTrigger;
import com.systemk.spyder.Repository.Main.BatchTriggerRepository;
import com.systemk.spyder.Service.ProductionStorageRfidTagService;
import com.systemk.spyder.Service.UserNotiService;

@Configuration
@EnableBatchProcessing
@PropertySource("classpath:application.properties")
public class InspectionBatchConfig {
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private InspectionBatchItemReader inspectionBatchItemReader;
	
	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;
	
	@Autowired
	private ProductionStorageRfidTagService productionStorageRfidTagService;
	
	@Autowired
	private BatchTriggerRepository batchTriggerRepository;
	
	@Autowired
	private UserNotiService userNotiService;
	
	@Bean
	public Job inspectionJob(JobBuilderFactory jobs, CustomerJobCompletionNotificationListener listener, Step inspection) throws Exception {
		return jobBuilderFactory
				.get("inspectionJob")
				.incrementer(new RunIdIncrementer()).listener(listener)
				.start(inspection)
				.build();
	}
	
	@Bean
	public Step inspection(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("inspection").<BatchTrigger, BatchTrigger>chunk(10)
				.reader(inspectionBatchItemReader.inspectionItemReader(mainDataSourceConfig))
				.listener(new InspectionBatchItemReaderListener(batchTriggerRepository, userNotiService))
				.processor(new InspectionBatchItemProcessor())
				.writer(new InspectionBatchItemWriter(productionStorageRfidTagService))
				.listener(new InspectionBatchItemWriterListener(batchTriggerRepository, userNotiService))
				.build();
	}
}
