package com.systemk.spyder.Dto.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.systemk.spyder.Entity.Main.UserInfo;

public class ApiUserInfoResult {
	
	public String resultMessage;
	
	public int resultCode;
	
	@JsonInclude(Include.NON_DEFAULT)
	private long userSeq;
	
	@JsonInclude(Include.NON_NULL)
    private String userId;
	
	@JsonInclude(Include.NON_NULL)
    private String userUseYn;
    
	@JsonInclude(Include.NON_NULL)
    private String userCheckYn;
	
	@JsonInclude(Include.NON_DEFAULT)
	private long companySeq;
	
	@JsonInclude(Include.NON_NULL)
	private String companyName;
	
	@JsonInclude(Include.NON_NULL)
	private String companyCode;
	
	@JsonInclude(Include.NON_NULL)
	private String companyCustomerCode;
	
	@JsonInclude(Include.NON_NULL)
	private String companyCornerName;
	
	@JsonInclude(Include.NON_NULL)
	private String companyCornerCode;
	
	@JsonInclude(Include.NON_NULL)
	private String companyType;
	
	@JsonInclude(Include.NON_NULL)
	private String companyCloseYn;
	
	@JsonInclude(Include.NON_NULL)
	private String companyAddress1;
	
	@JsonInclude(Include.NON_NULL)
	private String companyAddress2;
	
	@JsonInclude(Include.NON_NULL)
	private String companyTelNo;
	
	@JsonInclude(Include.NON_NULL)
	private String companyRole;
	
	@JsonInclude(Include.NON_NULL)
    private String companyUseYn;
    
	@JsonInclude(Include.NON_NULL)
	private String appDownloadUrl;
    
	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
	public long getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(long userSeq) {
		this.userSeq = userSeq;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserUseYn() {
		return userUseYn;
	}

	public void setUserUseYn(String userUseYn) {
		this.userUseYn = userUseYn;
	}

	public String getUserCheckYn() {
		return userCheckYn;
	}

	public void setUserCheckYn(String userCheckYn) {
		this.userCheckYn = userCheckYn;
	}

	public long getCompanySeq() {
		return companySeq;
	}

	public void setCompanySeq(long companySeq) {
		this.companySeq = companySeq;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyCustomerCode() {
		return companyCustomerCode;
	}

	public void setCompanyCustomerCode(String companyCustomerCode) {
		this.companyCustomerCode = companyCustomerCode;
	}

	public String getCompanyCornerName() {
		return companyCornerName;
	}

	public void setCompanyCornerName(String companyCornerName) {
		this.companyCornerName = companyCornerName;
	}

	public String getCompanyCornerCode() {
		return companyCornerCode;
	}

	public void setCompanyCornerCode(String companyCornerCode) {
		this.companyCornerCode = companyCornerCode;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getCompanyCloseYn() {
		return companyCloseYn;
	}

	public void setCompanyCloseYn(String companyCloseYn) {
		this.companyCloseYn = companyCloseYn;
	}

	public String getCompanyAddress1() {
		return companyAddress1;
	}

	public void setCompanyAddress1(String companyAddress1) {
		this.companyAddress1 = companyAddress1;
	}

	public String getCompanyAddress2() {
		return companyAddress2;
	}

	public void setCompanyAddress2(String companyAddress2) {
		this.companyAddress2 = companyAddress2;
	}

	public String getCompanyTelNo() {
		return companyTelNo;
	}

	public void setCompanyTelNo(String companyTelNo) {
		this.companyTelNo = companyTelNo;
	}

	public String getCompanyRole() {
		return companyRole;
	}

	public void setCompanyRole(String companyRole) {
		this.companyRole = companyRole;
	}

	public String getCompanyUseYn() {
		return companyUseYn;
	}

	public void setCompanyUseYn(String companyUseYn) {
		this.companyUseYn = companyUseYn;
	}
	
	public ApiUserInfoResult(String resultMessage, int resultCode, UserInfo userInfo) {
		super();
		this.resultMessage = resultMessage;
		this.resultCode = resultCode;
		this.userSeq = userInfo.getUserSeq();
		this.userId = userInfo.getUserId();
		this.userUseYn = userInfo.getUseYn();
		this.userCheckYn = userInfo.getCheckYn();
		this.companySeq = userInfo.getCompanyInfo().getCompanySeq();
		this.companyName = userInfo.getCompanyInfo().getName();
		this.companyCode = userInfo.getCompanyInfo().getCode();
		this.companyCustomerCode = userInfo.getCompanyInfo().getCustomerCode();
		this.companyCornerCode = userInfo.getCompanyInfo().getCornerCode();
		this.companyType = userInfo.getCompanyInfo().getType();
		this.companyCloseYn = userInfo.getCompanyInfo().getCloseYn();
		this.companyAddress1 = userInfo.getCompanyInfo().getAddress1();
		this.companyAddress2 = userInfo.getCompanyInfo().getAddress2();
		this.companyTelNo = userInfo.getCompanyInfo().getTelNo();
		this.companyRole = userInfo.getCompanyInfo().getRoleInfo().getRole();
		this.companyUseYn = userInfo.getCompanyInfo().getUseYn();
	}
	
	public ApiUserInfoResult(String resultMessage, int resultCode) {
		super();
		this.resultMessage = resultMessage;
		this.resultCode = resultCode;
	}

	public String getAppDownloadUrl() {
		return appDownloadUrl;
	}

	public void setAppDownloadUrl(String appDownloadUrl) {
		this.appDownloadUrl = appDownloadUrl;
	}
}
