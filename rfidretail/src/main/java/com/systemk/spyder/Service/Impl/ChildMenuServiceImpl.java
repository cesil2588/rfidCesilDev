package com.systemk.spyder.Service.Impl;


import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Entity.Main.ChildMenu;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.ChildMenuRepository;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.ChildMenuService;
import com.systemk.spyder.Util.SecurityUtil;

@Service
public class ChildMenuServiceImpl implements ChildMenuService{

	@Autowired
	ChildMenuRepository childMenuRepository;


	@Transactional
	@Override
	public Map<String, Object> save(ChildMenu childMenu) throws Exception {
		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		childMenuRepository.save(childMenu);

		LoginUser user = SecurityUtil.getCustomUser();

		if(user != null) {
			UserInfo regUserInfo = new UserInfo();
			regUserInfo.setUserSeq(user.getUserSeq());
			childMenu.setRegUserInfo(regUserInfo);
			childMenu.setRegDate(new Date());

			childMenuRepository.save(childMenu);
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", "저장하였습니다");

		return obj;
	}

	@Transactional
	@Override
	public Map<String, Object> update(ChildMenu childMenu) throws Exception {
		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		LoginUser user = SecurityUtil.getCustomUser();

		if(user != null){
			UserInfo updUserInfo = new UserInfo();
			updUserInfo.setUserSeq(user.getUserSeq());
			childMenu.setUpdUserInfo(updUserInfo);
			childMenu.setUpdDate(new Date());

			childMenuRepository.save(childMenu);
		}


		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", "수정하였습니다");

		return obj;
	}

}
