package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.MobileReleaseRfidTag;

public class MobileReleaseRfidTagMapper implements RowMapper<MobileReleaseRfidTag>{

	@Override
	public MobileReleaseRfidTag mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		MobileReleaseRfidTag obj = new MobileReleaseRfidTag();
		obj.setRfidTag(rs.getString("rfid_tag"));
		obj.setProductionStorageSeq(rs.getLong("production_storage_seq"));
		obj.setStyle(rs.getString("style"));
		obj.setColor(rs.getString("color"));
		obj.setSize(rs.getString("size"));
		obj.setOrderDegree(rs.getString("order_degree"));
		obj.setAdditionOrderDegree(rs.getString("addition_order_degree"));
		return obj;
	}

}
