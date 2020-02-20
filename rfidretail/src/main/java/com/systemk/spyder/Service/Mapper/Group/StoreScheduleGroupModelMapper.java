package com.systemk.spyder.Service.Mapper.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.Group.StoreScheduleGroupModel;

public class StoreScheduleGroupModelMapper implements RowMapper<StoreScheduleGroupModel> {

	public StoreScheduleGroupModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StoreScheduleGroupModel obj = new StoreScheduleGroupModel();
		obj.setCompleteDate(rs.getString("complete_date"));
		obj.setCompleteType(rs.getString("complete_type"));
		obj.setCompleteSeq(rs.getString("complete_seq"));
		obj.setCompanySeq(rs.getLong("company_seq"));
		obj.setCompanyName(rs.getString("name"));
		obj.setCustomerCode(rs.getString("customer_code"));
		obj.setCornerCode(rs.getString("corner_code"));
		obj.setStyle(rs.getString("style"));
		obj.setColor(rs.getString("color"));
		obj.setSize(rs.getString("size"));
		obj.setOrderAmount(rs.getLong("order_amount"));
		obj.setReleaseAmount(rs.getLong("release_amount"));
		obj.setBatchPercent(rs.getLong("batch_percent"));
    	
    	return obj;
    }
}
