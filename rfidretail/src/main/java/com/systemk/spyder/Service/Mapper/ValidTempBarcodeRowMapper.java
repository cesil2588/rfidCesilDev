package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.ValidBarcode;

public class ValidTempBarcodeRowMapper implements RowMapper<ValidBarcode>{

	@Override
	public ValidBarcode mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ValidBarcode obj = new ValidBarcode();
		obj.setBarcode(rs.getString("barcode"));
		obj.setTargetBarcode(rs.getString("target_barcode"));
		
		return obj;
	}

}
