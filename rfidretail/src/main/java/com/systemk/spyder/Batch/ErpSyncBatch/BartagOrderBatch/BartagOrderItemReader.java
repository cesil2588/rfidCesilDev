package com.systemk.spyder.Batch.ErpSyncBatch.BartagOrderBatch;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Entity.External.RfidAa06If;

@Configuration
public class BartagOrderItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<RfidAa06If> bartagOrderReader(ExternalDataSourceConfig externalDataSourceConfig) throws Exception {
		String jpqlQuery = "SELECT t FROM RfidAa06If t " +
							"WHERE t.aa06Tryn = 'N' " +
						 "ORDER BY t.key.aa06Crdt ASC";

		// JpaPaging 버그 수정
		JpaPagingItemReader<RfidAa06If> reader = new JpaPagingItemReader<RfidAa06If>() {
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
		reader.setName("bartagOrderReader");

		return reader;
	}
}
