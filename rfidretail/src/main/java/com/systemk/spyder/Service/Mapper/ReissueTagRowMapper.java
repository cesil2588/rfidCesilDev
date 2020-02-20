package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.CountModel;

public class ReissueTagRowMapper implements RowMapper<CountModel> {
    
	public CountModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CountModel count = new CountModel();
		count.setTotal_amount(rs.getLong("total_amount"));
		count.setStat1_amount(rs.getLong("stat1_amount"));
		count.setStat2_amount(rs.getLong("stat2_amount"));
		count.setStat3_amount(rs.getLong("stat3_amount"));
    	
    	return count;
    }
}