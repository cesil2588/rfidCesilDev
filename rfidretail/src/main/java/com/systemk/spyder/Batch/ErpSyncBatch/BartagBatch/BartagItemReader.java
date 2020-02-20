package com.systemk.spyder.Batch.ErpSyncBatch.BartagBatch;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Entity.External.RfidAc18If;

@Configuration
public class BartagItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<RfidAc18If> bartagReader(ExternalDataSourceConfig externalDataSourceConfig) throws Exception {
		String jpqlQuery = "SELECT t FROM RfidAc18If t " +
							"WHERE t.ac18Tryn = 'N' " +
					     "ORDER BY t.key.ac18Crdt ASC";

		// JpaPaging 버그 수정
		JpaPagingItemReader<RfidAc18If> reader = new JpaPagingItemReader<RfidAc18If>() {
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
		reader.setName("bartagReader");

		return reader;
	}
}
