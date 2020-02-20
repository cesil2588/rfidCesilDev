package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreReturnScheduleBatch;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Entity.External.RfidSd02If;

@Configuration
public class StoreReturnScheduleItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<RfidSd02If> storeReturnScheduleReader(ExternalDataSourceConfig externalDataSourceConfig) throws Exception {

		String jpqlQuery = "SELECT t FROM RfidSd02If t " +
							"WHERE t.sd02Tryn = 'N' AND t.sd02Trdt = '' AND t.sd02Engb = 'E' " +
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
		reader.setName("storeReturnScheduleReader");

		return reader;
	}
}
