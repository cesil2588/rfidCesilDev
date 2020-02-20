package com.systemk.spyder.Service.Mapper.Select;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.Select.SelectCompanyModel;

public class SelectCompanyModelMapper implements RowMapper<SelectCompanyModel> {
    
	public SelectCompanyModel mapRow(ResultSet rs, int rowNum) throws SQLException {

		SelectCompanyModel obj = new SelectCompanyModel();
		obj.setName(rs.getString("name"));
		obj.setType(rs.getString("type"));
		obj.setCompanySeq(rs.getLong("company_seq"));
		
    	return obj;
    }
}
