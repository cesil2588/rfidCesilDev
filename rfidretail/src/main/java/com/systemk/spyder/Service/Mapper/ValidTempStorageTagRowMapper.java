package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.ValidTag;

public class ValidTempStorageTagRowMapper implements RowMapper<ValidTag>{

	@Override
	public ValidTag mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ValidTag obj = new ValidTag();
		obj.setRfidTag(rs.getString("rfid_tag"));
		obj.setTargetRfidTag(rs.getString("target_rfid_tag"));
		
		return obj;
	}

}
