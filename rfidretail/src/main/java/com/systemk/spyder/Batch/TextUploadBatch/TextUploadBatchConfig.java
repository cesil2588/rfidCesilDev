package com.systemk.spyder.Batch.TextUploadBatch;

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
import com.systemk.spyder.Batch.TextUploadBatch.BartagTextUploadBatch.BartagTextUploadItemProcessor;
import com.systemk.spyder.Batch.TextUploadBatch.BartagTextUploadBatch.BartagTextUploadItemReader;
import com.systemk.spyder.Batch.TextUploadBatch.BartagTextUploadBatch.BartagTextUploadItemReaderListener;
import com.systemk.spyder.Batch.TextUploadBatch.BartagTextUploadBatch.BartagTextUploadItemWriter;
import com.systemk.spyder.Batch.TextUploadBatch.BartagTextUploadBatch.BartagTextUploadItemWriterListener;
import com.systemk.spyder.Batch.TextUploadBatch.BartagTextUploadReissueBatch.BartagTextUploadReissueItemProcessor;
import com.systemk.spyder.Batch.TextUploadBatch.BartagTextUploadReissueBatch.BartagTextUploadReissueItemReader;
import com.systemk.spyder.Batch.TextUploadBatch.BartagTextUploadReissueBatch.BartagTextUploadReissueItemReaderListener;
import com.systemk.spyder.Batch.TextUploadBatch.BartagTextUploadReissueBatch.BartagTextUploadReissueItemWriter;
import com.systemk.spyder.Batch.TextUploadBatch.BartagTextUploadReissueBatch.BartagTextUploadReissueItemWriterListener;
import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.BatchTrigger;
import com.systemk.spyder.Repository.Main.BatchTriggerRepository;
import com.systemk.spyder.Service.RfidTagService;
import com.systemk.spyder.Service.UserNotiService;

@Configuration
@EnableBatchProcessing
@PropertySource("classpath:application.properties")
public class TextUploadBatchConfig {
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private BartagTextUploadItemReader bartagTextUploadItemReader;
	
	@Autowired
	private BartagTextUploadReissueItemReader bartagTextUploadReissueItemReader;
	
	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;
	
	@Autowired
	private RfidTagService rfidTagService;
	
	@Autowired
	private BatchTriggerRepository batchTriggerRepository;
	
	@Autowired
	private UserNotiService userNotiService;
	
	@Bean
	public Job textUploadJob(JobBuilderFactory jobs, 
							 CustomerJobCompletionNotificationListener listener, 
							 Step bartagUpload,
							 Step bartagUploadReissue) throws Exception {
		return jobBuilderFactory
				.get("textUploadJob")
				.incrementer(new RunIdIncrementer()).listener(listener)
				.start(bartagUpload)
				.next(bartagUploadReissue)
				.build();
	}
	
	@Bean
	public Step bartagUpload(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("bartagUpload").<BatchTrigger, BatchTrigger>chunk(10)
				.reader(bartagTextUploadItemReader.bartagTextUploadReader(mainDataSourceConfig))
				.listener(new BartagTextUploadItemReaderListener(batchTriggerRepository, userNotiService))
				.processor(new BartagTextUploadItemProcessor())
				.writer(new BartagTextUploadItemWriter(rfidTagService))
				.listener(new BartagTextUploadItemWriterListener(batchTriggerRepository, userNotiService))
				.build();
	}
	
	@Bean
	public Step bartagUploadReissue(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("bartagUploadReissue").<BatchTrigger, BatchTrigger>chunk(10)
				.reader(bartagTextUploadReissueItemReader.bartagTextUploadReissueReader(mainDataSourceConfig))
				.listener(new BartagTextUploadReissueItemReaderListener(batchTriggerRepository, userNotiService))
				.processor(new BartagTextUploadReissueItemProcessor())
				.writer(new BartagTextUploadReissueItemWriter(rfidTagService))
				.listener(new BartagTextUploadReissueItemWriterListener(batchTriggerRepository, userNotiService))
				.build();
	}
}
