package com.systemk.spyder.Service.Impl;


import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Entity.Main.RoleInfo;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.RoleInfoRepository;
import com.systemk.spyder.Repository.Main.Specification.RoleInfoSpecification;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.RoleService;
import com.systemk.spyder.Service.Mapper.RoleMapper;
import com.systemk.spyder.Util.SecurityUtil;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleInfoRepository roleInfoRepository;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private JdbcTemplate template;


	@Transactional(readOnly = true)
	@Override
	public Page<RoleInfo> findAllPage(String role,String useYn, String search, String option, Pageable pageable) throws Exception {

		Page<RoleInfo> page = null;
		Specifications<RoleInfo> specifications = null;

		if(!useYn.equals("all")) {
			specifications = Specifications.where(RoleInfoSpecification.useYnEqual(useYn));
		} else {
			specifications = Specifications.where(null);
		}
		if(!role.equals("all")){
			specifications = specifications.and(RoleInfoSpecification.roleEqual(role));
		}

		if(!search.equals("") && option.equals("roleName")){
			specifications = specifications.and(RoleInfoSpecification.roleNameContain(search));
		}

		page = roleInfoRepository.findAll(specifications,pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public List<RoleInfo> findAll() throws Exception {
		return roleInfoRepository.findAll();
	}


	@Transactional
	@Override
	public Map<String, Object> addRole(RoleInfo roleInfo) throws Exception {
		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		LoginUser user = SecurityUtil.getCustomUser();

		if(user != null) {
			UserInfo regUserInfo = new UserInfo();
			regUserInfo.setUserSeq(user.getUserSeq());
			roleInfo.setRegUserInfo(regUserInfo);
			roleInfo.setRegDate(new Date());

			roleInfoRepository.save(roleInfo);
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", "추가하였습니다");

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public List<RoleInfo> findSelectGroupBy() throws Exception {

		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "SELECT code_value AS role, name AS roleName, use_yn AS useYn  from code_info where code_value "
				+ "NOT IN (select role from role_info) and parent_code_seq IN ( select parent_code_seq from parent_code_info where code_value = '10003')";

		return template.query(query, new RoleMapper());

	}

	@Transactional
	@Override
	public Map<String, Object> updateRole(RoleInfo roleInfo) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		LoginUser user = SecurityUtil.getCustomUser();

		if(user != null) {
			UserInfo updUserInfo = new UserInfo();
			updUserInfo.setUserSeq(user.getUserSeq());
			roleInfo.setUpdUserInfo(updUserInfo);
			roleInfo.setUpdDate(new Date());

			roleInfoRepository.save(roleInfo);
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", "수정하였습니다");

		return obj;
	}

}
