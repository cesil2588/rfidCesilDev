package com.systemk.spyder.Service.Impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Repository.Main.RfidTagReissuePrintLogRepository;
import com.systemk.spyder.Service.RfidTagReissuePrintLogService;

@Service
public class RfidTagReissuePrintLogServiceImpl implements RfidTagReissuePrintLogService{

	@Autowired
	private RfidTagReissuePrintLogRepository rfidTagReissuePrintLogRepository;
	
	@Autowired
	private JdbcTemplate template;
	
	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Transactional(readOnly = true)
	@Override
	public Long maxSeq(String createDate) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "SELECT ISNULL(MAX(create_seq), 0) FROM rfid_tag_reissue_print_log WHERE create_date = ?";
		
    	return template.queryForObject(query, new Object[] { createDate }, Long.class);
	}
}
