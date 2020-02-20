package com.systemk.spyder.Service.Mapper.Stats;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.Stats.DailyBartagOrderGroupStats;

public class DailyBartagOrderGroupStatsRowMapper implements RowMapper<DailyBartagOrderGroupStats>{
	
	public DailyBartagOrderGroupStats mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		DailyBartagOrderGroupStats stats = new DailyBartagOrderGroupStats();
		stats.setOrderAmount(rs.getLong("order_amount"));
		stats.setCompleteAmount(rs.getLong("complete_amount"));
		stats.setNonCheckCompleteAmount(rs.getLong("non_check_complete_amount"));
		stats.setAdditionAmount(rs.getLong("addition_amount"));
		stats.setNonCheckAdditionAmount(rs.getLong("non_check_addition_amount"));
		stats.setCreateDate(rs.getString("create_date"));
		stats.setName(rs.getString("name"));
		stats.setCompanySeq(rs.getLong("company_seq"));
    	
    	return stats;
    }
}