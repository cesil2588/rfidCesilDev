package com.systemk.spyder.Service.Mapper.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.Group.StoreMoveGroupModel;

public class StoreMoveGroupModelMapper implements RowMapper<StoreMoveGroupModel> {

	public StoreMoveGroupModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StoreMoveGroupModel obj = new StoreMoveGroupModel();
		obj.setCreateDate(rs.getString("create_date"));
		obj.setStartCompanySeq(rs.getLong("start_company_seq"));
		obj.setStartCompanyName(rs.getString("start_company_name"));
		obj.setEndCompanySeq(rs.getLong("end_company_seq"));
		obj.setEndCompanyName(rs.getString("end_company_name"));
		obj.setStat1Count(rs.getLong("stat1_count"));
		obj.setStat2Count(rs.getLong("stat2_count"));
		obj.setStat3Count(rs.getLong("stat3_count"));
		obj.setStat4Count(rs.getLong("stat4_count"));
		obj.setStat5Count(rs.getLong("stat5_count"));
		obj.setStat6Count(rs.getLong("stat6_count"));
		obj.setTotalCount(rs.getLong("total_count"));
    	
    	return obj;
    }
}
