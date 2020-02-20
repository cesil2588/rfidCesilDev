package com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleBatch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.StorageScheduleLog;

@Configuration
public class StorageScheduleLogCompleteItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<StorageScheduleLog> storageScheduleLogCompleteReader(MainDataSourceConfig mainDataSourceConfig) throws Exception {

		String jpqlQuery = "SELECT ssl FROM StorageScheduleLog ssl " +
							 "WHERE ssl.confirmYn = :confirmYn " +
							   "AND ssl.batchYn = :batchYn " +
							   "AND ssl.completeYn = :completeYn " +
							   "AND ssl.wmsCompleteYn = :wmsCompleteYn " +
							   "AND ssl.erpCompleteYn = :erpCompleteYn " +
							   "AND ssl.completeBatchYn = :completeBatchYn " +
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
		reader.setName("storageScheduleLogCompleteReader");

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("confirmYn", "Y");
		parameter.put("batchYn", "Y");
		parameter.put("completeYn", "Y");
		parameter.put("wmsCompleteYn", "Y");
		parameter.put("erpCompleteYn", "N");
		parameter.put("completeBatchYn", "N");
		parameter.put("disuseYn", "N");
		reader.setParameterValues(parameter);

		return reader;
	}
}
