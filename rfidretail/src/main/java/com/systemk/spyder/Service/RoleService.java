package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Entity.Main.RoleInfo;

public interface RoleService {
	public Page<RoleInfo> findAllPage(String role,String useYn,String search, String option, Pageable pageable) throws Exception;
	public List<RoleInfo> findAll() throws Exception;
	public Map<String, Object> updateRole(RoleInfo roleInfo) throws Exception;
	public Map<String, Object> addRole(RoleInfo roleInfo) throws Exception;
	public List<RoleInfo> findSelectGroupBy() throws Exception;

}
