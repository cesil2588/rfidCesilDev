package com.systemk.spyder.Service.Impl;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Entity.Main.MenuMapping;
import com.systemk.spyder.Repository.Main.MenuMappingRepository;
import com.systemk.spyder.Service.MenuMappingService;

@Service
public class MenuMappingServiceImpl implements MenuMappingService {

	@Autowired
	private MenuMappingRepository menuMappingRepository;

	@Transactional
	@Override
	public Map<String, Object> updateMenuMapping(List<MenuMapping> menuMapping) throws Exception {
		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		menuMappingRepository.save(menuMapping);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", "수정하였습니다");

		return obj;
	}


	@Transactional
	@Override
	public Map<String, Object> deleteMenuOfRole(String role) throws Exception {
		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		menuMappingRepository.deleteByRoleInfoRole(role);

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", "삭제하였습니다");

		return obj;
	}

}
