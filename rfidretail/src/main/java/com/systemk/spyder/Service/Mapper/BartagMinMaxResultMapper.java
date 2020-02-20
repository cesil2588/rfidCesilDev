package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Dto.Response.BartagMinMaxResult;

public class BartagMinMaxResultMapper implements RowMapper<BartagMinMaxResult>{

	@Override
	public BartagMinMaxResult mapRow(ResultSet rs, int rowNum) throws SQLException {

		BartagMinMaxResult obj = new BartagMinMaxResult();
		obj.setErpKey(rs.getString("erp_key"));
		obj.setStartRfidSeq(rs.getString("start_rfid_seq"));
		obj.setEndRfidSeq(rs.getString("end_rfid_seq"));

    	return obj;
    }
}
