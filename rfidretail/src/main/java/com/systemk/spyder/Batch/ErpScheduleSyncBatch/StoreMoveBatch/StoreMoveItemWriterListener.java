package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreMoveBatch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Config.MultiDataBase.ExternalDataSourceConfig;
import com.systemk.spyder.Entity.External.RfidSd02If;

@Component
public class StoreMoveItemWriterListener implements ItemWriteListener<RfidSd02If>{

	private static final Logger log = LoggerFactory.getLogger(StoreMoveItemWriterListener.class);

	private ExternalDataSourceConfig externalDataSourceConfig;

	private JdbcTemplate template;

	public StoreMoveItemWriterListener(JdbcTemplate template,
									ExternalDataSourceConfig externalDataSourceConfig){
		this.template = template;
		this.externalDataSourceConfig = externalDataSourceConfig;
	}

	@Override
	public void beforeWrite(List<? extends RfidSd02If> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWrite(List<? extends RfidSd02If> items) {
		/*
		template.setDataSource(externalDataSourceConfig.externalDataSource());

		String query = "UPDATE rfid_sd02_if SET sd02_tryn_f = 'Y', sd02_trdt_f = ? " +
						"WHERE sd02_mgdt = ? AND sd02_frcd = ? AND sd02_frco = ? AND sd02_bxno = ? AND sd02_bxsr = ? AND sd02_engb = 'E'";

		for(RfidSd02IfBean obj: items){

			template.update(query,
					CalendarUtil.convertFormat("yyyyMMddHHmmss"),
					obj.getSd02Mgdt(),
					obj.getSd02Frcd(),
					obj.getSd02Frco(),
					obj.getSd02Bxno(),
					obj.getSd02Bxsr());
		}	*/
	}

	@Override
	public void onWriteError(Exception exception, List<? extends RfidSd02If> items) {
		// TODO Auto-generated method stub

	}

}

