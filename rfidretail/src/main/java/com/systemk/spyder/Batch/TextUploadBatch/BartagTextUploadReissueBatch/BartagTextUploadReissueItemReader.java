package com.systemk.spyder.Batch.TextUploadBatch.BartagTextUploadReissueBatch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.BatchTrigger;

@Configuration
public class BartagTextUploadReissueItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<BatchTrigger> bartagTextUploadReissueReader(MainDataSourceConfig mainDataSourceConfig) throws Exception {
		String jpqlQuery = "SELECT a FROM BatchTrigger a WHERE a.type = :type AND a.stat = :stat ORDER BY a.batchTriggerSeq ASC";

		// JpaPaging 버그 수정
		JpaPagingItemReader<BatchTrigger> reader = new JpaPagingItemReader<BatchTrigger>() {
			@Override
			public int getPage() {
				return 0;
			}
		};

		reader.setQueryString(jpqlQuery);
		reader.setEntityManagerFactory(mainDataSourceConfig.mainEntityManager().getObject());
		reader.setPageSize(10);
		reader.afterPropertiesSet();
		reader.setSaveState(true);
		reader.setName("bartagTextUploadReissueReader");

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("type", "4");
		parameter.put("stat", "1");
		reader.setParameterValues(parameter);

		return reader;
	}
}
