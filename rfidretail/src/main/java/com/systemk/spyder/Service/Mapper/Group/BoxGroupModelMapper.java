package com.systemk.spyder.Service.Mapper.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.Group.BoxGroupModel;

public class BoxGroupModelMapper implements RowMapper<BoxGroupModel> {
    
	public BoxGroupModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		BoxGroupModel obj = new BoxGroupModel();
		
		obj.setCreateDate(rs.getString("create_date"));
		obj.setStartCompanySeq(rs.getLong("start_company_seq"));
		obj.setCompanyName(rs.getString("name"));
		obj.setStat(rs.getString("stat"));
		obj.setType(rs.getString("type"));
		obj.setStat1_count(rs.getString("stat1_count"));
		obj.setStat2_count(rs.getString("stat2_count"));
		obj.setStat3_count(rs.getString("stat3_count"));
		obj.setStat4_count(rs.getString("stat4_count"));
		obj.setTotal_count(rs.getString("total_count"));
    	
    	return obj;
    }
}
