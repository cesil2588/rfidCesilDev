package com.systemk.spyder.Batch.ErpScheduleSyncBatch;

import com.systemk.spyder.Batch.CustomBatchConfigurer;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.OnlineReturnBatch.OnlineReturnItemProcessor;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.OnlineReturnBatch.OnlineReturnItemReader;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.OnlineReturnBatch.OnlineReturnItemWriter;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.OnlineReturnBatch.OnlineReturnItemWriterListener;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.OnlineScheduleBatch.OnlineScheduleItemProcessor;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.OnlineScheduleBatch.OnlineScheduleItemReader;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.OnlineScheduleBatch.OnlineScheduleItemWriter;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.OnlineScheduleBatch.OnlineScheduleItemWriterListener;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreInventoryBatch.StoreInventoryItemProcessor;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreInventoryBatch.StoreInventoryItemReader;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreInventoryBatch.StoreInventoryItemWriter;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreInventoryBatch.StoreInventoryItemWriterListener;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreMoveBatch.StoreMoveItemProcessor;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreMoveBatch.StoreMoveItemReader;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreMoveBatch.StoreMoveItemWriter;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreMoveBatch.StoreMoveItemWriterListener;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreReturnScheduleBatch.StoreReturnScheduleItemProcessor;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreReturnScheduleBatch.StoreReturnScheduleItemReader;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreReturnScheduleBatch.StoreReturnScheduleItemWriter;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreReturnScheduleBatch.StoreReturnScheduleItemWriterListener;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreScheduleBatch.StoreScheduleItemProcessor;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreScheduleBatch.StoreScheduleItemReader;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreScheduleBatch.StoreScheduleItemWriter;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreScheduleBatch.StoreScheduleItemWriterListener;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreStorageBatch.StoreStorageItemProcessor;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreStorageBatch.StoreStorageItemReader;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreStorageBatch.StoreStorageItemWriter;
import com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreStorageBatch.StoreStorageItemWriterListener;
import com.systemk.spyder.Batch.ErpSyncBatch.CustomerBatch.CustomerJobCompletionNotificationListener;
import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Entity.External.*;
import com.systemk.spyder.Entity.Main.*;
import com.systemk.spyder.Repository.External.*;
import com.systemk.spyder.Repository.Main.*;
import com.systemk.spyder.Service.StorageScheduleLogService;
import com.systemk.spyder.Service.StoreMoveLogService;
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
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
@PropertySource("classpath:application.properties")
public class ErpScheduleSyncBatchJobConfig {

	private JobBuilderFactory jobBuilderFactory;

	private StepBuilderFactory stepBuilderFactory;

	private ExternalDataSourceConfig externalDataSourceConfig;

	private JdbcTemplate template;

	private CompanyInfoRepository companyInfoRepository;

	private BartagMasterRepository bartagMasterRepository;

    private UserInfoRepository userInfoRepository;

	private ErpStoreScheduleRepository erpStoreScheduleRepository;

	private StoreScheduleItemReader storeScheduleItemReader;

	private StoreMoveItemReader storeMoveItemReader;

	private StoreMoveLogRepository storeMoveLogRepository;

	private StoreStorageRfidTagRepository storeStorageRfidTagRepository;

	private BoxInfoRepository boxInfoRepository;

	private StoreMoveLogService storeMoveLogService;

	private OnlineReturnItemReader onlineReturnItemReader;

	private ErpOnlineReturnRepository erpOnlineReturnRepository;

	private OnlineScheduleItemReader onlineScheduleItemReader;

	private ErpOnlineScheduleRepository erpOnlineScheduleRepository;

	private StoreReturnScheduleItemReader storeReturnScheduleItemReader;

	private ErpStoreReturnScheduleRepository erpStoreReturnScheduleRepository;

	private StorageScheduleLogService storageScheduleLogService;

	private StoreStorageItemReader storeStorageItemReader;

	private ReleaseScheduleLogRepository releaseScheduleLogRepository;

	private ProductMasterRepository productMasterRepository;

	private RfidLd02IfRepository rfidLd02IfRepository;

	private RfidSd02IfRepository rfidSd02IfRepository;

	private RfidLa11IfRepository rfidLa11IfRepository;

	private RfidLa12IfRepository rfidLa12IfRepository;

	private RfidLe10IfRepository rfidLe10IfRepository;

	private StoreInventoryItemReader storeInventoryItemReader;

	private InventoryScheduleHeaderRepository inventoryScheduleHeaderRepository;

	private RfidMd14IfRepository rfidMd14IfRepository;

	@Autowired
	public ErpScheduleSyncBatchJobConfig(JobBuilderFactory jobBuilderFactory,
										 StepBuilderFactory stepBuilderFactory,
										 ExternalDataSourceConfig externalDataSourceConfig,
										 JdbcTemplate template,
										 CompanyInfoRepository companyInfoRepository,
										 BartagMasterRepository bartagMasterRepository,
										 UserInfoRepository userInfoRepository,
										 ErpStoreScheduleRepository erpStoreScheduleRepository,
										 StoreScheduleItemReader storeScheduleItemReader,
										 StoreMoveItemReader storeMoveItemReader,
										 StoreMoveLogRepository storeMoveLogRepository,
										 StoreStorageRfidTagRepository storeStorageRfidTagRepository,
										 BoxInfoRepository boxInfoRepository,
										 StoreMoveLogService storeMoveLogService,
										 OnlineReturnItemReader onlineReturnItemReader,
										 ErpOnlineReturnRepository erpOnlineReturnRepository,
										 OnlineScheduleItemReader onlineScheduleItemReader,
										 ErpOnlineScheduleRepository erpOnlineScheduleRepository,
										 StoreReturnScheduleItemReader storeReturnScheduleItemReader,
										 ErpStoreReturnScheduleRepository erpStoreReturnScheduleRepository,
										 StorageScheduleLogService storageScheduleLogService,
										 StoreStorageItemReader storeStorageItemReader,
										 ReleaseScheduleLogRepository releaseScheduleLogRepository,
										 ProductMasterRepository productMasterRepository,
										 RfidLd02IfRepository rfidLd02IfRepository,
										 RfidSd02IfRepository rfidSd02IfRepository,
										 RfidLa11IfRepository rfidLa11IfRepository,
										 RfidLa12IfRepository rfidLa12IfRepository,
										 RfidLe10IfRepository rfidLe10IfRepository,
										 StoreInventoryItemReader storeInventoryItemReader,
										 InventoryScheduleHeaderRepository inventoryScheduleHeaderRepository,
										 RfidMd14IfRepository rfidMd14IfRepository) {
		this.jobBuilderFactory 					= jobBuilderFactory;
		this.stepBuilderFactory 				= stepBuilderFactory;
		this.externalDataSourceConfig 			= externalDataSourceConfig;
		this.template 							= template;
		this.companyInfoRepository 				= companyInfoRepository;
		this.bartagMasterRepository 			= bartagMasterRepository;
		this.userInfoRepository 				= userInfoRepository;
		this.erpStoreScheduleRepository 		= erpStoreScheduleRepository;
		this.storeScheduleItemReader 			= storeScheduleItemReader;
		this.storeMoveItemReader 				= storeMoveItemReader;
		this.storeMoveLogRepository 			= storeMoveLogRepository;
		this.storeStorageRfidTagRepository 		= storeStorageRfidTagRepository;
		this.boxInfoRepository 					= boxInfoRepository;
		this.storeMoveLogService 				= storeMoveLogService;
		this.onlineReturnItemReader 			= onlineReturnItemReader;
		this.erpOnlineReturnRepository 			= erpOnlineReturnRepository;
		this.onlineScheduleItemReader 			= onlineScheduleItemReader;
		this.erpOnlineScheduleRepository 		= erpOnlineScheduleRepository;
		this.storeReturnScheduleItemReader 		= storeReturnScheduleItemReader;
		this.erpStoreReturnScheduleRepository 	= erpStoreReturnScheduleRepository;
		this.storageScheduleLogService 			= storageScheduleLogService;
		this.storeStorageItemReader 			= storeStorageItemReader;
		this.releaseScheduleLogRepository 		= releaseScheduleLogRepository;
		this.productMasterRepository 			= productMasterRepository;
		this.rfidLd02IfRepository 				= rfidLd02IfRepository;
		this.rfidSd02IfRepository 				= rfidSd02IfRepository;
		this.rfidLa11IfRepository 				= rfidLa11IfRepository;
		this.rfidLa12IfRepository 				= rfidLa12IfRepository;
		this.rfidLe10IfRepository 				= rfidLe10IfRepository;
		this.storeInventoryItemReader 			= storeInventoryItemReader;
		this.inventoryScheduleHeaderRepository  = inventoryScheduleHeaderRepository;
		this.rfidMd14IfRepository 				= rfidMd14IfRepository;
	}			

	@Bean
	public Job erpScheduleSyncJob(JobBuilderFactory jobs,
						  CustomerJobCompletionNotificationListener listener,
						  Step storeScheduleSync,
						  Step storeStorageSync,
						  Step storeReturnScheduleSync,
						  Step storeInventorySync,
						  Step storeMoveSync,
						  Step onlineScheduleSync,
						  Step onlineReturnSync) throws Exception {

		return jobBuilderFactory
				.get("erpScheduleSyncJob")
				.incrementer(new RunIdIncrementer()).listener(listener)
				.start(storeScheduleSync)
				.on("*")
				.to(storeStorageSync)
//				.on("*")
//				.to(storeMoveSync)
//				.on("*")
//				.to(onlineScheduleSync)
//				.on("*")
//				.to(onlineReturnSync)
				.on("*")
				.to(storeReturnScheduleSync)
				.on("*")
				.to(storeInventorySync)
				.end()
				.build();
	}

	@Bean
	public Step storeScheduleSync(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("storeScheduleSync").<RfidLd02If, ErpStoreSchedule>chunk(1000)
				.reader(storeScheduleItemReader.storeScheduleReader(externalDataSourceConfig))
				.processor(new StoreScheduleItemProcessor(companyInfoRepository, bartagMasterRepository))
				.writer(new StoreScheduleItemWriter(erpStoreScheduleRepository))
				.listener(new StoreScheduleItemWriterListener(rfidLd02IfRepository))
				.build();
	}

	//반품 예정정보 가져오기
	@Bean
	public Step storeReturnScheduleSync(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("storeReturnScheduleSync").<RfidSd02If, ErpStoreReturnSchedule>chunk(1000)
				.reader(storeReturnScheduleItemReader.storeReturnScheduleReader(externalDataSourceConfig))
				.processor(new StoreReturnScheduleItemProcessor(companyInfoRepository))
				.writer(new StoreReturnScheduleItemWriter(erpStoreReturnScheduleRepository,boxInfoRepository, storageScheduleLogService, userInfoRepository))
				.listener(new StoreReturnScheduleItemWriterListener(rfidSd02IfRepository))
				.build();
	}

	// TODO 매장간 이동 재개발 필요
	@Bean
	public Step storeMoveSync(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("storeMoveSync").<RfidSd02If, RfidSd02If>chunk(1000)
				.reader(storeMoveItemReader.storeMoveReader(externalDataSourceConfig))
				.processor(new StoreMoveItemProcessor())
				.writer(new StoreMoveItemWriter(storeMoveLogRepository, companyInfoRepository, userInfoRepository, storeStorageRfidTagRepository, storeMoveLogService, boxInfoRepository))
				.listener(new StoreMoveItemWriterListener(template, externalDataSourceConfig))
				.build();
	}

	@Bean
	public Step onlineScheduleSync(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("onlineScheduleSync").<RfidLa11If, ErpOnlineSchedule>chunk(1000)
				.reader(onlineScheduleItemReader.onlineScheduleReader(externalDataSourceConfig))
				.processor(new OnlineScheduleItemProcessor(companyInfoRepository))
				.writer(new OnlineScheduleItemWriter(erpOnlineScheduleRepository))
				.listener(new OnlineScheduleItemWriterListener(rfidLa11IfRepository))
				.build();
	}

	@Bean
	public Step onlineReturnSync(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("onlineReturnSync").<RfidLa12If, ErpOnlineReturn>chunk(1000)
				.reader(onlineReturnItemReader.onlineReturnReader(externalDataSourceConfig))
				.processor(new OnlineReturnItemProcessor(companyInfoRepository))
				.writer(new OnlineReturnItemWriter(erpOnlineReturnRepository))
				.listener(new OnlineReturnItemWriterListener(rfidLa12IfRepository))
				.build();
	}

	@Bean
	public Step storeStorageSync(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("storeStorageSync").<RfidLe10If, ReleaseScheduleLog>chunk(1000)
				.reader(storeStorageItemReader.storeStorageReader(externalDataSourceConfig))
				.processor(new StoreStorageItemProcessor(companyInfoRepository, releaseScheduleLogRepository, productMasterRepository))
				.writer(new StoreStorageItemWriter(releaseScheduleLogRepository, boxInfoRepository))
				.listener(new StoreStorageItemWriterListener(rfidLe10IfRepository))
				.build();
	}

	@Bean
	public Step storeInventorySync(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("storeInventorySync").<RfidMd14If, InventoryScheduleHeader>chunk(1000)
				.reader(storeInventoryItemReader.storeInventoryReader(externalDataSourceConfig))
				.processor(new StoreInventoryItemProcessor(companyInfoRepository, productMasterRepository, userInfoRepository))
				.writer(new StoreInventoryItemWriter(inventoryScheduleHeaderRepository))
				.listener(new StoreInventoryItemWriterListener(rfidMd14IfRepository))
				.build();
	}
}
