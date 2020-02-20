package com.systemk.spyder.Batch.WorkJobBatch.ProductionStorageBatch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.TempProductionStorageHeader;

@Configuration
public class ProductionStorageItemReader {

	@Bean(destroyMethod="")
	public ItemReader<TempProductionStorageHeader> productionStroageReader(MainDataSourceConfig mainDataSourceConfig) throws Exception {

		String jpqlQuery = "SELECT tpsh FROM TempProductionStorageHeader tpsh " +
							"WHERE tpsh.requestYn = :requestYn " +
							  "AND tpsh.batchYn = :batchYn " +
							  "AND tpsh.completeYn = :completeYn " +
					     "ORDER BY tpsh.tempHeaderSeq ASC";

		// JpaPaging 버그 수정
		JpaPagingItemReader<TempProductionStorageHeader> reader = new JpaPagingItemReader<TempProductionStorageHeader>() {
			@Override
			public int getPage() {
				return 0;
			}
		};

		reader.setQueryString(jpqlQuery);
		reader.setEntityManagerFactory(mainDataSourceConfig.mainEntityManager().getObject());
		reader.setPageSize(100);
		reader.afterPropertiesSet();
		reader.setSaveState(true);
		reader.setName("productionStroageReader");

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("requestYn", "Y");
		parameter.put("completeYn", "Y");
		parameter.put("batchYn", "N");
		reader.setParameterValues(parameter);

		return reader;
	}
}
