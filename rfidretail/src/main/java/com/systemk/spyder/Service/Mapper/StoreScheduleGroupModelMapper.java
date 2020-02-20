package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.StoreScheduleGroupModel;

public class StoreScheduleGroupModelMapper implements RowMapper<StoreScheduleGroupModel> {

	public StoreScheduleGroupModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StoreScheduleGroupModel obj = new StoreScheduleGroupModel();
		obj.setCompleteDate(rs.getString("complete_date"));
		obj.setCompleteType(rs.getString("complete_type"));
		obj.setCompleteSeq(rs.getString("complete_seq"));
		obj.setStyle(rs.getString("style"));
		obj.setColor(rs.getString("color"));
		obj.setSize(rs.getString("size"));
		obj.setOrderAmount(rs.getLong("order_amount"));
		obj.setReleaseAmount(rs.getLong("release_amount"));
		obj.setBatchPercent(rs.getLong("batch_percent"));
    	
    	return obj;
    }
}
