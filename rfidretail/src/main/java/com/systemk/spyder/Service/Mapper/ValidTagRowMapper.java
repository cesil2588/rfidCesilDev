package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.ValidTag;

public class ValidTagRowMapper implements RowMapper<ValidTag>{

	@Override
	public ValidTag mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ValidTag obj = new ValidTag();
		obj.setRfidTag(rs.getString("rfid_tag"));
		obj.setTargetStat(rs.getString("target_stat"));
		obj.setTargetRfidTag(rs.getString("target_rfid_tag"));
		obj.setTargetBoxBarcode(rs.getString("target_box_barcode"));
		
		return obj;
	}

}
