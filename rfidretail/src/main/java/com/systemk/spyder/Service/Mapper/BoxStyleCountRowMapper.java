package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.StyleModel;

public class BoxStyleCountRowMapper implements RowMapper<StyleModel> {

	public StyleModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StyleModel style = new StyleModel();
		
		style.setStyle(rs.getString("style"));
		style.setColor(rs.getString("color"));
		style.setSize(rs.getString("size"));
		style.setOrderDegree(rs.getString("order_degree"));
		style.setCount(rs.getLong("count"));
		style.setStyleSeq(rs.getLong("style_seq"));
    	
    	return style;
    }
}
