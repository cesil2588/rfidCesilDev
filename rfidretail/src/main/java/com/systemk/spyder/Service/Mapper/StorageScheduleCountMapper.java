package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.StorageScheduleModel;

public class StorageScheduleCountMapper implements RowMapper<StorageScheduleModel> {

	public StorageScheduleModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StorageScheduleModel obj = new StorageScheduleModel();
		obj.setStat1BoxCount(rs.getLong("stat1_box_count"));
		obj.setStat2BoxCount(rs.getLong("stat2_box_count"));
		obj.setStat3BoxCount(rs.getLong("stat3_box_count"));
		obj.setStat1StyleCount(rs.getLong("stat1_style_count"));
		obj.setStat2StyleCount(rs.getLong("stat2_style_count"));
		obj.setStat3StyleCount(rs.getLong("stat3_style_count"));
		obj.setStat1TagCount(rs.getLong("stat1_tag_count"));
		obj.setStat2TagCount(rs.getLong("stat2_tag_count"));
		obj.setStat3TagCount(rs.getLong("stat3_tag_count"));
    	
    	return obj;
    }
}
