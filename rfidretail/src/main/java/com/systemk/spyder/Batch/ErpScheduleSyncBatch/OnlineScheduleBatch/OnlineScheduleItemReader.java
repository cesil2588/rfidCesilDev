package com.systemk.spyder.Batch.ErpScheduleSyncBatch.OnlineScheduleBatch;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Entity.External.RfidLa11If;

@Configuration
public class OnlineScheduleItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<RfidLa11If> onlineScheduleReader(ExternalDataSourceConfig externalDataSourceConfig) throws Exception {
		String jpqlQuery = "SELECT t FROM RfidLa11If t " +
							"WHERE t.la11Tryn = 'N' " +
						 "ORDER BY t.key.la11Jmdt ASC";

		// JpaPaging 버그 수정
		JpaPagingItemReader<RfidLa11If> reader = new JpaPagingItemReader<RfidLa11If>() {
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
		reader.setName("onlineScheduleReader");

		return reader;
	}
}
