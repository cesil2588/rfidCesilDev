package com.systemk.spyder.Service.Mapper.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.Group.BartagOrderGroupModel;

public class BartagOrderGroupModelMapper implements RowMapper<BartagOrderGroupModel>{

	@Override
	public BartagOrderGroupModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		BartagOrderGroupModel obj = new BartagOrderGroupModel();
		obj.setDate(rs.getString("date"));
		obj.setProductYy(rs.getString("product_yy"));
		obj.setProductSeason(rs.getString("product_season"));
		obj.setName(rs.getString("name"));
		obj.setCompanySeq(rs.getLong("company_seq"));
		obj.setOrderAmount(rs.getLong("order_amount"));
		obj.setCompleteAmount(rs.getLong("complete_amount"));
		obj.setNonCheckCompleteAmount(rs.getLong("non_check_complete_amount"));
		obj.setAdditionAmount(rs.getLong("addition_amount"));
		obj.setNonCheckAdditionAmount(rs.getLong("non_check_addition_amount"));
		
		return obj;
	}
}
