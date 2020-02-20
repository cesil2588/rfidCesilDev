package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreMoveBatch;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Entity.External.RfidSd02If;

@Configuration
public class StoreMoveItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<RfidSd02If> storeMoveReader(ExternalDataSourceConfig externalDataSourceConfig) throws Exception {
		String jpqlQuery = "SELECT t FROM RfidSd02If t " +
							"WHERE t.sd02TrynF = 'N' AND t.sd02Engb = 'E' " +
						 "ORDER BY t.key.sd02Orno ASC";

		// JpaPaging 버그 수정
		JpaPagingItemReader<RfidSd02If> reader = new JpaPagingItemReader<RfidSd02If>() {
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
		reader.setName("storeMoveReader");

		return reader;
	}

}
