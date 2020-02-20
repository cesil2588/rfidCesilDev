package com.systemk.spyder.Batch.WorkJobBatch.StorageScheduleSaveBatch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.TempProductionReleaseHeader;

@Configuration
public class StorageScheduleLogSaveItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<TempProductionReleaseHeader> storageScheduleLogSaveReader(MainDataSourceConfig mainDataSourceConfig) throws Exception {

		String jpqlQuery = "SELECT tprh FROM TempProductionReleaseHeader tprh " +
							"WHERE tprh.requestYn = :requestYn " +
							  "AND tprh.batchYn = :batchYn " +
							  "AND tprh.completeYn = :completeYn " +
					     "ORDER BY tprh.tempHeaderSeq ASC";

		// JpaPaging 버그 수정
		JpaPagingItemReader<TempProductionReleaseHeader> reader = new JpaPagingItemReader<TempProductionReleaseHeader>() {
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
		reader.setName("storageScheduleLogSaveReader");

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("requestYn", "Y");
		parameter.put("completeYn", "Y");
		parameter.put("batchYn", "N");
		reader.setParameterValues(parameter);

		return reader;
	}
}
