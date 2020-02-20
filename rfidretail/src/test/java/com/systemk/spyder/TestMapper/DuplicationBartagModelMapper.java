package com.systemk.spyder.TestMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.TestModel.DuplicationBartagModel;

public class DuplicationBartagModelMapper implements RowMapper<DuplicationBartagModel>{

	@Override
	public DuplicationBartagModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		DuplicationBartagModel obj = new DuplicationBartagModel();
		
		obj.setBartagSeq(rs.getLong("bartag_seq"));
		obj.setCreateDate(rs.getString("create_date"));
		obj.setErpKey(rs.getString("erp_key"));
		obj.setStyle(rs.getString("style"));
		obj.setColor(rs.getString("color"));
		obj.setSize(rs.getString("size"));
		obj.setOrderDegree(rs.getString("order_degree"));
		obj.setAdditionOrderDegree(rs.getString("addition_order_degree"));
		obj.setStartRfidSeq(rs.getString("start_rfid_seq"));
		obj.setEndRfidSeq(rs.getString("end_rfid_seq"));
		obj.setAmount(rs.getLong("amount"));
		
		return obj;
	}
}
