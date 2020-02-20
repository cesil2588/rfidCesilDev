package com.systemk.spyder.Service.Impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.BoxWorkGroup;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.BoxInfoRepository;
import com.systemk.spyder.Repository.Main.BoxWorkGroupRepository;
import com.systemk.spyder.Repository.Main.Specification.BoxWorkGroupSpecification;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.BoxWorkGroupService;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.SecurityUtil;

@Service
public class BoxWorkGroupServiceImpl implements BoxWorkGroupService{

	@Autowired
	private BoxWorkGroupRepository boxWorkGroupRepository;

	@Autowired
	private BoxInfoRepository boxInfoRepository;

	@Transactional(readOnly = true)
	@Override
	public Page<BoxWorkGroup> findAll(String startDate, String endDate, Long startCompanySeq, Long endCompanySeq, String stat, String type, Pageable pageable, String search) throws Exception {

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Page<BoxWorkGroup> page = null;

		Specifications<BoxWorkGroup> specifications = Specifications.where(BoxWorkGroupSpecification.regDateBetween(start, end))
																	.and(BoxWorkGroupSpecification.typeEqual(type));

		if(startCompanySeq != 0) {
			specifications = specifications.and(BoxWorkGroupSpecification.startCompanySeqEqual(startCompanySeq));
		}

		if(endCompanySeq != 0){
			specifications = specifications.and(BoxWorkGroupSpecification.endCompanySeqEqual(endCompanySeq));
		}

		if(!stat.equals("") && !search.equals("")){
			specifications = specifications.and(BoxWorkGroupSpecification.statEqual(stat));
		}

		page = boxWorkGroupRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional
	@Override
	public boolean saveAll(List<BoxWorkGroup> boxWorkGroupList) throws Exception {

		boolean success = true;

		LoginUser user = SecurityUtil.getCustomUser();

		if(user != null){
			UserInfo regUserInfo = new UserInfo();
			regUserInfo.setUserSeq(user.getUserSeq());

			for(BoxWorkGroup boxWorkGroup : boxWorkGroupList){

				boxWorkGroup.setRegUserInfo(regUserInfo);
				boxWorkGroup.setRegDate(new Date());

				for(BoxInfo box : boxWorkGroup.getBoxInfo()){

					Long count = boxInfoRepository.countByBarcode(box.getBarcode());

					if(count > 0){
						success = false;
						return success;
					}

					box.setRegUserInfo(regUserInfo);
					box.setRegDate(new Date());
				}

				boxWorkGroupRepository.save(boxWorkGroup);
			}
		}
		return success;
	}

	@Transactional
	@Override
	public Map<String, Object> updateBoxGroupStat(List<BoxWorkGroup> boxWorkGroupList) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		LoginUser user = SecurityUtil.getCustomUser();

		if(user != null){
			UserInfo userInfo = new UserInfo();
			userInfo.setUserSeq(user.getUserSeq());

			for(BoxWorkGroup boxWorkGroup : boxWorkGroupList){
				boxWorkGroupRepository.updateUpdUser(boxWorkGroup.getBoxWorkGroupSeq(), "2", userInfo.getUserSeq());
			}
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}
}
