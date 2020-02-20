package com.systemk.spyder.Batch.ErpSyncBatch;

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
import com.systemk.spyder.Batch.ErpSyncBatch.BartagBatch.BartagItemProcessor;
import com.systemk.spyder.Batch.ErpSyncBatch.BartagBatch.BartagItemReader;
import com.systemk.spyder.Batch.ErpSyncBatch.BartagBatch.BartagItemWriter;
import com.systemk.spyder.Batch.ErpSyncBatch.BartagBatch.BartagItemWriterListener;
import com.systemk.spyder.Batch.ErpSyncBatch.BartagOrderBatch.BartagOrderItemProcessor;
import com.systemk.spyder.Batch.ErpSyncBatch.BartagOrderBatch.BartagOrderItemReader;
import com.systemk.spyder.Batch.ErpSyncBatch.BartagOrderBatch.BartagOrderItemWriter;
import com.systemk.spyder.Batch.ErpSyncBatch.BartagOrderBatch.BartagOrderItemWriterListener;
import com.systemk.spyder.Batch.ErpSyncBatch.BartagSerialBatch.BartagSerialItemProcessor;
import com.systemk.spyder.Batch.ErpSyncBatch.BartagSerialBatch.BartagSerialItemReader;
import com.systemk.spyder.Batch.ErpSyncBatch.BartagSerialBatch.BartagSerialItemWriter;
import com.systemk.spyder.Batch.ErpSyncBatch.BartagSerialBatch.BartagSerialItemWriterListener;
import com.systemk.spyder.Batch.ErpSyncBatch.CustomerBatch.CustomerItemProcessor;
import com.systemk.spyder.Batch.ErpSyncBatch.CustomerBatch.CustomerItemReader;
import com.systemk.spyder.Batch.ErpSyncBatch.CustomerBatch.CustomerItemWriter;
import com.systemk.spyder.Batch.ErpSyncBatch.CustomerBatch.CustomerItemWriterListener;
import com.systemk.spyder.Batch.ErpSyncBatch.CustomerBatch.CustomerJobCompletionNotificationListener;
import com.systemk.spyder.Batch.ErpSyncBatch.ProductBatch.ProductItemProcessor;
import com.systemk.spyder.Batch.ErpSyncBatch.ProductBatch.ProductItemReader;
import com.systemk.spyder.Batch.ErpSyncBatch.ProductBatch.ProductItemWriter;
import com.systemk.spyder.Batch.ErpSyncBatch.ProductBatch.ProductItemWriterListener;
import com.systemk.spyder.Batch.ErpSyncBatch.StoragePosBatch.StoragePosItemProcessor;
import com.systemk.spyder.Batch.ErpSyncBatch.StoragePosBatch.StoragePosItemReader;
import com.systemk.spyder.Batch.ErpSyncBatch.StoragePosBatch.StoragePosItemWriter;
import com.systemk.spyder.Batch.ErpSyncBatch.StoragePosBatch.StoragePosItemWriterListener;
import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.External.RfidAa06If;
import com.systemk.spyder.Entity.External.RfidAc16If;
import com.systemk.spyder.Entity.External.RfidAc18If;
import com.systemk.spyder.Entity.External.RfidZa11If;
import com.systemk.spyder.Entity.External.RfidZa40If;
import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.BartagOrder;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.ProductMaster;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.External.RfidAa06IfRepository;
import com.systemk.spyder.Repository.External.RfidAc16IfRepository;
import com.systemk.spyder.Repository.External.RfidAc18IfRepository;
import com.systemk.spyder.Repository.External.RfidZa11IfRepository;
import com.systemk.spyder.Repository.External.RfidZa40IfRepository;
import com.systemk.spyder.Repository.Main.BartagMasterRepository;
import com.systemk.spyder.Repository.Main.BartagOrderRepository;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.ParentCodeInfoRepository;
import com.systemk.spyder.Repository.Main.ProductMasterRepository;
import com.systemk.spyder.Repository.Main.RfidTagMasterRepository;
import com.systemk.spyder.Repository.Main.RoleInfoRepository;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import com.systemk.spyder.Service.BartagLogService;
import com.systemk.spyder.Service.BartagService;
import com.systemk.spyder.Service.CompanyService;
import com.systemk.spyder.Service.MailService;
import com.systemk.spyder.Service.RedisService;
import com.systemk.spyder.Service.UserNotiService;

@Configuration
@EnableBatchProcessing
@PropertySource("classpath:application.properties")
public class ErpSyncBatchJobConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private ExternalDataSourceConfig externalDataSourceConfig;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private CustomerItemReader customerItemReader;

	@Autowired
	private ProductItemReader productItemReader;

	@Autowired
	private BartagItemReader bartagItemReader;

	@Autowired
	private BartagSerialItemReader bartagSerialItemReader;

	@Autowired
	private CompanyInfoRepository companyInfoRepository;

	@Autowired
	private ProductMasterRepository productMasterRepository;

	@Autowired
	private BartagMasterRepository bartagMasterRepository;

	@Autowired
	private RfidTagMasterRepository rfidTagMasterRepository;

	@Autowired
	private ParentCodeInfoRepository parentCodeInfoRepository;

	@Autowired
	private MailService mailService;

	@Autowired
    private UserInfoRepository userInfoRepository;

	@Autowired
	private BartagLogService bartagLogService;

	@Autowired
	private UserNotiService userNotiService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private RfidAc18IfRepository rfidAc18IfRepository;

	@Autowired
	private BartagOrderItemReader bartagOrderItemReader;

	@Autowired
	private BartagOrderRepository bartagOrderRepository;

	@Autowired
	private BartagService bartagService;

	@Autowired
	private RfidAa06IfRepository rfidAa06IfRepository;

	@Autowired
	private RedisService redisService;

	@Autowired
	private RoleInfoRepository roleInfoRepository;

	@Autowired
	private StoragePosItemReader storagePosItemReader;

	@Autowired
	private RfidAc16IfRepository rfidAc16IfRepository;

	@Autowired
	private RfidZa40IfRepository rfidZa40IfRepository;

	@Autowired
	private RfidZa11IfRepository rfidZa11IfRepository;

	@Bean
    public BatchConfigurer Configurer(EntityManagerFactory entityManagerFactory){
        return new CustomBatchConfigurer(entityManagerFactory);
    }

	@Bean
	public Job erpSyncJob(JobBuilderFactory jobs,
						  CustomerJobCompletionNotificationListener listener,
						  Step erpCustomerSync,
						  Step erpPosSync,
						  Step erpProductSync,
						  Step erpBartagOrderSync,
						  Step erpBartagSync,
						  Step bartagSerialGenerate) throws Exception {

		return jobBuilderFactory
				.get("erpSyncJob")
				.incrementer(new RunIdIncrementer()).listener(listener)
				.start(erpCustomerSync)
				.on("*")
				.to(erpPosSync)
				.on("*")
				.to(erpProductSync)
				.on("*")
				.to(erpBartagOrderSync)
				.on("*")
				.to(erpBartagSync)
				.on("*")
				.to(bartagSerialGenerate)
				.end()
				.build();
	}

	@Bean
	public Step erpCustomerSync(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("erpCustomerSync").<RfidZa11If, CompanyInfo>chunk(1000)
				.reader(customerItemReader.customerReader(externalDataSourceConfig))
				.processor(new CustomerItemProcessor(companyInfoRepository, parentCodeInfoRepository, roleInfoRepository))
				.writer(new CustomerItemWriter(companyInfoRepository, companyService))
				.listener(new CustomerItemWriterListener(rfidZa11IfRepository))
				.build();
	}

	@Bean
	public Step erpPosSync(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("erpPosSync").<RfidZa40If, UserInfo>chunk(1000)
				.reader(storagePosItemReader.storagePosReader(externalDataSourceConfig))
				.processor(new StoragePosItemProcessor(companyInfoRepository))
				.writer(new StoragePosItemWriter(userInfoRepository))
				.listener(new StoragePosItemWriterListener(rfidZa40IfRepository))
				.build();
	}

	@Bean
	public Step erpProductSync(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("erpProductSync").<RfidAc16If, ProductMaster>chunk(1000)
				.reader(productItemReader.productReader(externalDataSourceConfig))
				.processor(new ProductItemProcessor(parentCodeInfoRepository))
				.writer(new ProductItemWriter(productMasterRepository, redisService))
				.listener(new ProductItemWriterListener(rfidAc16IfRepository))
				.build();
	}

	@Bean
	public Step erpBartagOrderSync(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("erpBartagOrderSync").<RfidAa06If, BartagOrder>chunk(1000)
				.reader(bartagOrderItemReader.bartagOrderReader(externalDataSourceConfig))
				.processor(new BartagOrderItemProcessor(companyInfoRepository, productMasterRepository))
				.writer(new BartagOrderItemWriter(bartagOrderRepository))
				.listener(new BartagOrderItemWriterListener(rfidAa06IfRepository))
				.build();
	}

	@Bean
	public Step erpBartagSync(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("erpBartagSync").<RfidAc18If, BartagMaster>chunk(1000)
				.reader(bartagItemReader.bartagReader(externalDataSourceConfig))
				.processor(new BartagItemProcessor(productMasterRepository, companyInfoRepository))
				.writer(new BartagItemWriter(bartagMasterRepository, bartagService))
				.listener(new BartagItemWriterListener(rfidAc18IfRepository))
				.build();
	}

	@Bean
	public Step bartagSerialGenerate(StepBuilderFactory steps) throws Exception {
		return stepBuilderFactory.get("bartagSerialGenerate").<BartagMaster, BartagMaster>chunk(1000)
				.reader(bartagSerialItemReader.bartagSerialReader(mainDataSourceConfig))
				.processor(new BartagSerialItemProcessor())
				.writer(new BartagSerialItemWriter(rfidTagMasterRepository, companyInfoRepository, bartagMasterRepository, rfidAc18IfRepository))
				.listener(new BartagSerialItemWriterListener(bartagMasterRepository, mailService, userInfoRepository, userNotiService, bartagLogService))
				.build();
	}
}
