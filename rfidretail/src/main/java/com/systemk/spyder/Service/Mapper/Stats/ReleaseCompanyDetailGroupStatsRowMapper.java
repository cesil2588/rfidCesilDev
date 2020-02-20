package com.systemk.spyder.Service.Mapper.Stats;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.Stats.ReleaseCompanyDetailGroupStats;

public class ReleaseCompanyDetailGroupStatsRowMapper implements RowMapper<ReleaseCompanyDetailGroupStats>{

	@Override
	public ReleaseCompanyDetailGroupStats mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ReleaseCompanyDetailGroupStats stats = new ReleaseCompanyDetailGroupStats();
		
		stats.setCompanyName(rs.getString("name"));
		stats.setCompanySeq(rs.getLong("company_seq"));
		stats.setStyle(rs.getString("style"));
		stats.setColor(rs.getString("color"));
		stats.setSize(rs.getString("size"));
		stats.setRfidYn(rs.getString("rfid_yn"));
		stats.setOrderAmount(rs.getLong("order_amount"));
		stats.setReleaseAmount(rs.getLong("release_amount"));
		stats.setCompleteAmount(rs.getLong("complete_amount"));
		
		
    	return stats;
	}

}
