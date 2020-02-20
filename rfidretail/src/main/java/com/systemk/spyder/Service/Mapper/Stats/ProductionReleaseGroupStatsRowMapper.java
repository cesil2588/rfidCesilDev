package com.systemk.spyder.Service.Mapper.Stats;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.Stats.ProductionReleaseGroupStats;

public class ProductionReleaseGroupStatsRowMapper implements RowMapper<ProductionReleaseGroupStats>{
	
	public ProductionReleaseGroupStats mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ProductionReleaseGroupStats stats = new ProductionReleaseGroupStats();
		
		stats.setAmount(rs.getLong("amount"));
		stats.setNonConfirmAmount(rs.getLong("non_confirm_amount"));
		stats.setConfirmAmount(rs.getLong("confirm_amount"));
		stats.setCompleteAmount(rs.getLong("complete_amount"));
		stats.setDisuseAmount(rs.getLong("disuse_amount"));
		stats.setName(rs.getString("name"));
		stats.setCompanySeq(rs.getLong("company_seq"));
    	
    	return stats;
    }
}