package com.systemk.spyder.Service.Impl;


import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Entity.Main.ParentMenu;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.ParentMenuRepository;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.ParentMenuService;
import com.systemk.spyder.Util.SecurityUtil;

@Service
public class ParentMenuServiceImpl implements ParentMenuService{

	@Autowired
	ParentMenuRepository parentMenuRepository;

	@Transactional(readOnly = true)
	@Override
	public List<ParentMenu> findAllList() throws Exception {
		return parentMenuRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public List<ParentMenu> findUseList(String useYn) throws Exception {
		return parentMenuRepository.findByUseYn(useYn);
	}

	@Transactional
	@Override
	public Map<String, Object> save(ParentMenu parentMenu) throws Exception {
		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		parentMenuRepository.save(parentMenu);

		LoginUser user = SecurityUtil.getCustomUser();

		if(user != null) {
			UserInfo regUserInfo = new UserInfo();
			regUserInfo.setUserSeq(user.getUserSeq());
			parentMenu.setRegUserInfo(regUserInfo);
			parentMenu.setRegDate(new Date());

			parentMenuRepository.save(parentMenu);
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", "저장하였습니다");

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> update(ParentMenu parentMenu) throws Exception {
		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		LoginUser user = SecurityUtil.getCustomUser();

		if(user != null){
			UserInfo updUserInfo = new UserInfo();
			updUserInfo.setUserSeq(user.getUserSeq());
			parentMenu.setUpdUserInfo(updUserInfo);
			parentMenu.setUpdDate(new Date());

			parentMenuRepository.save(parentMenu);
		}


		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", "수정하였습니다");

		return obj;
	}

}
