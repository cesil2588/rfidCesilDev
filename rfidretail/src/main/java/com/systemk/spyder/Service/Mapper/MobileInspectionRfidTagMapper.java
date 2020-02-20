package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.MobileInspectionRfidTag;

public class MobileInspectionRfidTagMapper implements RowMapper<MobileInspectionRfidTag>{

	@Override
	public MobileInspectionRfidTag mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		MobileInspectionRfidTag obj = new MobileInspectionRfidTag();
		obj.setRfidTag(rs.getString("rfid_tag"));
		obj.setProductionStorageSeq(rs.getLong("production_storage_seq"));
		obj.setBartagSeq(rs.getLong("bartag_seq"));
		return obj;
	}

}
