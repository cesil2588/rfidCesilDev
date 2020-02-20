package com.systemk.spyder.Batch.ErpOrderBatch.MoveOrderListSaveBatch;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Entity.External.RfidSd06If;

@Configuration
public class MoveOrderListItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<RfidSd06If> moveOrderListReader(ExternalDataSourceConfig externalDataSourceConfig) throws Exception {
		String jpqlQuery = "SELECT t FROM RfidSd06If t " +
							"WHERE t.sd06Tryn = 'N' AND t.sd06Stat <> 'D' " +
						 "ORDER BY t.key.sd06Iddt ASC";

		// JpaPaging 버그 수정
		JpaPagingItemReader<RfidSd06If> reader = new JpaPagingItemReader<RfidSd06If>() {
			@Override
			public int getPage() {
				return 0;
			}
		};

		reader.setQueryString(jpqlQuery);
		reader.setEntityManagerFactory(externalDataSourceConfig.externalEntityManager().getObject());
		reader.setPageSize(100);
		reader.afterPropertiesSet();
		reader.setSaveState(true);
		reader.setName("moveOrderListItemReader");

		return reader;
	}
}
