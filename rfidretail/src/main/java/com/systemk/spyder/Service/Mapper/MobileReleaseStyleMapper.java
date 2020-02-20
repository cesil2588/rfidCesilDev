package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.MobileReleaseStyle;

public class MobileReleaseStyleMapper implements RowMapper<MobileReleaseStyle>{

	@Override
	public MobileReleaseStyle mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		MobileReleaseStyle obj = new MobileReleaseStyle();
		
		obj.setStyle(rs.getString("style"));
		obj.setColor(rs.getString("color"));
		obj.setSize(rs.getString("size"));
		obj.setOrderDegree(rs.getString("order_degree"));
		obj.setAdditionOrderDegree(rs.getString("addition_order_degree"));
		obj.setStockAmount(rs.getLong("stock_amount"));
		obj.setReleaseAmount(rs.getLong("release_amount"));
		obj.setProductionStorageSeq(rs.getLong("production_storage_seq"));
		
		return obj;
	}

}