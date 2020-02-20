package com.systemk.spyder.Dto.Response;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.systemk.spyder.Entity.Main.UserInfo;

/**
 * LoginUser DTO
 * @author eschoi
 *
 */
public class LoginUserResult implements Serializable{

	private static final long serialVersionUID = -4832046829316535826L;
	
	// 사용자 일련번호
	private Long userSeq;

	// 사용자 ID
	private String userId;

	// 사용여부
	private String useYn;

	// 마지막 로그인일시
	private Date lastLoginDate;

	// 업체 명
	private String companyName;

	// 업체코드
	private String customerCode;

	// 권한
	private String role;
	
	// 타입
	private String type;
	
	// JWT 토큰
	private String token;
	
	// 메뉴 목록
	@JsonInclude(Include.NON_DEFAULT)
	private Set<ParentMenuResult> parentMenuList = new HashSet<ParentMenuResult>();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Set<ParentMenuResult> getParentMenuList() {
		return parentMenuList;
	}

	public void setParentMenuList(Set<ParentMenuResult> parentMenuList) {
		this.parentMenuList = parentMenuList;
	}
	
	public Long getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(Long userSeq) {
		this.userSeq = userSeq;
	}
	
	public LoginUserResult() {
		
	}
	
	public LoginUserResult(UserInfo param, String token) {
		this.userSeq = param.getUserSeq();
		this.userId = param.getUserId();
		this.useYn = param.getUseYn();
		this.lastLoginDate = param.getLastLoginDate();
		this.customerCode = param.getCompanyInfo().getCustomerCode();
		this.companyName = param.getCompanyInfo().getName();
		this.role = param.getCompanyInfo().getRoleInfo().getRole();
		this.type = param.getCompanyInfo().getType();
		this.token = token;
		
		//TODO 메뉴부분 적용시 처리
//		for(MenuMapping menuMapping : param.getCompanyInfo().getRoleInfo().getMenuMapping()) {
//			this.parentMenuList.add(new ParentMenuResult(menuMapping.getParentMenu()));
//		}
	}

}
