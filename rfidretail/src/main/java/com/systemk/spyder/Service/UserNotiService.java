package com.systemk.spyder.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Entity.Main.UserNoti;

public interface UserNotiService {

	public List<UserNoti> findAll(Long userSeq);
	
	public Page<UserNoti> findAll(String startDate, String endDate, Long userSeq, String checkYn, String search, String option, Pageable pageable) throws Exception;
	
	public List<UserNoti> findTop10(Long userSeq);
	
	public UserNoti update(UserNoti userNoti);
	
	public UserNoti save(String message, UserInfo notiUserInfo, String category, Long targetSeq);
	
	public UserNoti test(String message, Long userSeq, String category, Long targetSeq);
}
