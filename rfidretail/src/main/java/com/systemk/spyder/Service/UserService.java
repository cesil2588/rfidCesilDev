package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Dto.Request.MemberBean;
import com.systemk.spyder.Dto.Response.ApiUserInfoResult;
import com.systemk.spyder.Entity.Main.UserInfo;

public interface UserService {

	public Page<UserInfo> findAll(Long companySeq, String role, String checkYn, String search, String option, Pageable pageable) throws Exception;

	public List<ApiUserInfoResult> restMemberLogin(String userId, String password, String version, String type, String appType) throws Exception;

	public Map<String, Object> restMemberLogin(MemberBean member) throws Exception;
}
