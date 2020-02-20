package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreInventoryBatch;

import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Entity.External.RfidLe10If;
import com.systemk.spyder.Entity.External.RfidMd14If;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StoreInventoryItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<RfidMd14If> storeInventoryReader(ExternalDataSourceConfig externalDataSourceConfig) throws Exception {
		String jpqlQuery = "SELECT t FROM RfidMd14If t " +
							"WHERE t.md14Tryn = 'N' " +
						 "ORDER BY t.key.md14Bsdt, t.key.md14Mjcd, t.key.md14Corn, t.key.md14Styl, t.key.md14Stcd ASC";

		// JpaPaging 버그 수정
		JpaPagingItemReader<RfidMd14If> reader = new JpaPagingItemReader<RfidMd14If>() {
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
