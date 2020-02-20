package com.systemk.spyder.Service.Mapper.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.Group.InventoryScheduleGroupModel;

public class InventoryScheduleGroupModelMapper implements RowMapper<InventoryScheduleGroupModel> {

	public InventoryScheduleGroupModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		InventoryScheduleGroupModel obj = new InventoryScheduleGroupModel();
		
		obj.setCreateDate(rs.getString("create_date"));
		obj.setCompanySeq(rs.getLong("company_seq"));
		obj.setName(rs.getString("name"));
		obj.setStat1HeaderCount(rs.getLong("stat1_header_count"));
		obj.setStat2HeaderCount(rs.getLong("stat2_header_count"));
		obj.setStat3HeaderCount(rs.getLong("stat3_header_count"));
		obj.setStat1StyleCount(rs.getLong("stat1_style_count"));
		obj.setStat2StyleCount(rs.getLong("stat2_style_count"));
		obj.setStat3StyleCount(rs.getLong("stat3_style_count"));
		obj.setStat1TagCount(rs.getLong("stat1_tag_count"));
		obj.setStat2TagCount(rs.getLong("stat2_tag_count"));
		obj.setStat3TagCount(rs.getLong("stat3_tag_count"));
		obj.setStat4TagCount(rs.getLong("stat4_tag_count"));
		obj.setBatchPercent(rs.getLong("batch_percent"));
    	
    	return obj;
    }
}