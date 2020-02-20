package com.systemk.spyder.Batch.ErpOrderBatch.ReturnOrderListSaveBatch;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Entity.External.RfidSd14If;

@Configuration
public class ReturnOrderListItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<RfidSd14If> returnOrderListReader(ExternalDataSourceConfig externalDataSourceConfig) throws Exception {
		String jpqlQuery = "SELECT t FROM RfidSd14If t " +
							"WHERE t.sd14Tryn = 'N' AND t.sd14Stat <> 'D' " +
						 "ORDER BY t.key.sd14Jsdt ASC";

		// JpaPaging 버그 수정
		JpaPagingItemReader<RfidSd14If> reader = new JpaPagingItemReader<RfidSd14If>() {
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
		reader.setName("returnOrderListItemReader");

		return reader;
	}
}
