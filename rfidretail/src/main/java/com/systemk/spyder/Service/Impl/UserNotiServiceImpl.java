package com.systemk.spyder.Service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Entity.Main.UserNoti;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import com.systemk.spyder.Repository.Main.UserNotiRepository;
import com.systemk.spyder.Repository.Main.Specification.UserNotiSpecification;
import com.systemk.spyder.Service.UserNotiService;
import com.systemk.spyder.Util.CalendarUtil;

@Service
public class UserNotiServiceImpl implements UserNotiService{

	@Autowired
	private UserNotiRepository userNotiRepository;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@Transactional(readOnly = true)
	@Override
	public List<UserNoti> findAll(Long userSeq) {
		return userNotiRepository.findByNotiUserInfoUserSeqAndCheckYnOrderByUserNotiSeqDesc(userSeq, "N");
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<UserNoti> findAll(String startDate, String endDate, Long userSeq, String checkYn, String search, String option, Pageable pageable) throws Exception{
		
		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Page<UserNoti> page = null;
		
		Specifications<UserNoti> specifications = null;
		
		specifications = Specifications.where(UserNotiSpecification.regDateBetween(start, end));
		
		if(userSeq != 0){
			specifications = specifications.and(UserNotiSpecification.userSeqEqual(userSeq));
		}
		
		if(!checkYn.equals("all")){
			specifications = specifications.and(UserNotiSpecification.checkYnEqual(checkYn));
		}
		
		page = userNotiRepository.findAll(specifications, pageable);
		
		return page;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<UserNoti> findTop10(Long userSeq) {
		return userNotiRepository.findTop10ByNotiUserInfoUserSeqAndCheckYnOrderByUserNotiSeqDesc(userSeq, "N");
	}

	@Transactional
	@Override
	public UserNoti update(UserNoti userNoti) {
		return userNotiRepository.save(userNoti);
	}

	@Transactional
	@Override
	public UserNoti save(String message, UserInfo notiUserInfo, String category, Long targetSeq){
		
		UserNoti userNoti = new UserNoti();
		userNoti.setCheckYn("N");
		userNoti.setNotice(message);
		userNoti.setCategory(category);
		userNoti.setTargetSeq(targetSeq);
		userNoti.setRegDate(new Date());
		userNoti.setNotiUserInfo(notiUserInfo);
		userNoti = userNotiRepository.save(userNoti);
		
		template.convertAndSendToUser(notiUserInfo.getUserId(), "/queue/notiReceive", userNoti);
		
		return userNoti;
	}

	
	@Transactional
	@Override
	public UserNoti test(String message, Long userSeq, String category, Long targetSeq){
		
		UserInfo notiUserInfo = userInfoRepository.findOne(Long.valueOf(1));
		
		UserNoti userNoti = new UserNoti();
		userNoti.setCheckYn("N");
		userNoti.setNotice(message);
		userNoti.setCategory(category);
		userNoti.setTargetSeq(targetSeq);
		userNoti.setRegDate(new Date());
		userNoti.setNotiUserInfo(notiUserInfo);
		
		template.convertAndSendToUser(notiUserInfo.getUserId(), "/queue/notiReceive", userNoti);
		
		return userNoti;
	}
}
