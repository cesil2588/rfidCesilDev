package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.MobileInspectionStyle;

public class MobileInspectionStyleMapper implements RowMapper<MobileInspectionStyle>{

	@Override
	public MobileInspectionStyle mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		MobileInspectionStyle obj = new MobileInspectionStyle();
		obj.setStyle(rs.getString("style"));
		obj.setColor(rs.getString("color"));
		obj.setSize(rs.getString("size"));
		obj.setOrderDegree(rs.getString("order_degree"));
		obj.setAdditionOrderDegree(rs.getString("addition_order_degree"));
		obj.setNonCheckAmount(rs.getLong("non_check_amount"));
		obj.setStockAmount(rs.getLong("stock_amount"));
		obj.setBartagSeq(rs.getLong("bartag_seq"));
		obj.setProductionStorageSeq(rs.getLong("production_storage_seq"));
		
		return obj;
	}

}