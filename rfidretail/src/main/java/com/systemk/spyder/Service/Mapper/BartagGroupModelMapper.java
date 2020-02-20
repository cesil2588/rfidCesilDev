package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.BartagGroupModel;

public class BartagGroupModelMapper implements RowMapper<BartagGroupModel>{

	@Override
	public BartagGroupModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		BartagGroupModel obj = new BartagGroupModel();
		
		obj.setRegDate(rs.getString("reg_date"));
		obj.setProductYy(rs.getString("product_yy"));
		obj.setProductSeason(rs.getString("product_season"));
		obj.setName(rs.getString("name"));
		obj.setCompanySeq(rs.getLong("company_seq"));
		obj.setAmount(rs.getLong("amount"));
		obj.setStat1Amount(rs.getLong("stat1_amount"));
		obj.setStat2Amount(rs.getLong("stat2_amount"));
		obj.setStat3Amount(rs.getLong("stat3_amount"));
		obj.setStat4Amount(rs.getLong("stat4_amount"));
		obj.setStat5Amount(rs.getLong("stat5_amount"));
		obj.setStat6Amount(rs.getLong("stat6_amount"));
		obj.setStat7Amount(rs.getLong("stat7_amount"));
		
		return obj;
	}
}
