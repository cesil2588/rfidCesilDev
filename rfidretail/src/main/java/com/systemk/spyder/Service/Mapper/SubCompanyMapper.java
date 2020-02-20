package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.BartagSubCompany;

public class SubCompanyMapper implements RowMapper<BartagSubCompany> {
    
	public BartagSubCompany mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		BartagSubCompany bartagSubCompany = new BartagSubCompany();
		bartagSubCompany.setName(rs.getString("detail_production_company_name"));
    	
    	return bartagSubCompany;
    }
}