package com.systemk.spyder.Service.Mapper.Select;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Service.CustomBean.Select.SelectGroupBy;

public class SelectGroupByMapper implements RowMapper<SelectGroupBy>{

	public SelectGroupBy mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		SelectGroupBy select = new SelectGroupBy();
		
		select.setFlag(rs.getString("flag"));
		select.setData(rs.getString("data"));
		select.setRank(rs.getString("rank"));
    	
    	return select;
    }
}
