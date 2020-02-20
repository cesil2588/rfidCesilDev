package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.CountModel;

public class DistributionStockStorageScheduleRowMapper implements RowMapper<CountModel> {
    
	public CountModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CountModel count = new CountModel();
		count.setTotal_amount(rs.getLong("total_amount"));
		count.setStat1_amount(rs.getLong("stat1_amount"));
		count.setStat2_amount(rs.getLong("stat2_amount"));
    	
    	return count;
    }
}