package com.systemk.spyder.Batch.ErpOrderBatch;

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
import com.systemk.spyder.Batch.ErpOrderBatch.MoveOrderListSaveBatch.MoveOrderListItemProcessor;
import com.systemk.spyder.Batch.ErpOrderBatch.MoveOrderListSaveBatch.MoveOrderListItemReader;
import com.systemk.spyder.Batch.ErpOrderBatch.MoveOrderListSaveBatch.MoveOrderListItemWriter;
import com.systemk.spyder.Batch.ErpOrderBatch.MoveOrderListSaveBatch.MoveOrderListItemWriterListener;
import com.systemk.spyder.Batch.ErpOrderBatch.ReturnOrderListSaveBatch.ReturnOrderListItemProcessor;
import com.systemk.spyder.Batch.ErpOrderBatch.ReturnOrderListSaveBatch.ReturnOrderListItemReader;
import com.systemk.spyder.Batch.ErpOrderBatch.ReturnOrderListSaveBatch.ReturnOrderListItemWriter;
import com.systemk.spyder.Batch.ErpOrderBatch.ReturnOrderListSaveBatch.ReturnOrderListItemWriterListener;
import com.systemk.spyder.Batch.ErpSyncBatch.CustomerBatch.CustomerJobCompletionNotificationListener;
import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Entity.External.RfidSd06If;
import com.systemk.spyder.Entity.External.RfidSd14If;
import com.systemk.spyder.Entity.Main.ErpStoreMove;
import com.systemk.spyder.Entity.Main.ErpStoreReturnInfo;
import com.systemk.spyder.Repository.External.RfidSd06IfRepository;
import com.systemk.spyder.Repository.External.RfidSd14IfRepository;
import com.systemk.spyder.Repository.Main.ErpStoreMoveDetailRepository;
import com.systemk.spyder.Repository.Main.ErpStoreMoveRepository;
import com.systemk.spyder.Repository.Main.ErpStoreReturnInfoRepository;
import com.systemk.spyder.Repository.Main.ErpStoreReturnSubInfoRepository;

@Configuration
@EnableBatchProcessing
@PropertySource("classpath:application.properties")
public class ErpOrderBatch {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private ExternalDataSourceConfig externalDataSourceConfig;
	
	@Autowired
	private ReturnOrderListItemReader returnOrderListItemReader;
	
	@Autowired
	private MoveOrderListItemReader moveOrderListItemReader;
	
	@Autowired
	private RfidSd14IfRepository rfidSd14IfRepository;
	
	@Autowired
	private RfidSd06IfRepository rfidSd06IfRepository;
	
	@Autowired
	private ErpStoreReturnInfoRepository erpStoreReturnInfoRepository;
	
	@Autowired
	private ErpStoreReturnSubInfoRepository erpStoreReturnSubInfoRepository;
	
	@Autowired
	private ErpStoreMoveRepository erpStoreMoveRepository;
	
	@Autowired
	private ErpStoreMoveDetailRepository erpStoreMoveDetailRepository;


	@Bean
    public BatchConfigurer Configurer(EntityManagerFactory entityManagerFactory){
        return new CustomBatchConfigurer(entityManagerFactory);
    }

	@Bean
	public Job erpOrderJob(JobBuilderFactory jobs,
									 CustomerJobCompletionNotificationListener listener,
									 Step orderListSave,
									 Step moveOrderListSave) throws Exception {
		return jobBuilderFactory
				.get("erpOrderJob")
				.incrementer(new RunIdIncrementer()).listener(listener)
				.start(orderListSave)
				.on("*")
				.to(moveOrderListSave)
				.end()
				.build();
	}

	@Bean
	public Step orderListSave(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("orderListSave").<RfidSd14If, ErpStoreReturnInfo>chunk(100)
				.reader(returnOrderListItemReader.returnOrderListReader(externalDataSourceConfig))
				.processor(new ReturnOrderListItemProcessor())
				.writer(new ReturnOrderListItemWriter(erpStoreReturnInfoRepository, erpStoreReturnSubInfoRepository))
				.listener(new ReturnOrderListItemWriterListener(rfidSd14IfRepository))
				.build();
	}
	
	@Bean
	public Step moveOrderListSave(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("moveOrderListSave").<RfidSd06If, ErpStoreMove>chunk(100)
				.reader(moveOrderListItemReader.moveOrderListReader(externalDataSourceConfig))
				.processor(new MoveOrderListItemProcessor())
				.writer(new MoveOrderListItemWriter(erpStoreMoveRepository, erpStoreMoveDetailRepository))
				.listener(new MoveOrderListItemWriterListener(rfidSd06IfRepository))
				.build();
	}
	
}
