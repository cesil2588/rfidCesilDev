package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.RfidModel;

public class BoxStyleRfidRowMapper implements RowMapper<RfidModel> {

	public RfidModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		RfidModel style = new RfidModel();
		
		style.setStyle(rs.getString("style"));
		style.setColor(rs.getString("color"));
		style.setSize(rs.getString("size"));
		style.setOrderDegree(rs.getString("order_degree"));
		style.setRfidTag(rs.getString("rfid_tag"));
    	
    	return style;
    }
}
