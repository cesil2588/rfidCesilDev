package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.ReturnBoxModel;

public class ReturnBoxModelRowMapper implements RowMapper<ReturnBoxModel>{

	public ReturnBoxModel mapRow(ResultSet rs, int rowNum) throws SQLException{
		
		ReturnBoxModel returnBox = new ReturnBoxModel();
		
		returnBox.setBarcode(rs.getString("barcode"));
		returnBox.setAmount(rs.getInt("amount"));
		returnBox.setStyle(rs.getString("style"));
		returnBox.setColor(rs.getString("color"));
		returnBox.setSize(rs.getString("size"));
		returnBox.setCompleteDate(rs.getString("completeDate"));
		returnBox.setCompanyName(rs.getString("companyName"));
	
		return returnBox;
	}
}
