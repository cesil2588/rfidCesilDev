package com.systemk.spyder.Service.Mapper.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.Group.ReissueTagGroupModel;

public class ReissueTagGroupModelMapper implements RowMapper<ReissueTagGroupModel> {
    
	public ReissueTagGroupModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ReissueTagGroupModel obj = new ReissueTagGroupModel();
		
		obj.setCreateDate(rs.getString("create_date"));
		obj.setCompanySeq(rs.getLong("company_seq"));
		obj.setName(rs.getString("name"));
//		obj.setPublishLocation(rs.getString("publish_location"));
		obj.setStat1Count(rs.getString("stat1_count"));
		obj.setStat2Count(rs.getString("stat2_count"));
		obj.setStat3Count(rs.getString("stat3_count"));
		obj.setTotalCount(rs.getString("total_count"));
    	
    	return obj;
    }
}
