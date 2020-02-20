package com.systemk.spyder.Batch;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.apache.log4j.Logger;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

public class CustomBatchConfigurer implements BatchConfigurer {

	private static final Logger LOG = Logger.getLogger(CustomBatchConfigurer.class);

	private final EntityManagerFactory entityManagerFactory;

	private PlatformTransactionManager batchTransactionManager;

	private JobRepository jobRepository;

	private JobLauncher jobLauncher;

	private JobExplorer jobExplorer;

	/**
	 * Create a new {@link CustomBatchConfigurer} instance.
	 * 
	 * @param entityManagerFactory
	 *            the entity manager factory
	 */
	public CustomBatchConfigurer(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	@Override
	public JobRepository getJobRepository() {
		return this.jobRepository;
	}

	@Override
	public PlatformTransactionManager getTransactionManager() {
		return this.batchTransactionManager;
	}

	@Override
	public JobLauncher getJobLauncher() {
		return this.jobLauncher;
	}

	@Override
	public JobExplorer getJobExplorer() throws Exception {
		return this.jobExplorer;
	}

	@PostConstruct
	public void initialize() {
		try {
			// transactionManager:
			LOG.info("Forcing the use of a JPA transactionManager");
			if (this.entityManagerFactory == null) {
				throw new Exception("Unable to initialize batch configurer : entityManagerFactory must not be null");
			}
			this.batchTransactionManager = new JpaTransactionManager(this.entityManagerFactory);

			// jobRepository:
			LOG.info("Forcing the use of a Map based JobRepository");
			MapJobRepositoryFactoryBean jobRepositoryFactory = new MapJobRepositoryFactoryBean(this.batchTransactionManager);
			jobRepositoryFactory.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
			jobRepositoryFactory.afterPropertiesSet();
			this.jobRepository = jobRepositoryFactory.getObject();

			// jobLauncher:
			SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
			jobLauncher.setJobRepository(getJobRepository());
			jobLauncher.afterPropertiesSet();
			this.jobLauncher = jobLauncher;

			// jobExplorer:
			MapJobExplorerFactoryBean jobExplorerFactory = new MapJobExplorerFactoryBean(jobRepositoryFactory);
			jobExplorerFactory.afterPropertiesSet();
			this.jobExplorer = jobExplorerFactory.getObject();
		} catch (Exception ex) {
			throw new IllegalStateException("Unable to initialize Spring Batch", ex);
		}
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public BatchConfigurer Configurer(EntityManagerFactory entityManagerFactory){
		return new CustomBatchConfigurer(entityManagerFactory);
	}
}