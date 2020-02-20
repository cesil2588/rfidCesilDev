package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.StoreScheduleParentModel;

public class BoxStoreScheduleParentRowMapper implements RowMapper<StoreScheduleParentModel>{

	public StoreScheduleParentModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StoreScheduleParentModel storeSchedule = new StoreScheduleParentModel();
		
		storeSchedule.setBoxSeq(rs.getLong("box_seq"));
		storeSchedule.setBarcode(rs.getString("barcode"));
		storeSchedule.setOpenDbCode(rs.getString("open_db_code"));
    	
    	return storeSchedule;
    }
}
