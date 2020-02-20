package com.systemk.spyder.Batch.WorkJobBatch.ReleaseErpScheduleBatch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.ErpStoreSchedule;

@Configuration
public class ErpScheduleItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<ErpStoreSchedule> erpScheduleReader(MainDataSourceConfig mainDataSourceConfig) throws Exception {
		String jpqlQuery = "SELECT e FROM ErpStoreSchedule e " +
							"WHERE e.batchYn = :batchYn " +
							  "AND e.erpCompleteYn = :erpCompleteYn " +
							  "AND e.releaseAmount > 0 " +
						 "ORDER BY e.erpStoreScheduleSeq ASC ";

		// JpaPaging 버그 수정
		JpaPagingItemReader<ErpStoreSchedule> reader = new JpaPagingItemReader<ErpStoreSchedule>() {
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
		reader.setName("erpScheduleReader");

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("batchYn", "N");
		parameter.put("erpCompleteYn", "N");
		reader.setParameterValues(parameter);

		return reader;
	}
}
