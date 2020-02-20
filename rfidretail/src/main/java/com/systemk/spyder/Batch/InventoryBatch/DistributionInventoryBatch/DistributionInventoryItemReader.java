package com.systemk.spyder.Batch.InventoryBatch.DistributionInventoryBatch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.InventoryScheduleHeader;

@Configuration
public class DistributionInventoryItemReader {

	@Bean(destroyMethod="")
	public JpaPagingItemReader<InventoryScheduleHeader> distributionInventoryReader(MainDataSourceConfig mainDataSourceConfig) throws Exception {

		String jpqlQuery = "SELECT ish FROM InventoryScheduleHeader ish " +
				"WHERE ish.confirmYn = :confirmYn " +
				  "AND ish.completeYn = :completeYn " +
				  "AND ish.disuseYn = :disuseYn " +
				  "AND ish.batchYn = :batchYn " +
				  "AND ish.companyType = :companyType " +
			 "ORDER BY ish.inventoryScheduleHeaderSeq ASC";

		JpaPagingItemReader<InventoryScheduleHeader> reader = new JpaPagingItemReader<InventoryScheduleHeader>();
		reader.setQueryString(jpqlQuery);
		reader.setEntityManagerFactory(mainDataSourceConfig.mainEntityManager().getObject());
		reader.setPageSize(10);
		reader.afterPropertiesSet();
		reader.setSaveState(true);

		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("confirmYn", "Y");
		parameter.put("completeYn", "Y");
		parameter.put("disuseYn", "N");
		parameter.put("batchYn", "N");
		parameter.put("companyType", "distribution");

		reader.setParameterValues(parameter);

		return reader;
	}
}
