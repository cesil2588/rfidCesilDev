package com.systemk.spyder.Batch.ErpSyncBatch.StoragePosBatch;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Entity.External.RfidZa40If;

@Configuration
public class StoragePosItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<RfidZa40If> storagePosReader(ExternalDataSourceConfig externalDataSourceConfig) throws Exception {
		String jpqlQuery = "SELECT t FROM RfidZa40If t " +
							"WHERE t.za40Tryn = 'N' " +
						 "ORDER BY t.key.za40Crdt ASC";

		// JpaPaging 버그 수정
		JpaPagingItemReader<RfidZa40If> reader = new JpaPagingItemReader<RfidZa40If>() {
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
		reader.setName("storagePosReader");

		return reader;
	}
}
