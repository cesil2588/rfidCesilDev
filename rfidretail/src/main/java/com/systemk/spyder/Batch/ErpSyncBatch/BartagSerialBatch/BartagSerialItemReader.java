package com.systemk.spyder.Batch.ErpSyncBatch.BartagSerialBatch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.BartagMaster;

@Configuration
public class BartagSerialItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<BartagMaster> bartagSerialReader(MainDataSourceConfig mainDataSourceConfig) throws Exception {
		String jpqlQuery = "SELECT a FROM BartagMaster a WHERE a.generateSeqYn = :generateSeqYn AND a.stat = :stat ORDER BY a.bartagSeq ASC";


		// JpaPaging 버그 수정
		JpaPagingItemReader<BartagMaster> reader = new JpaPagingItemReader<BartagMaster>() {
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

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("generateSeqYn", "N");
		parameter.put("stat", "1");
		reader.setParameterValues(parameter);

		return reader;
	}
}
