package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.StoreReleaseGroupModel;

public class StoreReleaseGroupModelMapper implements RowMapper<StoreReleaseGroupModel> {

	public StoreReleaseGroupModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StoreReleaseGroupModel obj = new StoreReleaseGroupModel();
		obj.setCreateDate(rs.getString("create_date"));
		obj.setName(rs.getString("name"));
		obj.setCompanySeq(rs.getLong("company_seq"));
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
