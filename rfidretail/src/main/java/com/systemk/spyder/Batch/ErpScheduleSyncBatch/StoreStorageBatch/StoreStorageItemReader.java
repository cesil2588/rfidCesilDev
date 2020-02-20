package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreStorageBatch;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Entity.External.RfidLe10If;

@Configuration
public class StoreStorageItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<RfidLe10If> storeStorageReader(ExternalDataSourceConfig externalDataSourceConfig) throws Exception {
		String jpqlQuery = "SELECT t FROM RfidLe10If t " +
							"WHERE t.le10Tryn = 'N' " +
						 "ORDER BY t.key.le10Chdt, t.key.le10Bxno, t.key.le10Chsq ASC";

		// JpaPaging 버그 수정
		JpaPagingItemReader<RfidLe10If> reader = new JpaPagingItemReader<RfidLe10If>() {
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
		reader.setName("storeStorageReader");

		return reader;
	}
}
