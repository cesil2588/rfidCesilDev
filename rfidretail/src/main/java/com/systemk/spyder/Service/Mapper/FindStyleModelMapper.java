package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;

public class FindStyleModelMapper implements RowMapper<SelectBartagModel> {
    
	public SelectBartagModel mapRow(ResultSet rs, int rowNum) throws SQLException {

		SelectBartagModel obj = new SelectBartagModel();
		obj.setStyle(rs.getString("style"));
		obj.setColor(rs.getString("color"));
		obj.setSize(rs.getString("size"));
		obj.setErpKey(rs.getString("erp_key"));
		
    	return obj;
    }
}