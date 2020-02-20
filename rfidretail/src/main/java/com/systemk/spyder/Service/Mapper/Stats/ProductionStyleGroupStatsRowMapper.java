package com.systemk.spyder.Service.Mapper.Stats;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.Stats.ProductionStyleGroupStats;

public class ProductionStyleGroupStatsRowMapper implements RowMapper<ProductionStyleGroupStats>{
	
	public ProductionStyleGroupStats mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ProductionStyleGroupStats stats = new ProductionStyleGroupStats();
		stats.setTotalAmount(rs.getLong("total_amount"));
		stats.setStat1Amount(rs.getLong("stat1_amount"));
		stats.setStat2Amount(rs.getLong("stat2_amount"));
		stats.setStat3Amount(rs.getLong("stat3_amount"));
		stats.setStat4Amount(rs.getLong("stat4_amount"));
		stats.setStat5Amount(rs.getLong("stat5_amount"));
		stats.setStat6Amount(rs.getLong("stat6_amount"));
		stats.setStat7Amount(rs.getLong("stat7_amount"));
		stats.setRegDate(rs.getString("convert_reg_date"));
		stats.setStyle(rs.getString("style"));
		stats.setColor(rs.getString("color"));
		stats.setSize(rs.getString("size"));
		stats.setProductionStorageSeq(rs.getLong("production_storage_seq"));
		stats.setName(rs.getString("name"));
		stats.setCompanySeq(rs.getLong("company_seq"));
    	
    	return stats;
    }
}