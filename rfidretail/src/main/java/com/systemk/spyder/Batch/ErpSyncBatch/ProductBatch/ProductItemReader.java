package com.systemk.spyder.Batch.ErpSyncBatch.ProductBatch;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Entity.External.RfidAc16If;

@Configuration
public class ProductItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<RfidAc16If> productReader(ExternalDataSourceConfig externalDataSourceConfig) throws Exception {
		String jpqlQuery = "SELECT t FROM RfidAc16If t " +
							"WHERE t.ac16Tryn = 'N' " +
		   				 "ORDER BY t.key.ac16Crdt ASC";

		// JpaPaging 버그 수정
		JpaPagingItemReader<RfidAc16If> reader = new JpaPagingItemReader<RfidAc16If>() {
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
		reader.setName("productReader");

		return reader;
	}

}
