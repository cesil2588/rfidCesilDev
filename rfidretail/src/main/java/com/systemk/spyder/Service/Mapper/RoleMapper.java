package com.systemk.spyder.Service.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;

import com.systemk.spyder.Entity.Main.MenuMapping;
import com.systemk.spyder.Entity.Main.RoleInfo;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.Select.SelectGroupBy;

public class RoleMapper implements RowMapper<RoleInfo> {
    

	public RoleInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		RoleInfo select = new RoleInfo();
		
		select.setRole(rs.getString("role"));
		select.setRoleName(rs.getString("roleName"));
		select.setUseYn(rs.getString("useYn"));
    	
    	return select;
    }
}