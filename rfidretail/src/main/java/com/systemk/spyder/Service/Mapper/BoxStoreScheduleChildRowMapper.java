package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.StoreScheduleChildModel;

public class BoxStoreScheduleChildRowMapper implements RowMapper<StoreScheduleChildModel>{

	public StoreScheduleChildModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StoreScheduleChildModel storeSchedule = new StoreScheduleChildModel();
		
		storeSchedule.setBoxSeq(rs.getLong("box_seq"));
		storeSchedule.setBarcode(rs.getString("barcode"));
		storeSchedule.setOpenDbCode(rs.getString("open_db_code"));
		storeSchedule.setLineNum(rs.getString("line_num"));
		storeSchedule.setStyle(rs.getString("style"));
		storeSchedule.setColor(rs.getString("color"));
		storeSchedule.setSize(rs.getString("size"));
		storeSchedule.setOrderDegree(rs.getString("order_degree"));
		storeSchedule.setCount(rs.getString("count"));
    	
    	return storeSchedule;
    }
}
