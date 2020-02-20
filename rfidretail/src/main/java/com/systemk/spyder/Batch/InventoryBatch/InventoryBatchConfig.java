package com.systemk.spyder.Batch.InventoryBatch;

import com.systemk.spyder.Batch.CustomBatchConfigurer;
import com.systemk.spyder.Batch.ErpSyncBatch.CustomerBatch.CustomerJobCompletionNotificationListener;
import com.systemk.spyder.Batch.InventoryBatch.DistributionInventoryBatch.DistributionInventoryItemProcessor;
import com.systemk.spyder.Batch.InventoryBatch.DistributionInventoryBatch.DistributionInventoryItemReader;
import com.systemk.spyder.Batch.InventoryBatch.DistributionInventoryBatch.DistributionInventoryItemWriter;
import com.systemk.spyder.Batch.InventoryBatch.DistributionInventoryBatch.DistributionInventoryItemWriterListener;
import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.InventoryScheduleHeader;
import com.systemk.spyder.Repository.Main.DistributionStorageRepository;
import com.systemk.spyder.Repository.Main.DistributionStorageRfidTagRepository;
import com.systemk.spyder.Repository.Main.InventoryScheduleHeaderRepository;
import com.systemk.spyder.Repository.Main.RfidTagHistoryRepository;
import com.systemk.spyder.Service.DistributionStorageLogService;
import com.systemk.spyder.Service.DistributionStorageRfidTagService;
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

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
@PropertySource("classpath:application.properties")
public class InventoryBatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private DistributionInventoryItemReader distributionInventoryItemReader;

	@Autowired
	private InventoryScheduleHeaderRepository inventoryScheduleHeaderRepository;

	@Autowired
	private RfidTagHistoryRepository rfidTagHistoryRepository;

	@Autowired
	private DistributionStorageRepository distributionStorageRepository;

	@Autowired
	private DistributionStorageRfidTagRepository distributionStorageRfidTagRepository;

	@Autowired
	private DistributionStorageLogService distributionStorageLogService;

	@Autowired
	private DistributionStorageRfidTagService distributionStorageRfidTagService;

	@Bean
	public Job inventoryJob(JobBuilderFactory jobs,
							CustomerJobCompletionNotificationListener listener,
							Step distributionInventory) throws Exception {
		return jobBuilderFactory
				.get("inventoryJob")
				.incrementer(new RunIdIncrementer()).listener(listener)
				.start(distributionInventory)
				.build();
	}


	@Bean
	public Step distributionInventory(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("distributionInventory").<InventoryScheduleHeader, InventoryScheduleHeader>chunk(10)
				.reader(distributionInventoryItemReader.distributionInventoryReader(mainDataSourceConfig))
				.processor(new DistributionInventoryItemProcessor())
				.writer(new DistributionInventoryItemWriter(distributionStorageRepository,
												distributionStorageRfidTagRepository,
												distributionStorageLogService,
												distributionStorageRfidTagService,
												rfidTagHistoryRepository))
				.listener(new DistributionInventoryItemWriterListener(inventoryScheduleHeaderRepository))
				.build();
	}
}
