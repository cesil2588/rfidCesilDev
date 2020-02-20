package com.systemk.spyder.Service.Mapper.Select;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;

public class SelectBartagModelMapper implements RowMapper<SelectBartagModel> {
    
	public SelectBartagModel mapRow(ResultSet rs, int rowNum) throws SQLException {

		SelectBartagModel obj = new SelectBartagModel();
		obj.setData(rs.getString("data"));
		
    	return obj;
    }
}
