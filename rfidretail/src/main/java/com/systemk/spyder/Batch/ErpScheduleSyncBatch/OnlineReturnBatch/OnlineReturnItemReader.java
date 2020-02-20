package com.systemk.spyder.Batch.ErpScheduleSyncBatch.OnlineReturnBatch;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Entity.External.RfidLa12If;

@Configuration
public class OnlineReturnItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<RfidLa12If> onlineReturnReader(ExternalDataSourceConfig externalDataSourceConfig) throws Exception {
		String jpqlQuery = "SELECT t FROM RfidLa12If t " +
							"WHERE t.la12Tryn = 'N' " +
						 "ORDER BY t.key.la12Jmdt ASC";

		// JpaPaging 버그 수정
		JpaPagingItemReader<RfidLa12If> reader = new JpaPagingItemReader<RfidLa12If>() {
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
		reader.setName("onlineReturnReader");

		return reader;
	}
}
