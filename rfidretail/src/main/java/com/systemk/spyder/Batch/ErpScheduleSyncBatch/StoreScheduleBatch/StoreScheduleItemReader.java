package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreScheduleBatch;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Entity.External.RfidLd02If;

@Configuration
public class StoreScheduleItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<RfidLd02If> storeScheduleReader(ExternalDataSourceConfig externalDataSourceConfig) throws Exception {
		String jpqlQuery = "SELECT t FROM RfidLd02If t " +
							"WHERE t.ld02Tryn = 'N' AND t.ld02Stat <> 'D' " +
						 "ORDER BY t.key.ld02Mgdt ASC";

		// JpaPaging 버그 수정
		JpaPagingItemReader<RfidLd02If> reader = new JpaPagingItemReader<RfidLd02If>() {
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
		reader.setName("storeScheduleReader");

		return reader;
	}
}
