package com.systemk.spyder.Batch.WorkJobBatch.ReleaseScheduleCompleteBatch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;

@Configuration
public class ReleaseScheduleLogCompleteItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<ReleaseScheduleLog> releaseScheduleLogCompleteReader(MainDataSourceConfig mainDataSourceConfig) throws Exception {

		String jpqlQuery = "SELECT rsl FROM ReleaseScheduleLog rsl " +
							 "WHERE rsl.releaseYn = :releaseYn " +
							   "AND rsl.completeYn = :completeYn " +
							   "AND rsl.completeBatchYn = :completeBatchYn " +
							   "AND rsl.disuseYn = :disuseYn " +
						  "ORDER BY rsl.releaseScheduleLogSeq ASC";

		// JpaPaging 버그 수정
		JpaPagingItemReader<ReleaseScheduleLog> reader = new JpaPagingItemReader<ReleaseScheduleLog>() {
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
		reader.setName("releaseScheduleLogCompleteReader");

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("releaseYn", "Y");
		parameter.put("completeYn", "Y");
		parameter.put("completeBatchYn", "N");
		parameter.put("disuseYn", "N");
		reader.setParameterValues(parameter);

		return reader;
	}
}
