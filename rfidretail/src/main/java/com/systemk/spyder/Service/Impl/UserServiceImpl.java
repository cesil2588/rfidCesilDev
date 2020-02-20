package com.systemk.spyder.Service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Dto.Request.MemberBean;
import com.systemk.spyder.Dto.Response.ApiUserInfoResult;
import com.systemk.spyder.Dto.Response.LoginUserResult;
import com.systemk.spyder.Entity.Main.AppInfo;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import com.systemk.spyder.Repository.Main.Specification.UserInfoSpecification;
import com.systemk.spyder.Security.CustomUserDetailService;
import com.systemk.spyder.Service.AppService;
import com.systemk.spyder.Service.JwtService;
import com.systemk.spyder.Service.UserService;
import com.systemk.spyder.Util.ResultUtil;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private AppService appService;

	@Autowired
    private Environment env;

	@Autowired
	private JwtService jwtService;

	@Transactional(readOnly = true)
	@Override
	public Page<UserInfo> findAll(Long companySeq, String role, String checkYn, String search, String option, Pageable pageable) throws Exception {

		Page<UserInfo> page = null;

		Specifications<UserInfo> specifications = null;

		if(companySeq != 0){
			specifications = Specifications.where(UserInfoSpecification.companySeqEqual(companySeq));
		}

		if(!role.equals("all") && specifications == null){
			specifications = Specifications.where(UserInfoSpecification.roleEqual(role));
		} else if(!role.equals("all") && specifications != null){
			specifications = specifications.and(UserInfoSpecification.roleEqual(role));
		}

		if(!checkYn.equals("all") && specifications == null){
			specifications = Specifications.where(UserInfoSpecification.checkYnEqual(checkYn));
		} else if(!checkYn.equals("all") && specifications != null){
			specifications = specifications.and(UserInfoSpecification.checkYnEqual(checkYn));
		}

		if(!search.equals("") && option.equals("userId") && specifications == null){
			specifications = Specifications.where(UserInfoSpecification.userIdContain(search));
		} else if(!search.equals("") && option.equals("userId") && specifications != null){
			specifications = specifications.and(UserInfoSpecification.userIdContain(search));
		}

		page = userInfoRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional(readOnly = true)
	@Override
	public List<ApiUserInfoResult> restMemberLogin(String userId, String password, String version, String type, String appType) throws Exception{

		List<ApiUserInfoResult> apiUserInfoList = new ArrayList<ApiUserInfoResult>();

		if(password.equals("") || userId.equals("")){
			ApiUserInfoResult apiUserInfo = new ApiUserInfoResult(ApiResultConstans.BAD_PARAMETER_MESSAGE, ApiResultConstans.BAD_PARAMETER);
			apiUserInfoList.add(apiUserInfo);
			return apiUserInfoList;
		}

		UserInfo user = userInfoRepository.findByUserId(userId);

		if(user == null){
			ApiUserInfoResult apiUserInfo = new ApiUserInfoResult(ApiResultConstans.NOT_FIND_USER_MESSAGE, ApiResultConstans.NOT_FIND_USER);
			apiUserInfoList.add(apiUserInfo);
			return apiUserInfoList;
		}

		if(!passwordEncoder.matches(password, user.getPassword())){
			ApiUserInfoResult apiUserInfo = new ApiUserInfoResult(ApiResultConstans.NOT_VALID_PASSWORD_MESSAGE, ApiResultConstans.NOT_VALID_PASSWORD);
			apiUserInfoList.add(apiUserInfo);
			return apiUserInfoList;
		}

		if(user.getUseYn().equals("N")){
			ApiUserInfoResult apiUserInfo = new ApiUserInfoResult(ApiResultConstans.NOT_USE_USER_MESSAGE, ApiResultConstans.NOT_USE_USER);
			apiUserInfoList.add(apiUserInfo);
			return apiUserInfoList;
		}

		if(user.getCheckYn().equals("N")){
			ApiUserInfoResult apiUserInfo = new ApiUserInfoResult(ApiResultConstans.NOT_CHECK_ADMIN_MESSAGE, ApiResultConstans.NOT_CHECK_ADMIN);
			apiUserInfoList.add(apiUserInfo);
			return apiUserInfoList;
		}

		if(user.getCompanyInfo().getUseYn().equals("N")){
			ApiUserInfoResult apiUserInfo = new ApiUserInfoResult(ApiResultConstans.NOT_USE_COMPANY_MESSAGE, ApiResultConstans.NOT_USE_COMPANY);
			apiUserInfoList.add(apiUserInfo);
			return apiUserInfoList;
		}


		if(type.equals("2")){
			AppInfo appInfo = appService.currentRepresentApp(appType);

			if(appInfo != null){

				if(!version.equals(appInfo.getVersion())){
					ApiUserInfoResult apiUserInfo = new ApiUserInfoResult(ApiResultConstans.VERSION_UPDATE_MESSAGE, ApiResultConstans.VERSION_UPDATE);
					apiUserInfo.setAppDownloadUrl(env.getProperty("version.download.url"));
					apiUserInfoList.add(apiUserInfo);
					return apiUserInfoList;
				}

				switch(appType){
					case "1" :
						if(!user.getCompanyInfo().getRoleInfo().getRole().equals("production")){
							ApiUserInfoResult apiUserInfo = new ApiUserInfoResult(ApiResultConstans.ROLE_VIOLATION_MESSAGE, ApiResultConstans.ROLE_VIOLATION);
							apiUserInfoList.add(apiUserInfo);
							return apiUserInfoList;
						}
						break;
					case "2" :
						if(!user.getCompanyInfo().getRoleInfo().getRole().equals("distribution")){
							ApiUserInfoResult apiUserInfo = new ApiUserInfoResult(ApiResultConstans.ROLE_VIOLATION_MESSAGE, ApiResultConstans.ROLE_VIOLATION);
							apiUserInfoList.add(apiUserInfo);
							return apiUserInfoList;
						}
						break;
					case "3" :
						if(!(user.getCompanyInfo().getRoleInfo().getRole().equals("sales") || user.getCompanyInfo().getRoleInfo().getRole().equals("special"))){
							ApiUserInfoResult apiUserInfo = new ApiUserInfoResult(ApiResultConstans.ROLE_VIOLATION_MESSAGE, ApiResultConstans.ROLE_VIOLATION);
							apiUserInfoList.add(apiUserInfo);
							return apiUserInfoList;
						}
						break;
				}
			}

		}

		UserDetails userDetail = customUserDetailService.loadUserByUsername(user.getUserId());
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
					userDetail, user.getPassword(),
					userDetail.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(auth);


		ApiUserInfoResult apiUserInfo = new ApiUserInfoResult(ApiResultConstans.LOGIN_SUCCESS_MESSAGE, ApiResultConstans.SUCCESS, user);
		apiUserInfoList.add(apiUserInfo);

		return apiUserInfoList;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> restMemberLogin(MemberBean member) throws Exception {

		// RequestBody 체크
		if(member == null ||
			member.getUserId() == null || member.getUserId().equals("") ||
			member.getPassword() == null || member.getPassword().equals("") ||
			member.getType() == null || member.getType().equals("") ||
			member.getAppType() == null || member.getAppType().equals("")){

			return ResultUtil.setCommonResult("E", ApiResultConstans.BAD_PARAMETER_MESSAGE);
		}

		UserInfo user = userInfoRepository.findByUserId(member.getUserId());

		// 사용자 정보 검증
		if(user == null){
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_USER_MESSAGE);
		}

		// 패스워드 검증
		if(!passwordEncoder.matches(member.getPassword(), user.getPassword())){
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_VALID_PASSWORD_MESSAGE);
		}

		// 사용자 사용여부 검증
		if(user.getUseYn().equals("N")){
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_USE_USER_MESSAGE);
		}

		// 사용자 체크 여부 검증
		if(user.getCheckYn().equals("N")){
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_CHECK_ADMIN_MESSAGE);
		}

		// 업체 사용정보 검증
		if(user.getCompanyInfo().getUseYn().equals("N")){
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_USE_COMPANY_MESSAGE);
		}

		// PDA일때만 검증(추후 컨베이어도 변경 고려할 필요 있음
		if(member.getType().equals("2")){
			AppInfo appInfo = appService.currentRepresentApp(member.getAppType());

			// 앱 정보가 없을시 리턴
			if(appInfo == null) {
				return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_APP_MESSAGE);
			}

			// PDA App 버전과 서버 App 버전이 같지 않을 경우
			if(!member.getVersion().equals(appInfo.getVersion())){
				return ResultUtil.setCommonResult("U", env.getProperty("version.download.url") + "storage/" + appInfo.getFileName());
			}

			// PDA App 버전과 사용자 권한에 따른 에러 처리
			switch(member.getAppType()){
				case "1" :
					if(!user.getCompanyInfo().getRoleInfo().getRole().equals("production")){
						return ResultUtil.setCommonResult("E", ApiResultConstans.ROLE_VIOLATION_MESSAGE);
					}
					break;
				case "2" :
					if(!user.getCompanyInfo().getRoleInfo().getRole().equals("distribution")){
						return ResultUtil.setCommonResult("E", ApiResultConstans.ROLE_VIOLATION_MESSAGE);
					}
					break;
				case "3" :
					if(!(user.getCompanyInfo().getRoleInfo().getRole().equals("sales") || user.getCompanyInfo().getRoleInfo().getRole().equals("special"))){
						return ResultUtil.setCommonResult("E", ApiResultConstans.ROLE_VIOLATION_MESSAGE);
					}
					break;
			}
		}

		// Spring Security Session 발생
		UserDetails userDetail = customUserDetailService.loadUserByUsername(user.getUserId());
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
					userDetail, user.getPassword(),
					userDetail.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(auth);

		// JWT 토큰 생성
		String jwt = jwtService.createToken(user);

		return ResultUtil.setCommonResult("S", ApiResultConstans.LOGIN_SUCCESS_MESSAGE, new LoginUserResult(user, jwt));
	}
}
