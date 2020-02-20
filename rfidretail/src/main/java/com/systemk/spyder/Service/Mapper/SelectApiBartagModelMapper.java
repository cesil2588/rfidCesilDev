package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;

public class SelectApiBartagModelMapper implements RowMapper<SelectBartagModel> {
    
	public SelectBartagModel mapRow(ResultSet rs, int rowNum) throws SQLException {

		SelectBartagModel obj = new SelectBartagModel();
		
		obj.setData(rs.getString("data"));
		obj.setStyle(rs.getString("style"));
		obj.setColor(rs.getString("color"));
		obj.setSize(rs.getString("size"));
		obj.setErpKey(rs.getString("erp_key"));
		obj.setStartRfidSeq(rs.getString("start_rfid_seq"));
		obj.setEndRfidSeq(rs.getString("end_rfid_seq"));
		
    	return obj;
    }
}
