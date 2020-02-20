package com.systemk.spyder.Batch.DailyWorkJobBatch.ReleaseScheduleSaveBatch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.TempDistributionReleaseBox;

@Configuration
public class ReleaseScheduleLogSaveItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<TempDistributionReleaseBox> releaseScheduleLogSaveReader(MainDataSourceConfig mainDataSourceConfig) throws Exception {
		String jpqlQuery = "SELECT t FROM TempDistributionReleaseBox t " +
							"WHERE t.completeYn = :completeYn " +
							  "AND t.wmsCompleteYn = :wmsCompleteYn " +
							  "AND t.wmsValidYn = :wmsValidYn " +
							  "AND t.batchYn = :batchYn " +
						 "ORDER BY t.tempBoxSeq ASC ";

		// JpaPaging 버그 수정
		JpaPagingItemReader<TempDistributionReleaseBox> reader = new JpaPagingItemReader<TempDistributionReleaseBox>() {
			@Override
			public int getPage() {
				return 0;
			}
		};

		reader.setQueryString(jpqlQuery);
		reader.setEntityManagerFactory(mainDataSourceConfig.mainEntityManager().getObject());
		reader.setPageSize(1000);
		reader.afterPropertiesSet();
		reader.setSaveState(true);
		reader.setName("releaseScheduleLogSaveReader");

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("completeYn", "N");
		parameter.put("wmsCompleteYn", "Y");
		parameter.put("wmsValidYn", "Y");
		parameter.put("batchYn", "N");
		reader.setParameterValues(parameter);

		return reader;
	}
}
