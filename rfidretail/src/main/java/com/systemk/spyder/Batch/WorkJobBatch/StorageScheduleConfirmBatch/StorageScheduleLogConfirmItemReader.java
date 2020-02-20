package com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleConfirmBatch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.StorageScheduleLog;

@Configuration
public class StorageScheduleLogConfirmItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<StorageScheduleLog> storageScheduleLogConfirmReader(MainDataSourceConfig mainDataSourceConfig) throws Exception {

		String jpqlQuery = "SELECT ssl FROM StorageScheduleLog ssl " +
							"WHERE ssl.confirmYn = :confirmYn " +
							  "AND ssl.batchYn = :batchYn " +
							  "AND ssl.completeYn = :completeYn " +
							  "AND ssl.disuseYn = :disuseYn " +
					     "ORDER BY ssl.storageScheduleLogSeq ASC";

		// JpaPaging 버그 수정
		JpaPagingItemReader<StorageScheduleLog> reader = new JpaPagingItemReader<StorageScheduleLog>() {
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
		reader.setName("storageScheduleLogConfirmReader");

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("confirmYn", "Y");
		parameter.put("batchYn", "N");
		parameter.put("completeYn", "N");
		parameter.put("disuseYn", "N");
		reader.setParameterValues(parameter);

		return reader;
	}
}
