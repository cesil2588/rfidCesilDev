package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.CountModel;

public class DistributionCountRowMapper implements RowMapper<CountModel> {
    
	public CountModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CountModel count = new CountModel();
		count.setTotal_amount(rs.getLong("total_amount"));
		count.setStat1_amount(rs.getLong("stat1_amount"));
		count.setStat2_amount(rs.getLong("stat2_amount"));
		count.setStat3_amount(rs.getLong("stat3_amount"));
		count.setStat4_amount(rs.getLong("stat4_amount"));
		count.setStat5_amount(rs.getLong("stat5_amount"));
		count.setStat6_amount(rs.getLong("stat6_amount"));
		count.setStat7_amount(rs.getLong("stat7_amount"));
    	
    	return count;
    }
}