package com.systemk.spyder.Service.Mapper.Select;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.Select.SelectBartagModel;

public class SelectBartagSeqModelMapper implements RowMapper<SelectBartagModel> {
    
	public SelectBartagModel mapRow(ResultSet rs, int rowNum) throws SQLException {

		SelectBartagModel obj = new SelectBartagModel();
		
		obj.setData(rs.getString("data"));
		obj.setBartagSeq(rs.getLong("bartag_seq"));
		obj.setSeq(rs.getLong("seq"));
		obj.setStartRfidSeq(rs.getString("start_rfid_seq"));
		obj.setEndRfidSeq(rs.getString("end_rfid_seq"));
		
    	return obj;
    }
}
