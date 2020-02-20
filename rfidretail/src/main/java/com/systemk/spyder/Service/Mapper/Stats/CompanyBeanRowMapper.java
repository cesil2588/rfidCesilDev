package com.systemk.spyder.Service.Mapper.Stats;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.CompanyBean;

public class CompanyBeanRowMapper implements RowMapper<CompanyBean>{
	
	public CompanyBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CompanyBean stats = new CompanyBean();
		stats.setName(rs.getString("name"));
		stats.setCompanySeq(rs.getLong("company_seq"));
    	
    	return stats;
    }
}