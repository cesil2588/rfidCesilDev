package com.systemk.spyder.Batch.WorkJobBatch;

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
import com.systemk.spyder.Batch.WorkJobBatch.ProductionStorageBatch.ProductionStorageItemProcessor;
import com.systemk.spyder.Batch.WorkJobBatch.ProductionStorageBatch.ProductionStorageItemReader;
import com.systemk.spyder.Batch.WorkJobBatch.ProductionStorageBatch.ProductionStorageItemWriter;
import com.systemk.spyder.Batch.WorkJobBatch.ProductionStorageBatch.ProductionStorageItemWriterListener;
import com.systemk.spyder.Batch.WorkJobBatch.ReleaseErpScheduleBatch.ErpScheduleItemProcessor;
import com.systemk.spyder.Batch.WorkJobBatch.ReleaseErpScheduleBatch.ErpScheduleItemReader;
import com.systemk.spyder.Batch.WorkJobBatch.ReleaseErpScheduleBatch.ErpScheduleItemWriter;
import com.systemk.spyder.Batch.WorkJobBatch.ReleaseErpScheduleBatch.ErpScheduleItemWriterListener;
import com.systemk.spyder.Batch.WorkJobBatch.ReleaseScheduleCompleteBatch.ReleaseScheduleLogCompleteItemProcessor;
import com.systemk.spyder.Batch.WorkJobBatch.ReleaseScheduleCompleteBatch.ReleaseScheduleLogCompleteItemReader;
import com.systemk.spyder.Batch.WorkJobBatch.ReleaseScheduleCompleteBatch.ReleaseScheduleLogCompleteItemWriter;
import com.systemk.spyder.Batch.WorkJobBatch.ReleaseScheduleCompleteBatch.ReleaseScheduleLogCompleteItemWriterListener;
import com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleBatch.StorageScheduleLogCompleteItemProcessor;
import com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleBatch.StorageScheduleLogCompleteItemReader;
import com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleBatch.StorageScheduleLogCompleteItemWriter;
import com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleBatch.StorageScheduleLogCompleteItemWriterListener;
import com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleConfirmBatch.StorageScheduleLogConfirmItemProcessor;
import com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleConfirmBatch.StorageScheduleLogConfirmItemReader;
import com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleConfirmBatch.StorageScheduleLogConfirmItemWriter;
import com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleConfirmBatch.StorageScheduleLogConfirmItemWriterListener;
import com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleSaveBatch.StorageScheduleLogSaveItemProcessor;
import com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleSaveBatch.StorageScheduleLogSaveItemReader;
import com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleSaveBatch.StorageScheduleLogSaveItemWriter;
import com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleSaveBatch.StorageScheduleLogSaveItemWriterListener;
import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.ErpStoreSchedule;
import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;
import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.Entity.Main.TempProductionReleaseHeader;
import com.systemk.spyder.Entity.Main.TempProductionStorageHeader;
import com.systemk.spyder.Repository.Main.ErpStoreScheduleRepository;
import com.systemk.spyder.Repository.Main.ReleaseScheduleLogRepository;
import com.systemk.spyder.Repository.Main.StorageScheduleLogRepository;
import com.systemk.spyder.Repository.Main.TempDistributionReleaseBoxRepository;
import com.systemk.spyder.Repository.Main.TempProductionReleaseHeaderRepository;
import com.systemk.spyder.Repository.Main.TempProductionStorageHeaderRepository;
import com.systemk.spyder.Service.DistributionStorageLogService;
import com.systemk.spyder.Service.ErpStoreScheduleService;
import com.systemk.spyder.Service.ProductionStorageRfidTagService;
import com.systemk.spyder.Service.ReleaseScheduleLogService;
import com.systemk.spyder.Service.StorageScheduleLogService;

@Configuration
@EnableBatchProcessing
@PropertySource("classpath:application.properties")
public class WorkJobBatch {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private StorageScheduleLogService storageScheduleLogService;

	@Autowired
	private StorageScheduleLogConfirmItemReader storageScheduleLogConfirmItemReader;

	@Autowired
	private StorageScheduleLogCompleteItemReader storageScheduleLogCompleteItemReader;

	@Autowired
	private DistributionStorageLogService distributionStorageLogService;

	@Autowired
	private StorageScheduleLogRepository storageScheduleLogRepository;

	@Autowired
	private ProductionStorageRfidTagService productionStorageRfidTagService;

	@Autowired
	private StorageScheduleLogSaveItemReader storageScheduleLogSaveItemReader;

	@Autowired
	private TempProductionReleaseHeaderRepository tempProductionReleaseHeaderRepository;

	@Autowired
	private ProductionStorageItemReader productionStorageItemReader;

	@Autowired
	private TempDistributionReleaseBoxRepository tempDistributionReleaseBoxRepository;

	@Autowired
	private TempProductionStorageHeaderRepository tempProductionStorageHeaderRepository;

	@Autowired
	private ErpScheduleItemReader erpScheduleItemReader;

	@Autowired
	private ErpStoreScheduleService erpStoreScheduleService;

	@Autowired
	private ErpStoreScheduleRepository erpStoreScheduleRepository;

	@Autowired
	private ReleaseScheduleLogCompleteItemReader releaseScheduleLogCompleteItemReader;

	@Autowired
	private ReleaseScheduleLogService releaseScheduleLogService;

	@Autowired
	private ReleaseScheduleLogRepository releaseScheduleLogRepository;

	@Bean
	public Job workJob(JobBuilderFactory jobs,
									 CustomerJobCompletionNotificationListener listener,
									 Step productionStorageSave,
									 Step storageScheduleLogSave,
									 Step storageScheduleLogConfirm,
									 Step storageScheduleLogComplete,
									 Step erpSchedule,
									 Step releaseScheduleLogComplete) throws Exception {
		return jobBuilderFactory
				.get("workJob")
				.incrementer(new RunIdIncrementer()).listener(listener)
				.start(productionStorageSave)
				.on("*")
				.to(storageScheduleLogSave)
				.on("*")
				.to(storageScheduleLogConfirm)
				.on("*")
				.to(storageScheduleLogComplete)
				//.on("*")
				//.to(erpSchedule)
				//.on("*")
				// 매장입고완료 배치 실시간으로 변경
				//.to(releaseScheduleLogComplete)
				.end()
				.build();
	}

	@Bean
	public Step storageScheduleLogConfirm(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("storageScheduleLogConfirm").<StorageScheduleLog, StorageScheduleLog>chunk(100)
				.reader(storageScheduleLogConfirmItemReader.storageScheduleLogConfirmReader(mainDataSourceConfig))
				.processor(new StorageScheduleLogConfirmItemProcessor())
				.writer(new StorageScheduleLogConfirmItemWriter(storageScheduleLogService, distributionStorageLogService))
				.listener(new StorageScheduleLogConfirmItemWriterListener(storageScheduleLogRepository))
				.build();
	}

	@Bean
	public Step storageScheduleLogComplete(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("storageScheduleLogComplete").<StorageScheduleLog, StorageScheduleLog>chunk(100)
				.reader(storageScheduleLogCompleteItemReader.storageScheduleLogCompleteReader(mainDataSourceConfig))
				.processor(new StorageScheduleLogCompleteItemProcessor())
				.writer(new StorageScheduleLogCompleteItemWriter(storageScheduleLogService, distributionStorageLogService))
				.listener(new StorageScheduleLogCompleteItemWriterListener(storageScheduleLogRepository))
				.build();
	}

	@Bean
	public Step storageScheduleLogSave(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("storageScheduleLogSave").<TempProductionReleaseHeader, TempProductionReleaseHeader>chunk(100)
				.reader(storageScheduleLogSaveItemReader.storageScheduleLogSaveReader(mainDataSourceConfig))
				.processor(new StorageScheduleLogSaveItemProcessor())
				.writer(new StorageScheduleLogSaveItemWriter(productionStorageRfidTagService))
				.listener(new StorageScheduleLogSaveItemWriterListener(tempProductionReleaseHeaderRepository))
				.build();
	}

	@Bean
	public Step erpSchedule(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("erpSchedule").<ErpStoreSchedule, ErpStoreSchedule>chunk(100)
				.reader(erpScheduleItemReader.erpScheduleReader(mainDataSourceConfig))
				.processor(new ErpScheduleItemProcessor(tempDistributionReleaseBoxRepository))
				.writer(new ErpScheduleItemWriter(erpStoreScheduleService))
				.listener(new ErpScheduleItemWriterListener(erpStoreScheduleRepository))
				.build();
	}

	@Bean
	public Step releaseScheduleLogComplete(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("releaseScheduleLogComplete").<ReleaseScheduleLog, ReleaseScheduleLog>chunk(100)
				.reader(releaseScheduleLogCompleteItemReader.releaseScheduleLogCompleteReader(mainDataSourceConfig))
				.processor(new ReleaseScheduleLogCompleteItemProcessor())
				.writer(new ReleaseScheduleLogCompleteItemWriter(releaseScheduleLogService))
				.listener(new ReleaseScheduleLogCompleteItemWriterListener(releaseScheduleLogRepository))
				.build();
	}


	@Bean
	public Step productionStorageSave(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("productionStorageSave").<TempProductionStorageHeader, TempProductionStorageHeader>chunk(100)
				.reader(productionStorageItemReader.productionStroageReader(mainDataSourceConfig))
				.processor(new ProductionStorageItemProcessor())
				.writer(new ProductionStorageItemWriter(productionStorageRfidTagService))
				.listener(new ProductionStorageItemWriterListener(tempProductionStorageHeaderRepository))
				.build();
	}

}
