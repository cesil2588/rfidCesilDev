package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.MobileReleaseBox;

public class MobileReleaseBoxMapper implements RowMapper<MobileReleaseBox>{

	@Override
	public MobileReleaseBox mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		MobileReleaseBox obj = new MobileReleaseBox();
		
		obj.setBoxSeq(rs.getLong("box_seq"));
		obj.setStartCompanySeq(rs.getLong("start_company_seq"));
		obj.setEndCompanySeq(rs.getLong("end_company_seq"));
		obj.setBarcode(rs.getString("barcode"));
		obj.setBoxNum(rs.getString("box_num"));
		obj.setType(rs.getString("type"));
		obj.setReturnYn(rs.getString("return_yn"));
		obj.setStat(rs.getString("stat"));
		
		return obj;
	}

}