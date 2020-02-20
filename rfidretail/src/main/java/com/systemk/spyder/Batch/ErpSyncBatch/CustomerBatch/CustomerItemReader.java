package com.systemk.spyder.Batch.ErpSyncBatch.CustomerBatch;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Entity.External.RfidZa11If;

@Configuration
public class CustomerItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<RfidZa11If> customerReader(ExternalDataSourceConfig externalDataSourceConfig) throws Exception {
		String jpqlQuery = "SELECT t FROM RfidZa11If t " +
							"WHERE t.za11Tryn = 'N' " +
						 "ORDER BY t.key.za11Crdt ASC";

		// JpaPaging 버그 수정
		JpaPagingItemReader<RfidZa11If> reader = new JpaPagingItemReader<RfidZa11If>() {
			@Override
			public int getPage() {
				return 0;
			}
		};

		reader.setQueryString(jpqlQuery);
		reader.setEntityManagerFactory(externalDataSourceConfig.externalEntityManager().getObject());
		reader.setPageSize(1000);
		reader.afterPropertiesSet();
		reader.setSaveState(true);
		reader.setName("customerReader");

		return reader;
	}
}
