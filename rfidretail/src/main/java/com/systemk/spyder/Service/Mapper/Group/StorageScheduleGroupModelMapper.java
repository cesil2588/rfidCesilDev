package com.systemk.spyder.Service.Mapper.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.Group.StorageScheduleGroupModel;

public class StorageScheduleGroupModelMapper implements RowMapper<StorageScheduleGroupModel> {

	public StorageScheduleGroupModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StorageScheduleGroupModel obj = new StorageScheduleGroupModel();
		obj.setStartCompanySeq(rs.getLong("start_company_seq"));
		obj.setCompanyName(rs.getString("name"));
		obj.setArrivalDate(rs.getString("arrival_date"));
		obj.setStat1BoxCount(rs.getLong("stat1_box_count"));
		obj.setStat2BoxCount(rs.getLong("stat2_box_count"));
		obj.setTotalBoxCount(rs.getLong("total_box_count"));
		obj.setStat1StyleCount(rs.getLong("stat1_style_count"));
		obj.setStat2StyleCount(rs.getLong("stat2_style_count"));
		obj.setTotalStyleCount(rs.getLong("total_style_count"));
		obj.setStat1TagCount(rs.getLong("stat1_tag_count"));
		obj.setStat2TagCount(rs.getLong("stat2_tag_count"));
		obj.setTotalTagCount(rs.getLong("total_tag_count"));
		obj.setBatchPercent(rs.getLong("batch_percent"));
    	
    	return obj;
    }
}
