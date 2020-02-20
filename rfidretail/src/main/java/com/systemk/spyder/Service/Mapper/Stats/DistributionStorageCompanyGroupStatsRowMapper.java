package com.systemk.spyder.Service.Mapper.Stats;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.Stats.StorageCompanyGroupStats;

public class DistributionStorageCompanyGroupStatsRowMapper implements RowMapper<StorageCompanyGroupStats>{

	@Override
	public StorageCompanyGroupStats mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StorageCompanyGroupStats stats = new StorageCompanyGroupStats();
		
		stats.setTotalAmount(rs.getLong("total_amount"));
		stats.setConfirmAmount(rs.getLong("confirm_amount"));
		stats.setCompleteAmount(rs.getLong("complete_amount"));
		stats.setCompanyName(rs.getString("company_name"));
		stats.setCompanySeq(rs.getLong("company_seq"));
    	
    	return stats;
	}

}
